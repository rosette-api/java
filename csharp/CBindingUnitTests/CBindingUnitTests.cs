using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using System.Net;
using System.Net.Http;
using System.Threading;
using System.Threading.Tasks;
using System.Web;
using CBinding;
using System.Web.Script.Serialization;

namespace CBindingUnitTests
{
    /* Mock Data Handler
     * 
     * Provides support for getting data from the request/response directories of the mockdata folder
     * 
     */
    class CMockData
    {
        private static string mockDir = "../../../../mock-data";
        public static string requestDir = mockDir + "/request/";
        public static string responseDir = mockDir + "/response/";
        public CMockData()
        {
            RequestDir = requestDir;
            ResponseDir = responseDir;
        }

        public string RequestDir { get; set; }
        public string ResponseDir { get; set; }


        /* Gets all the Request file names
         * 
         * @returns List<string> of file names in the request directory
         * 
         */
        public List<string> getAllRequests()
        {
            List<string> req = new List<string>();
            foreach (string s in Directory.EnumerateFiles(requestDir))
            {
                req.Add(s);
            }
            return req;
        }

        /* Gets file data in string form from the file
         * 
         * @params filename: string path to the file data
         * 
         * @returns string form of the file data or null if file does not exist
         * 
         */
        public string getFileData(string filename)
        {
            if (File.Exists(filename))
            {
                using (StreamReader file = File.OpenText(filename))
                {
                    return file.ReadToEnd();
                }
            }
            else
            {
                return null;
            }
        }
    }

    /* Mock HTTP Handler to simulate the HTTP requests and responses
     * 
     * Checks the user_key and if it matches the correct filename, sends back the response 
     * 
     */
    public class FakeHttpMessageHandler : HttpMessageHandler
    {
        private HttpResponseMessage response;
        private string filename;

        /* Sets the response and filename if there are any
         * 
         * @params response: HttpResponseMessage sent back when an acceptable HTTP request comes in
         * @params filename: string name of file to be checked against the user_key sent by the api request
         * 
         */
        public FakeHttpMessageHandler(HttpResponseMessage response, string filename = null)
        {
            this.response = response;
            this.filename = filename;
        }

        /* Handles the Async Get Post requests
         * 
         * @params request: HttpRequestMessage sent by the API. Contains a user_key (filename for testing) and content
         * @params cancellationToken: Sent by Async caller. Not handled manually.
         * 
         * @return Task<HttpResponseMessage>: Sends back a response message with the set response if the filename matches 
         *                                    the user_key sent by the request header.
         * 
         */
        protected override Task<HttpResponseMessage> SendAsync(HttpRequestMessage request, CancellationToken cancellationToken)
        {
            IEnumerable<string> headerValues = request.Headers.GetValues("user_key");
            string user_key = headerValues.FirstOrDefault();
            var responseTask = new TaskCompletionSource<HttpResponseMessage>();
            responseTask.SetResult(response);
            if (user_key == filename || filename == null)
            {
                return responseTask.Task;
            }
            else
            {
                return null;
            }
        }
    }

    /* Data Structure for the Mock Data input in accordance with other language bindings
     * 
     * @param inpFilename: string request filename
     * @param outputStatusFilename: string response status filename
     * @param outputDataFilename: string response data filename
     * @param endpoint: string API endpoint
     * 
     */
    public class TestDataStructure{
        public TestDataStructure()
        {

        }
        public string inpFilename { get; set; }
        public string outputStatusFilename { get; set; }
        public string outputDataFilename { get; set; }
        public string endpoint { get; set; }
    }

    [TestClass]
    public class CBindingUnitTests
    {
        /* Setup the List of Test data 
         * 
         * Adds Info and Ping as the first two API endpoints to be tested
         * 
         */
        public List<TestDataStructure> Setup()
        {
            CMockData c = new CMockData();
            List<TestDataStructure> allData = new List<TestDataStructure>()
            {
                {new TestDataStructure {inpFilename = null, outputStatusFilename = c.ResponseDir + "/info.status", outputDataFilename = c.ResponseDir + "/info.json", endpoint = "info"}},
                {new TestDataStructure {inpFilename = null, outputStatusFilename = c.ResponseDir + "/ping.status", outputDataFilename = c.ResponseDir + "/ping.json", endpoint = "ping"}}
            };
            List<string> cRequests = new List<string>();
            cRequests = c.getAllRequests();
            foreach (string s in cRequests)
            {
                var filename = s.Remove(0, c.RequestDir.Length);
                TestDataStructure testD = new TestDataStructure();
                testD.inpFilename = s;
                testD.outputStatusFilename = c.ResponseDir + filename.Remove(filename.Length - 4, 4) + "status";
                testD.outputDataFilename = c.ResponseDir + filename;
                testD.endpoint = filename.Substring(filename.LastIndexOf("-") + 1, filename.Length - filename.LastIndexOf("-") - 6).Replace("_", "/");
                allData.Add(testD);
            }
            return allData;
        }

        /* Test Method: Runs through all files in Requests and checks them against Responses
         * 
         * We are effectively sending the request through the API using the MOCK HTTPclient that has the response
         * 
         * and checking whether or not the API correctly receives the response.
         * 
         */
        [TestMethod]
        public void TestMethod1()
        {
            List<TestDataStructure> mockData = Setup();
            CMockData cmd = new CMockData();

            foreach (TestDataStructure td in mockData)
            {
                CAPI c = new CAPI(td.inpFilename);

                Dictionary<object, object> tdInputData = null;
                if (td.inpFilename != null)
                {
                    string tdInput = cmd.getFileData(td.inpFilename);
                    tdInputData = new JavaScriptSerializer().Deserialize<Dictionary<object, object>>(tdInput);
                }
                
                Dictionary<string, object> result = new Dictionary<string,object>();

                var fakeResponse = new HttpResponseMessage();
                string tdStatus = cmd.getFileData(td.outputStatusFilename);
                fakeResponse.StatusCode = (HttpStatusCode)(Convert.ToInt32(tdStatus));

                string tdOutput = cmd.getFileData(td.outputDataFilename);
                fakeResponse.Content = new StringContent(tdOutput);
                var fakeHandler = new FakeHttpMessageHandler(fakeResponse, td.inpFilename);
                var httpClient = new HttpClient(fakeHandler);
                c.Client = httpClient;

                string morphofeature = null; 
                if (td.endpoint.IndexOf("morphology") != -1)
                {
                    morphofeature = td.endpoint.Remove(0, td.endpoint.IndexOf("/"));
                    td.endpoint = td.endpoint.Remove(td.endpoint.IndexOf("/"));
                }
                try
                {
                    switch (td.endpoint)
                    {
                        case "categories":
                            result = c.Categories(tdInputData);
                            break;
                        case "entities":
                            result = c.Entity(tdInputData);
                            break;
                        case "entities/linked":
                            result = c.EntitiesLinked(tdInputData);
                            break;
                        case "info":
                            result = c.Info();
                            break;
                        case "language":
                            result = c.Entity(tdInputData);
                            break;
                        case "morphology":
                            result = c.Morphology(tdInputData, morphofeature);
                            break;
                        case "name":
                            if (td.inpFilename.Contains("matched"))
                            {
                                result = c.MatchedName(tdInputData);
                            }
                            else
                            {
                                result = c.TranslatedName(tdInputData);
                            }
                            break;
                        case "ping":
                            result = c.Ping();
                            break;
                        case "sentences":
                            result = c.Sentences(tdInputData);
                            break;
                        case "sentiment":
                            result = c.Sentiment(tdInputData);
                            break;
                        case "tokens":
                            result = c.Tokens(tdInputData);
                            break;
                    }
                }
                catch(RosetteException e)
                {
                    result = new Dictionary<string, object>(){
                        {"message", e.Message}, 
                        {"code", e.Code}, 
                        {"requestId", e.RequestID},
                        {"file", e.File}, 
                        {"line", e.Line}
                    };
                }
                Dictionary<string, object> outputDict = new JavaScriptSerializer().Deserialize<Dictionary<string,object>>(tdOutput);

                if (result.Keys.Contains("file"))
                {
                    Assert.AreEqual(result["code"], Convert.ToInt32(tdStatus));
                }
                else
                {
                    foreach (string key in outputDict.Keys)
                    {
                        if (key != "requestId")
                        {
                            Assert.IsTrue(result.Keys.Contains(key));
                            if (result[key] is Object)
                            {
                                Assert.AreEqual(new JavaScriptSerializer().Serialize(result[key]), new JavaScriptSerializer().Serialize(outputDict[key]));
                            }
                            else
                            {
                                Assert.AreEqual(outputDict[key], result[key]);
                            }

                        }
                    }
                }
            }
        }
    }
}

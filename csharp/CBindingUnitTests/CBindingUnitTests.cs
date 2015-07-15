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
    /// <summary>Mock Data Handler
    /// <para>Provides support for getting data from the request/response directories of the mockdata folder</para>
    /// </summary>
    class CMockData
    {
        private static string mockDir = null;
        public static string requestDir = null;
        public static string responseDir = null;
        public CMockData()
        {
            string baseDirectory = Directory.GetCurrentDirectory();
            baseDirectory = baseDirectory.Remove(baseDirectory.IndexOf("ws-client-bindings") + 19);
            mockDir = baseDirectory + "mock-data";
            requestDir = mockDir + "\\request\\";
            responseDir = mockDir + "\\response\\";
            RequestDir = requestDir;
            ResponseDir = responseDir;
        }

        public string RequestDir { get; set; }
        public string ResponseDir { get; set; }

        /// <summary>getAllRequests
        /// <para>Gets all the Request file names</para>
        /// </summary>
        /// <returns>List<string> of file names in the request directory</returns>
        public List<string> getAllRequests()
        {
            List<string> req = new List<string>();
            foreach (string s in Directory.EnumerateFiles(requestDir))
            {
                req.Add(s);
            }
            return req;
        }

        /// <summary>getFileData
        /// <para>Gets file data in string form from the file</para>
        /// </summary>
        /// <param name="filename">string: path to the file data</param>
        /// <returns>string form of the file data or null if file does not exist</returns>
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

    /// <summary>Mock HTTP Handler to simulate the HTTP requests and responses
    /// <para>Checks the user_key and if it matches the correct filename, sends back the response</para>
    /// </summary>
    public class FakeHttpMessageHandler : HttpMessageHandler
    {
        private HttpResponseMessage response;

        private HttpResponseMessage responseInfo;

        private string filename;

        /// <summary>FakeHttpMessageHandler
        /// <para>Sets the response and filename if there are any</para>
        /// </summary>
        /// <param name="response">HttpResponseMessage: ResponseMessage sent back when an acceptable HTTP request comes in</param>
        /// <param name="filename">(string optional): Name of file to be checked against the user_key sent by the api request</param>
        public FakeHttpMessageHandler(HttpResponseMessage response, string filename = null)
        {
            this.response = response;
            this.filename = filename;
            this.responseInfo = new HttpResponseMessage();
            this.responseInfo.StatusCode = (HttpStatusCode)(Convert.ToInt32(200));
            this.responseInfo.Content = new StringContent(new JavaScriptSerializer().Serialize(new Dictionary<string, string>(){
                    {"buildNumber", "6bafb29d"}, 
                    {"buildTime", "2015.05.08_12:31:26"}, 
                    {"name", "Rosette API"}, 
                    {"version", "0.5.0"}
                })
            );
        }

        /// <summary>SendAsync
        /// <para>Handles the Async Get Post requests</para>
        /// </summary>
        /// <param name="request">HttpRequestMessage: RequestMessage sent by the API. Contains a user_key (filename for testing) and content</param>
        /// <param name="cancellationToken">CancellationToken: Sent by Async caller. Not handled manually.</param>
        /// <returns>Sends back a response message with the set response if the filename matches 
        /// the user_key sent by the request header.</returns>
        protected override Task<HttpResponseMessage> SendAsync(HttpRequestMessage request, CancellationToken cancellationToken)
        {
            IEnumerable<string> headerValues = request.Headers.GetValues("user_key");
            string uri = request.RequestUri.ToString();
            string user_key = headerValues.FirstOrDefault();
            var responseTask = new TaskCompletionSource<HttpResponseMessage>();
            if (uri.Contains("v1/info"))
            {
                responseTask.SetResult(responseInfo);
            }
            else
            {
                responseTask.SetResult(response);
            }
            return responseTask.Task;
        }
    }

    /// <summary>TestDataStructure
    /// <para>Data Structure for the Mock Data input in accordance with other language bindings</para>
    /// </summary>
    public class TestDataStructure{
        public TestDataStructure()
        {

        }

        /// <summary> inpFilename
        /// <para>
        /// Getter Setter for inpFilename
        /// inpFilename: string path to input data file
        /// </para>
        /// </summary>
        public string inpFilename { get; set; }

        /// <summary> outputStatusFilename
        /// <para>
        /// Getter Setter for outputStatusFilename
        /// outputStatusFilename: string path to output status file
        /// </para>
        /// </summary>
        public string outputStatusFilename { get; set; }

        /// <summary> outputDataFilename
        /// <para>
        /// Getter Setter for outputDataFilename
        /// inpFilename: string path to output data file
        /// </para>
        /// </summary>
        public string outputDataFilename { get; set; }

        /// <summary> endpoint
        /// <para>
        /// Getter Setter for endpoint
        /// inpFilename: string describing endpoint
        /// </para>
        /// </summary>
        public string endpoint { get; set; }
    }

    [TestClass]
    public class CBindingUnitTests
    {
        /// <summary>Setup
        /// <para>Setup the List of Test data. Adds Info and Ping as the first two API endpoints to be tested</para>
        /// </summary>
        /// <returns>List&ltTestDataStructure&gt: List of Test Data</returns>
        public List<TestDataStructure> Setup()
        {
            CMockData c = new CMockData();
            List<TestDataStructure> allData = new List<TestDataStructure>()
            {
                {new TestDataStructure {inpFilename = null, outputStatusFilename = c.ResponseDir + "info.status", outputDataFilename = c.ResponseDir + "info.json", endpoint = "info"}},
                {new TestDataStructure {inpFilename = null, outputStatusFilename = c.ResponseDir + "ping.status", outputDataFilename = c.ResponseDir + "ping.json", endpoint = "ping"}}
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

        /// <summary>Test Method: Runs through all files in Requests and checks them against Responses
        /// <para>We are effectively sending the request through the API using the MOCK HTTPclient that has the response
        /// and checking whether or not the API correctly receives the response.
        /// </para>
        /// </summary>
        [TestMethod]
        public void CAPITest()
        {
            List<TestDataStructure> mockData = Setup();
            CMockData cmd = new CMockData();

            foreach (TestDataStructure td in mockData)
            {
                System.Diagnostics.Debug.WriteLine("Evaluating " + td.outputDataFilename);
                Console.WriteLine("Evaluating " + td.outputDataFilename);
                Dictionary<object, object> tdInputData = null;
                if (td.inpFilename != null)
                {
                    string tdInput = cmd.getFileData(td.inpFilename);
                    if (tdInput != null)
                    {
                        tdInputData = new JavaScriptSerializer().Deserialize<Dictionary<object, object>>(tdInput);
                    }
                }
                
                Dictionary<string, object> result = new Dictionary<string,object>();

                var fakeResponse = new HttpResponseMessage();
                string tdStatus = cmd.getFileData(td.outputStatusFilename);
                fakeResponse.StatusCode = (HttpStatusCode)(Convert.ToInt32(tdStatus));

                string tdOutput = cmd.getFileData(td.outputDataFilename);
                fakeResponse.Content = new StringContent(tdOutput);
                var fakeHandler = new FakeHttpMessageHandler(fakeResponse, td.inpFilename);
                HttpClient httpClient = new HttpClient(fakeHandler);
                CAPI c = new CAPI(td.inpFilename, null, null, 3, httpClient);

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
                    System.Diagnostics.Debug.WriteLine("Status matched");
                    Console.WriteLine("Status matched");
                }
                else
                {
                    bool allResultsMatched = true;
                    foreach (string key in outputDict.Keys)
                    {
                        if (key != "requestId")
                        {
                            Assert.IsTrue(result.Keys.Contains(key));
                            if (result.Keys.Contains(key))
                            {
                                System.Diagnostics.Debug.WriteLine("Key found");
                            }
                            else
                            {
                                System.Diagnostics.Debug.WriteLine("Key NOT found");
                                allResultsMatched = false;
                            }
                            if (result[key] is Object)
                            {
                                Assert.AreEqual(new JavaScriptSerializer().Serialize(result[key]), new JavaScriptSerializer().Serialize(outputDict[key]));
                                if (new JavaScriptSerializer().Serialize(result[key]) == new JavaScriptSerializer().Serialize(outputDict[key]))
                                {
                                    System.Diagnostics.Debug.WriteLine("Results Matched");
                                }
                                else
                                {
                                    System.Diagnostics.Debug.WriteLine("Results NOT Matched");
                                    allResultsMatched = false;
                                }
                            }
                            else
                            {
                                Assert.AreEqual(outputDict[key], result[key]);
                                if (outputDict[key] == result[key])
                                {
                                   System.Diagnostics.Debug.WriteLine("Results Matched");
                                }
                                else
                                {
                                    System.Diagnostics.Debug.WriteLine("Results NOT Matched");
                                    allResultsMatched = false;
                                }
                            }
                        }
                    }
                    if (allResultsMatched)
                    {
                        Console.WriteLine("All results matched");
                    }
                    else
                    {
                        throw new Exception("An Error occurred during the test.");
                    }
                }

            }
        }

        
    }
    static class CTestsMain
    {
        [STAThread]
        static void Main()
        {
            bool success = true;
            Console.WriteLine("Beginning Tests");
            try{
                new CBindingUnitTests().CAPITest();
            }
            catch (Exception e)
            {
                Console.WriteLine("Uncaught Exception: " + e.Message);
                success = false;
            }
            finally
            {
                if (success)
                {
                    Console.WriteLine("Tests passed successfully");
                }
            }
        }
    }
}

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

    public class FakeHttpMessageHandler : HttpMessageHandler
    {
        private HttpResponseMessage response;
        private string filename;

        public FakeHttpMessageHandler(HttpResponseMessage response, string filename = null)
        {
            this.response = response;
            this.filename = filename;
        }

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

        public List<TestDataStructure> Setup()
        {
            CMockData c = new CMockData();
            List<TestDataStructure> allData = new List<TestDataStructure>()
            {
                {new TestDataStructure {inpFilename = null, outputStatusFilename = c.ResponseDir + "/info.status", outputDataFilename = c.ResponseDir + "/info.json", endpoint = "info"}},
                {new TestDataStructure {inpFilename = null, outputStatusFilename = c.ResponseDir + "/ping.status", outputDataFilename = c.ResponseDir + "/ping.json", endpoint = "ping"}}
            };
            List<string> cRequests = new List<string>();
            List<string> cResponses = new List<string>();
            cRequests = c.getAllRequests();
            cResponses = c.getAllResponses();
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

                switch (td.endpoint){
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
                Console.WriteLine(tdOutput);
                Console.WriteLine(new JavaScriptSerializer().Serialize(result));
                Dictionary<string, object> outputDict = new JavaScriptSerializer().Deserialize<Dictionary<string,object>>(tdOutput);

                foreach (string key in outputDict.Keys){
                    Console.WriteLine(key);
                    Assert.IsTrue(result.Keys.Contains(key));
                    Assert.AreEqual(outputDict[key], result[key]);
                }
            }
        }
    }
}

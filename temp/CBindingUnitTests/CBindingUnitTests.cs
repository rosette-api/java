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
            var responseTask = new TaskCompletionSource<HttpResponseMessage>();
            responseTask.SetResult(response);
            if (request.RequestUri.ToString() == filename || filename == null)
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
            List<TestDataStructure> allData = new List<TestDataStructure>();
            CMockData c = new CMockData();
            List<string> cRequests = new List<string>();
            List<string> cResponses = new List<string>();
            cRequests = c.getAllRequests();
            cResponses = c.getAllResponses();
            foreach (string s in cRequests)
            {
                Console.WriteLine(s);
                Console.WriteLine(c.RequestDir);
                var filename = s.Remove(0, c.RequestDir.Length);
                TestDataStructure testD = new TestDataStructure();
                testD.inpFilename = s;
                testD.outputStatusFilename = c.ResponseDir + filename.Remove(filename.Length - 4, 4) + "status";
                testD.outputDataFilename = c.ResponseDir + filename;
                testD.endpoint = filename.Substring(filename.LastIndexOf("-") + 1, filename.Length - filename.LastIndexOf("-") - 6).Replace("_", "/");

                Console.WriteLine(testD.inpFilename);
                Console.WriteLine(testD.outputStatusFilename);
                Console.WriteLine(testD.outputDataFilename);
                Console.WriteLine(testD.endpoint);

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


                string tdInput = cmd.getFileData(td.inpFilename);
                Dictionary<object, object> tdInputData = new JavaScriptSerializer().Deserialize<Dictionary<object, object>>(tdInput);
                Dictionary<string, object> result = new Dictionary<string,object>();

                var fakeResponse = new HttpResponseMessage();
                string tdStatus = cmd.getFileData(td.outputStatusFilename);
                fakeResponse.StatusCode = (HttpStatusCode)(Convert.ToInt32(tdStatus));

                string tdOutput = cmd.getFileData(td.outputDataFilename);
                fakeResponse.Content = new StringContent(tdOutput);
                var fakeHandler = new FakeHttpMessageHandler(fakeResponse, td.inpFilename);
                var httpClient = new HttpClient(fakeHandler);
                c.Client = httpClient;
                string morphofeature; 
                if (td.endpoint.IndexOf("morphology") != -1)
                {
                    td.endpoint.    
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
                        result = c.Categories(tdInputData);
                        break;
                    case "language":
                        result = c.Entity(tdInputData);
                        break;
                    case "entities/linked":
                        result = c.EntitiesLinked(tdInputData);
                        break;

                }
                    
            }

            Assert.IsTrue(false);
        }
    }
}

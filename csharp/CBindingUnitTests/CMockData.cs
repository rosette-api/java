using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;
//using System.Web.Script.Serialization;

namespace CBindingUnitTests
{
    class CMockData
    {    
        private string userKey = null;
        private static string mockDir = "../../../../mock-data";
        public static string requestDir = mockDir + "/request/";
        public static string responseDir = mockDir + "/response/";
        public string docDir = Environment.GetFolderPath(Environment.SpecialFolder.CommonApplicationData) + @"\Basis Technology\Docs\";
        public string dd = Environment.CurrentDirectory;
        public string ddd = null;
        public CMockData(){
            ddd = Environment.CurrentDirectory + @"\..\..\";
            RequestDir = requestDir;
            ResponseDir = responseDir;
        }

        public string RequestDir { get; set; }
        public string ResponseDir { get; set; }

        public List<string> getAllRequests()
        {
            List<string> req = new List<string>();
            foreach (string s in Directory.EnumerateFiles(requestDir))
            {
                req.Add(s);
            }
            return req;
        }

        public List<string> getAllResponses()
        {
            List<string> resp = new List<string>();
            foreach (string s in Directory.EnumerateFiles(responseDir))
            {
                resp.Add(s);
            }
            return resp;
        }

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
}

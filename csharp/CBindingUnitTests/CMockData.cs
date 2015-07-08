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
        private static string mockDir = "../../../../mock-data";
        public static string requestDir = mockDir + "/request/";
        public static string responseDir = mockDir + "/response/";
        public string dd = Environment.CurrentDirectory;
        public string ddd = null;
        public CMockData(){
            ddd = Environment.CurrentDirectory + @"\..\..\";
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
}

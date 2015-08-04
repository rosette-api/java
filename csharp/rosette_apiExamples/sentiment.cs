using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Web.Script.Serialization;
using rosette_api;

namespace rosette_apiExamples
{
    class sentiment
    {
        /// <summary>
        /// Example code to call Rosette API to get a document's sentiment
        /// Requires Nuget Package:
        /// rosette_api
        /// </summary>
        static void Main()
        {
            //To use the C# API, you must provide an API key
            CAPI SentimentCAPI = new CAPI("Your API key");

            StreamWriter sw = new StreamWriter("C:\\Test.txt");
            sw.WriteLine("We are looking forward to the upcoming release.");
            sw.Close();

            try
            {
                //Rosette API provides File upload options (shown here)
                //Simply create a new RosetteFile using the path to a file
                //The results of the API call will come back in the form of a Dictionary
                Dictionary<string, Object> SentimentResult = SentimentCAPI.Sentiment(new RosetteFile("C:\\Test.txt"));
                Console.WriteLine(new JavaScriptSerializer().Serialize(SentimentResult));
            }
            catch (RosetteException e)
            {
                Console.WriteLine("Error Code " + e.Code.ToString() + ":" + e.Message);
            }
        }
    }
}
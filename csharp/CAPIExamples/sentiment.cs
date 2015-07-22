using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using CBinding;
using System.Web.Script.Serialization;

namespace CAPIExamples
{
    class sentiment
    {
        static void Main()
        {
            //Example code to call Rosette API to get a document's sentiment
            CAPI SentimentCAPI = new CAPI("Your API key");

            StreamWriter sw = new StreamWriter("C:\\Test.txt");
            sw.WriteLine("We are looking forward to the upcoming release.");
            sw.Close();

            try
            {
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
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Web.Script.Serialization;
using rosette_api;

namespace rosette_apiExamples
{
    class morphology_han_readings
    {
        /// <summary>
        /// Example code to call Rosette API to get Chinese readings for words in a piece of text.
        /// Requires Nuget Package:
        /// rosette_api
        /// </summary>
        static void Main(string[] args)
        {
            //To use the C# API, you must provide an API key
            string apikey = "Your API key";

            //You may set the API key via command line argument:
            //morphology_han_readings yourapikeyhere
            if (args.Length == 0)
            {
                apikey = args[0];
            } 
            CAPI MorphologyCAPI = new CAPI(apikey);
            try
            {
                //The results of the API call will come back in the form of a Dictionary
                Dictionary<string, Object> MorphologyResult = MorphologyCAPI.Morphology("北京大学生物系主任办公室内部会议", null, null, null, null, "han-readings");
                Console.WriteLine(new JavaScriptSerializer().Serialize(MorphologyResult));
            }
            catch (RosetteException e)
            {
                Console.WriteLine("Error Code " + e.Code.ToString() + ":" + e.Message);
            }
        }
    }
}

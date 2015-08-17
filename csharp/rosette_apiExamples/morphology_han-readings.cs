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
            if (args.Length == 0)
            {
                Console.WriteLine("This example requires an API key argument in the command line");
            }
            else
            {
                CAPI MorphologyCAPI = new CAPI(args[0]);
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
}

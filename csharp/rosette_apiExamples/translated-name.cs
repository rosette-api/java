using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Web.Script.Serialization;
using rosette_api;

namespace rosette_apiExamples
{
    class translated_name
    {
        /// <summary>
        /// Example code to call Rosette API to translate a name from language to another.
        /// Requires Nuget Package:
        /// rosette_api
        /// </summary>
        static void Main()
        {
            //To use the C# API, you must provide an API key
            CAPI TranslatedNameCAPI = new CAPI("your API key");
            try
            {
                //The results of the API call will come back in the form of a Dictionary
                Dictionary<string, Object> TranslatedNameResult = TranslatedNameCAPI.TranslatedName("صفية طالب السهيل", null, null, "eng", null, null, null, "PERSON");
                Console.WriteLine(new JavaScriptSerializer().Serialize(TranslatedNameResult));
            }
            catch (RosetteException e)
            {
                Console.WriteLine("Error Code " + e.Code.ToString() + ":" + e.Message);
            }
        }
    }
}

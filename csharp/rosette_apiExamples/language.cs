using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Web.Script.Serialization;
using rosette_api;

namespace rosette_apiExamples
{
    class language
    {
        /// <summary>
        /// Example code to call Rosette API to detect possible languages for a piece of text.
        /// Requires Nuget Package:
        /// rosette_api
        /// </summary>
        static void Main(string[] args)
        {
            //To use the C# API, you must provide an API key
            string apikey = "Your API key";

            //You may set the API key via command line argument:
            //language yourapikeyhere
            if (args.Length != 0)
            {
                apikey = args[0];
            } 
            try
            {
                CAPI LanguageCAPI = new CAPI(apikey);
                //The results of the API call will come back in the form of a Dictionary
                Dictionary<string, Object> LanguageResult = LanguageCAPI.Language("${language_data}");
                Console.WriteLine(new JavaScriptSerializer().Serialize(LanguageResult));
            }
            catch (Exception e)
            {
                Console.WriteLine("Exception: " + e.Message);
            }
        }
    }
}
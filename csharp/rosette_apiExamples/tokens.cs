using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Web.Script.Serialization;
using rosette_api;

namespace rosette_apiExamples
{
    class tokens
    {
        /// <summary>
        /// Example code to call Rosette API to get tokens (words) in a piece of text.
        /// Requires Nuget Package:
        /// rosette_api
        /// </summary>
        static void Main(string[] args)
        {
            //To use the C# API, you must provide an API key
            string apikey = "Your API key";

            //You may set the API key via command line argument:
            //tokens yourapikeyhere
            if (args.Length != 0)
            {
                apikey = args[0];
            }
            try
            {
                CAPI TokensCAPI = new CAPI(apikey);
                //The results of the API call will come back in the form of a Dictionary
                Dictionary<string, Object> TokensResult = TokensCAPI.Tokens("${tokens_data}", null, null, "sentence", null);
                Console.WriteLine(new JavaScriptSerializer().Serialize(TokensResult));
            }
            catch (Exception e)
            {
                Console.WriteLine("Exception: " + e.Message);
            }
        }
    }
}
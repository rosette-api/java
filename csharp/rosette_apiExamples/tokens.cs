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
        /// Requires Reference to:
        /// System.Net.Http (CAPI)
        /// System.Web.Extensions (JavascriptSerializer)
        /// 
        /// Requires Nuget Package:
        /// rosette_api
        /// </summary>
        static void Main()
        {
            //To use the C# API, you must provide an API key
            CAPI TokensCAPI = new CAPI("your API key");
            try
            {
                //The results of the API call will come back in the form of a Dictionary
                Dictionary<string, Object> TokensResult = TokensCAPI.Tokens("北京大学生物系主任办公室内部会议", null, null, "sentence", null);
                Console.WriteLine(new JavaScriptSerializer().Serialize(TokensResult));
            }
            catch (RosetteException e)
            {
                Console.WriteLine("Error Code " + e.Code.ToString() + ":" + e.Message);
            }
        }
    }
}
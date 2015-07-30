using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Web.Script.Serialization;
using rosette_api;

namespace rosette_apiExamples
{
    class info
    {
        /// <summary>
        /// Example code to call Rosette API to get information such as version and build.
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
            CAPI InfoCAPI = new CAPI("your API key");
            try
            {
                //The results of the API call will come back in the form of a Dictionary
                Dictionary<string, Object> infoResult = InfoCAPI.Info();
                Console.WriteLine(new JavaScriptSerializer().Serialize(infoResult));
            }
            catch (RosetteException e)
            {
                Console.WriteLine("Error Code " + e.Code.ToString() + ":" + e.Message);
            }
        }
    }
}
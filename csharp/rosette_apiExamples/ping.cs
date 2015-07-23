using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Web.Script.Serialization;
using rosette_api;

namespace rosette_apiExamples
{
    class ping
    {
        /// <summary>
        /// Example code to send Rosette API a ping to check its reachability.
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
            CAPI NewCAPI = new CAPI("your API key");
            try
            {
                //The results of the API call will come back in the form of a Dictionary
                Dictionary<string, Object> pingResult = NewCAPI.Ping();
                Console.WriteLine(new JavaScriptSerializer().Serialize(pingResult));
            }
            catch (RosetteException e)
            {
                Console.WriteLine("Error Code " + e.Code.ToString() + ":" + e.Message);
            }
        }
    }
}
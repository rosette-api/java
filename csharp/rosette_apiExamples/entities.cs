using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Web.Script.Serialization;
using rosette_api;

namespace rosette_apiExamples
{
    class entities
    {
        /// <summary>
        /// Example code to call Rosette API to get entities from a piece of text.
        /// Requires Nuget Package:
        /// rosette_api
        /// </summary>
        static void Main()
        {
            //To use the C# API, you must provide an API key
            CAPI EntitiesCAPI = new CAPI("your API key");
            try
            {
                //The results of the API call will come back in the form of a Dictionary
                Dictionary<string, Object> EntitiesResult = EntitiesCAPI.Entity("President Obama urges the Congress and Speaker Boehner to pass the $50 billion spending bill based on Christian faith by July 1st or Washington will become totally dysfunctional, a terrible outcome for American people.");
                Console.WriteLine(new JavaScriptSerializer().Serialize(EntitiesResult));
            }
            catch (RosetteException e)
            {
                Console.WriteLine("Error Code " + e.Code.ToString() + ":" + e.Message);
            }
        }
    }
}
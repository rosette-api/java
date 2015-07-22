using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Web.Script.Serialization;
using rosette_api;

namespace CAPIExamples
{
    class entities_linked
    {
        static void Main()
        {
            //Example code to call Rosette API to get linked (against Wikipedia) entities from a piece of text.
            CAPI EntitiesLinkedCAPI = new CAPI("your API key");
            try
            {
                Dictionary<string, Object> EntitiesLinkedResult = EntitiesLinkedCAPI.EntitiesLinked("President Obama urges the Congress and Speaker Boehner to pass the $50 billion spending bill based on Christian faith by July 1st or Washington will become totally dysfunctional, a terrible outcome for American people.");
                Console.WriteLine(new JavaScriptSerializer().Serialize(EntitiesLinkedResult));
            }
            catch (RosetteException e)
            {
                Console.WriteLine("Error Code " + e.Code.ToString() + ":" + e.Message);
            }
        }
    }
}
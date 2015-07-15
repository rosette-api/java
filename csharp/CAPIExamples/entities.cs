using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using CBinding;
using System.Web.Script.Serialization;

namespace CAPIExamples
{
    class entities
    {
        static void Main()
        {
            //Example code to call Rosette API to get entities from a piece of text.
            CAPI EntitiesCAPI = new CAPI("your API key");
            try
            {
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
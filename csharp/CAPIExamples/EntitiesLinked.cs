using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using CBinding;
using System.Web.Script.Serialization;

namespace CAPIExamples
{
    class EntitiesLinked
    {
        public void EntitiesLinkedEx()
        {
            //Entity Linking
            CAPI EntitiesLinkedCAPI = new CAPI("your API key");
            Dictionary<string, Object> EntitiesLinkedResult = EntitiesLinkedCAPI.EntitiesLinked("The first men to reach the moon -- Mr. Armstrong and his co-pilot, Col. Edwin E. Aldrin, Jr. of the Air Force -- brought their ship to rest on a level, rock-strewn plain near the southwestern shore of the arid Sea of Tranquility.");
            Console.WriteLine(new JavaScriptSerializer().Serialize(EntitiesLinkedResult));
        }
    }
}

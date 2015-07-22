using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Web.Script.Serialization;
using rosette_api;

namespace CAPIExamples
{
    class ping
    {
        static void Main()
        {
            //Example code to send Rosette API a ping to check its reachability.
            CAPI NewCAPI = new CAPI("your API key");
            try
            {
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
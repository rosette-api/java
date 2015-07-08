using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using CBinding;
using System.Web.Script.Serialization;

namespace CAPIExamples
{
    class ping
    {
        static void Main()
        {
            //Create an API instance with key
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
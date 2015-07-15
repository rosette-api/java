using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using CBinding;
using System.Web.Script.Serialization;

namespace CAPIExamples
{
    class info
    {
        static void Main()
        {
            //Example code to call Rosette API to get information such as version and build.
            CAPI InfoCAPI = new CAPI("your API key");
            try
            {
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
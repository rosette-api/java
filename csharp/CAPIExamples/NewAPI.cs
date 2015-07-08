using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using CBinding;
using System.Web.Script.Serialization;

namespace CAPIExamples
{
    class NewAPI
    {
        public void NewAPIEx()
        {
            //Create an API instance with key
            CAPI NewCAPI = new CAPI("your API key");
            Dictionary<string, Object> pingResult = NewCAPI.Ping();
            Console.WriteLine(new JavaScriptSerializer().Serialize(pingResult));
        }
    }
}

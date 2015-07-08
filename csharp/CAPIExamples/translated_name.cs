using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using CBinding;
using System.Web.Script.Serialization;

namespace CAPIExamples
{
    class TranslatedName
    {
        static void Main()
        {
            //Example code to call Rosette API to translate a name from language to another.
            CAPI TranslatedNameCAPI = new CAPI("your API key");
            try
            {
                Dictionary<string, Object> TranslatedNameResult = TranslatedNameCAPI.TranslatedName("صفية طالب السهيل", null, null, "eng", null, null, null, "PERSON");
                Console.WriteLine(new JavaScriptSerializer().Serialize(TranslatedNameResult));
            }
            catch (RosetteException e)
            {
                Console.WriteLine("Error Code " + e.Code.ToString() + ":" + e.Message);
            }
        }
    }
}

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
        public void TranslatedNameEx()
        {
            //Name Translation
            CAPI TranslatedNameCAPI = new CAPI("your API key");
            Dictionary<string, Object> TranslatedNameResult = TranslatedNameCAPI.TranslatedName("صفية طالب السهيل", null, null, "eng", null, null, null, "PERSON");
            Console.WriteLine(new JavaScriptSerializer().Serialize(TranslatedNameResult));
        }
    }
}

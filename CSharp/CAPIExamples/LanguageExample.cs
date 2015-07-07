using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using CBinding;
using System.Web.Script.Serialization;

namespace CAPIExamples
{
    class LanguageExample
    {
        public void LanguageEx()
        {
            //Language Detection
            CAPI LanguageCAPI = new CAPI("your API key");
            Dictionary<string, Object> LanguageResult = LanguageCAPI.Language("The quick brown fox jumped over the lazy dog. Yes he did.");
            Console.WriteLine(new JavaScriptSerializer().Serialize(LanguageResult));
        }
    }
}

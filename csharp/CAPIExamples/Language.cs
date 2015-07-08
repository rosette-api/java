using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using CBinding;
using System.Web.Script.Serialization;

namespace CAPIExamples
{
    class Language
    {
        static void Main()
        {
            //Language Detection
            CAPI LanguageCAPI = new CAPI("your API key");
            try
            {
                Dictionary<string, Object> LanguageResult = LanguageCAPI.Language("The quick brown fox jumped over the lazy dog. Yes he did.");
                Console.WriteLine(new JavaScriptSerializer().Serialize(LanguageResult));
            }
            catch (RosetteException e)
            {
                Console.WriteLine("Error Code " + e.Code.ToString() + ":" + e.Message);
            }
        }
    }
}
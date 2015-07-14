using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using CBinding;
using System.Web.Script.Serialization;

namespace CAPIExamples
{
    class language
    {
        static void Main()
        {
            //Example code to call Rosette API to detect possible languages for a piece of text.
            CAPI LanguageCAPI = new CAPI("your API key");
            try
            {
                Dictionary<string, Object> LanguageResult = LanguageCAPI.Language("Por favor Señorita, says the man.");
                Console.WriteLine(new JavaScriptSerializer().Serialize(LanguageResult));
            }
            catch (RosetteException e)
            {
                Console.WriteLine("Error Code " + e.Code.ToString() + ":" + e.Message);
            }
        }
    }
}
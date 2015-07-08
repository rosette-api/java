using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using CBinding;
using System.Web.Script.Serialization;

namespace CAPIExamples
{
    class MatchedName
    {
        static void Main()
        {
            //Name Matching
            CAPI MatchedNameCAPI = new CAPI("your API key");
            Name name1 = new Name();
            Name name2 = new Name();
            name1.text = "Elizabeth Doe";
            name1.language = "eng";
            name1.entityType = "PERSON";
            name2.text = "Liz Doe";
            name2.entityType = "PERSON";
            CAPI LanguageCAPI = new CAPI("your API key");
            try
            {
                Dictionary<string, Object> MatchedNameResult = MatchedNameCAPI.MatchedName(name1, name2);
                Console.WriteLine(new JavaScriptSerializer().Serialize(MatchedNameResult));
            }
            catch (RosetteException e)
            {
                Console.WriteLine("Error Code " + e.Code.ToString() + ":" + e.Message);
            }
        }
    }
}

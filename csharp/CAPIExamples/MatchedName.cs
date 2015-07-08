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
        public void MatchedNameEx()
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
            Dictionary<string, Object> MatchedNameResult = MatchedNameCAPI.MatchedName(name1, name2);
            Console.WriteLine(new JavaScriptSerializer().Serialize(MatchedNameResult));
        }
    }
}

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using CBinding;
using System.Web.Script.Serialization;

namespace CAPIExamples
{
    class matched_name
    {
        static void Main()
        {
            //Example code to call Rosette API to get match score (similarity) for two names.
            CAPI MatchedNameCAPI = new CAPI("your API key");
            try
            {
                Dictionary<string, Object> MatchedNameResult = MatchedNameCAPI.MatchedName(new Name("Elizabeth Doe", "eng", null, "PERSON"), new Name("Liz Doe", null, null, "PERSON"));
                Console.WriteLine(new JavaScriptSerializer().Serialize(MatchedNameResult));
            }
            catch (RosetteException e)
            {
                Console.WriteLine("Error Code " + e.Code.ToString() + ":" + e.Message);
            }
        }
    }
}

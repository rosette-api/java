using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using CBinding;
using System.Web.Script.Serialization;

namespace CAPIExamples
{
    class morphology_complete
    {
        static void Main()
        {
            //Morphology complete
            CAPI MorphologyCAPI = new CAPI("your API key");
            try
            {
                Dictionary<string, Object> MorphologyResult = MorphologyCAPI.Morphology("The quick brown fox jumped over the lazy dog. Yes he did.");
                Console.WriteLine(new JavaScriptSerializer().Serialize(MorphologyResult));
            }
            catch (RosetteException e)
            {
                Console.WriteLine("Error Code " + e.Code.ToString() + ":" + e.Message);
            }
        }
    }
}

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using CBinding;
using System.Web.Script.Serialization;

namespace CAPIExamples
{
    class morphology_compound_components
    {
        static void Main()
        {
            //Morphology compound-components
            CAPI MorphologyCAPI = new CAPI("your API key");
            try
            {
                Dictionary<string, Object> MorphologyResult = MorphologyCAPI.Morphology("Rechtsschutzversicherungsgesellschaften", null, null, null, null, "compound-components");
                Console.WriteLine(new JavaScriptSerializer().Serialize(MorphologyResult));
            }
            catch (RosetteException e)
            {
                Console.WriteLine("Error Code " + e.Code.ToString() + ":" + e.Message);
            }
        }
    }
}

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using CBinding;
using System.Web.Script.Serialization;

namespace CAPIExamples
{
    class morphology_parts_of_speech
    {
        static void Main()
        {
            //Morphology parts_of_speech
            CAPI MorphologyCAPI = new CAPI("your API key");
            try
            {
                Dictionary<string, Object> MorphologyResult = MorphologyCAPI.Morphology("The fact is that the geese just went back to get a rest and I'm not banking on their return soon", null, null, null, null, "parts-of-speech");
                Console.WriteLine(new JavaScriptSerializer().Serialize(MorphologyResult));
            }
            catch (RosetteException e)
            {
                Console.WriteLine("Error Code " + e.Code.ToString() + ":" + e.Message);
            }
        }
    }
}

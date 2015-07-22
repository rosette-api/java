using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Web.Script.Serialization;
using rosette_api;

namespace CAPIExamples
{
    class morphology_lemmas
    {
        static void Main()
        {
            //Example code to call Rosette API to get lemmas for words in a piece of text.
            CAPI MorphologyCAPI = new CAPI("your API key");
            try
            {
                Dictionary<string, Object> MorphologyResult = MorphologyCAPI.Morphology("The fact is that the geese just went back to get a rest and I'm not banking on their return soon", null, null, null, null, "lemmas");
                Console.WriteLine(new JavaScriptSerializer().Serialize(MorphologyResult));
            }
            catch (RosetteException e)
            {
                Console.WriteLine("Error Code " + e.Code.ToString() + ":" + e.Message);
            }
        }
    }
}
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using CBinding;
using System.Web.Script.Serialization;

namespace CAPIExamples
{
    class morphology_han_readings
    {
        static void Main()
        {
            //Morphology han-readings
            CAPI MorphologyCAPI = new CAPI("your API key");
            try
            {
                Dictionary<string, Object> MorphologyResult = MorphologyCAPI.Morphology("北京大学生物系主任办公室内部会议", null, null, null, null, "han-readings");
                Console.WriteLine(new JavaScriptSerializer().Serialize(MorphologyResult));
            }
            catch (RosetteException e)
            {
                Console.WriteLine("Error Code " + e.Code.ToString() + ":" + e.Message);
            }
        }
    }
}
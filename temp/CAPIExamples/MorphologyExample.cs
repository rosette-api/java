using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using CBinding;
using System.Web.Script.Serialization;

namespace CAPIExamples
{
    class MorphologyExample
    {
        public void MorphologyEx()
        {
            //Morphology
            CAPI MorphologyCAPI = new CAPI("your API key");
            Dictionary<string, Object> MorphologyResult = MorphologyCAPI.Morphology("The quick brown fox jumped over the lazy dog. Yes he did.");
            Console.WriteLine(new JavaScriptSerializer().Serialize(MorphologyResult));

            //Morphology
            CAPI MorphologyCAPI2 = new CAPI("your API key");
            Dictionary<string, Object> MorphologyResult2 = MorphologyCAPI2.Morphology("新华网联合国１月２２日电（记者 白洁　王湘江）第６４届联合国大会２２日一致通过决议，呼吁１９２个成员国尽快响应联合国发起的海地救援紧急募捐呼吁，强调各国应对联合国主导的救灾工作予以支持。", null, null, null, null, "han-readings");
            Console.WriteLine(new JavaScriptSerializer().Serialize(MorphologyResult2));
        }
    }
}

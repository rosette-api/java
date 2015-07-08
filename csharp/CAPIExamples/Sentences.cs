using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using CBinding;
using System.Web.Script.Serialization;

namespace CAPIExamples
{
    class Sentences
    {
        public void SentencesEx()
        {
            //Sentences
            CAPI SentencesCAPI = new CAPI("your API key");
            Dictionary<string, Object> SentencesResult = SentencesCAPI.Sentences("The quick brown fox jumped over the lazy dog. Yes he did.");
            Console.WriteLine(new JavaScriptSerializer().Serialize(SentencesResult));
        }
    }
}

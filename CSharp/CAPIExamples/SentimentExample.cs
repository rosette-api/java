using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using CBinding;
using System.Web.Script.Serialization;

namespace CAPIExamples
{
    class SentimentExample
    {
        public void SentimentEx()
        {
            //Sentiment Analysis
            CAPI SentimentCAPI = new CAPI("your API key");
            Dictionary<string, Object> SentimentResult = SentimentCAPI.Sentiment("We are looking forward to the upcoming release.");
            Console.WriteLine(new JavaScriptSerializer().Serialize(SentimentResult));
        }
    }
}

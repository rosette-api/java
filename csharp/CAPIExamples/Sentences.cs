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
        static void Main()
        {
            //Example code to call Rosette API to get sentences in a piece of text.
            CAPI SentencesCAPI = new CAPI("your API key");
            try
            {
                Dictionary<string, Object> SentencesResult = SentencesCAPI.Sentences("This land is your land This land is my land From California to the New York island; From the red wood forest to the Gulf Stream waters This land was made for you and Me. As I was walking that ribbon of highway, I saw above me that endless skyway: I saw below me that golden valley: This land was made for you and me.");
                Console.WriteLine(new JavaScriptSerializer().Serialize(SentencesResult));
            }
            catch (RosetteException e)
            {
                Console.WriteLine("Error Code " + e.Code.ToString() + ":" + e.Message);
            }
        }
    }
}

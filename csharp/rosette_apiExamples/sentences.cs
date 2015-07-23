using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Web.Script.Serialization;
using rosette_api;

namespace rosette_apiExamples
{
    class sentences
    {
        /// <summary>
        /// Example code to call Rosette API to get sentences in a piece of text.
        /// Requires Reference to:
        /// System.Net.Http (CAPI)
        /// System.Web.Extensions (JavascriptSerializer)
        /// 
        /// Requires Nuget Package:
        /// rosette_api
        /// </summary>
        static void Main()
        {
            //To use the C# API, you must provide an API key
            CAPI SentencesCAPI = new CAPI("your API key");
            try
            {
                //The results of the API call will come back in the form of a Dictionary
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

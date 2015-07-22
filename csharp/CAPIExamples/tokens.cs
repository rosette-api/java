using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Web.Script.Serialization;
using rosette_api;

namespace CAPIExamples
{
    class tokens
    {
        static void Main()
        {
            //Example code to call Rosette API to get tokens (words) in a piece of text.
            CAPI TokensCAPI = new CAPI("your API key");
            try
            {
                Dictionary<string, Object> TokensResult = TokensCAPI.Tokens("北京大学生物系主任办公室内部会议", null, null, "sentence", null);
                Console.WriteLine(new JavaScriptSerializer().Serialize(TokensResult));
            }
            catch (RosetteException e)
            {
                Console.WriteLine("Error Code " + e.Code.ToString() + ":" + e.Message);
            }
        }
    }
}
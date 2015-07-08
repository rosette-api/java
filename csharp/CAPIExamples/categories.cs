using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using CBinding;
using System.Web.Script.Serialization;

namespace CAPIExamples
{
    class categories
    {
        static void Main()
        {
            //Example code to call Rosette API to get a document's (located at given URL) category.
            CAPI CategoriesCAPI = new CAPI("your API key");
            try
            {
                Dictionary<string, Object> CategoriesResult = CategoriesCAPI.Categories(null, null, null, null, "http://www.basistech.com/about");
                Console.WriteLine(new JavaScriptSerializer().Serialize(CategoriesResult));
            }
            catch (RosetteException e)
            {
                Console.WriteLine("Error Code " + e.Code.ToString() + ":" + e.Message);
            }
        }
    }
}
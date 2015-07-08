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
            //Categorization
            CAPI CategoriesCAPI = new CAPI("your API key");
            try
            {
                Dictionary<string, Object> CategoriesResult = CategoriesCAPI.Categories("We need to spend several weeks fixing up our family tennis court.");
                Console.WriteLine(new JavaScriptSerializer().Serialize(CategoriesResult));
            }
            catch (RosetteException e)
            {
                Console.WriteLine("Error Code " + e.Code.ToString() + ":" + e.Message);
            }
        }
    }
}
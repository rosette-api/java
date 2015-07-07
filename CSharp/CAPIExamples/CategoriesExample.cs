using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using CBinding;
using System.Web.Script.Serialization;

namespace CAPIExamples
{
    class CategoriesExample
    {
        public void CategoriesEx()
        {
            //Categorization
            CAPI CategoriesCAPI = new CAPI("your API key");
            Dictionary<string, Object> CategoriesResult = CategoriesCAPI.Categories("We need to spend several weeks fixing up our family tennis court.");
            Console.WriteLine(new JavaScriptSerializer().Serialize(CategoriesResult));
        }
    }
}

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Web.Script.Serialization;
using rosette_api;

namespace rosette_apiExamples
{
    class categories
    {
        /// <summary>
        /// Example code to call Rosette API to get a document's (located at given URL) category.
        /// Requires Nuget Package:
        /// rosette_api
        /// </summary>
        static void Main()
        {
            //To use the C# API, you must provide an API key
            CAPI CategoriesCAPI = new CAPI("your API key");
            try
            {
                //The results of the API call will come back in the form of a Dictionary
                Dictionary<string, Object> CategoriesResult = CategoriesCAPI.Categories(null, null, null, null, "${categories_data}");
                Console.WriteLine(new JavaScriptSerializer().Serialize(CategoriesResult));

                //Rosette API also supports Dictionary inputs
                //Simply instantiate a new dictionary object with the fields options as keys and inputs as values
                Dictionary<string, Object> CategoriesResultDic = CategoriesCAPI.Categories(new Dictionary<object, object>()
                {
                    {"contentUri", "https://en.wikipedia.org/wiki/Basis_Technology_Corp."}

                });
                Console.WriteLine(new JavaScriptSerializer().Serialize(CategoriesResultDic));
            }
            catch (RosetteException e)
            {
                Console.WriteLine("Error Code " + e.Code.ToString() + ":" + e.Message);
            }
        }
    }
}
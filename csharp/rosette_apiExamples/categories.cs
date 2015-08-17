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
        /// Example code to call Rosette API to get a document's (located at given URL) categories.
        /// Requires Nuget Package:
        /// rosette_api
        /// </summary>
        static void Main(string[] args)
        {
            //To use the C# API, you must provide an API key
            string apikey = "Your API key";
            
            //You may set the API key via command line argument:
            //categories yourapikeyhere
            if (args.Length != 0)
            {
                apikey = args[0];
            }
            try
            {
                CAPI CategoriesCAPI = new CAPI(apikey);
                //The results of the API call will come back in the form of a Dictionary
                Dictionary<string, Object> CategoriesResult = CategoriesCAPI.Categories(null, null, null, null, "${categories_data}");
                Console.WriteLine(new JavaScriptSerializer().Serialize(CategoriesResult));

                //Rosette API also supports Dictionary inputs
                //Simply instantiate a new dictionary object with the fields options as keys and inputs as values
                Dictionary<string, Object> CategoriesResultDic = CategoriesCAPI.Categories(new Dictionary<object, object>()
            {
                {"contentUri", "${categories_data}"}

            });
                Console.WriteLine(new JavaScriptSerializer().Serialize(CategoriesResultDic));
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }
        }
    }
}
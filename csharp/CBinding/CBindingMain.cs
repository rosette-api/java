using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Web.Script.Serialization;
namespace CBinding
{
    /// <summary>Simple Main Function to test the API code
    /// </summary>
    static class CBindingMain
    {
        [STAThread]
        static void Main()
        {

            CAPI c = new CAPI("88afd6b4b18a11d1248639ecf399903c");

            //CAPI c = new CAPI("your api key");
            Dictionary<string, Object> cr = null;

            try
            {
                cr = c.Info();
                foreach (string key in cr.Keys)
                {
                    System.Diagnostics.Debug.WriteLine(key);
                    System.Diagnostics.Debug.WriteLine(cr[key].ToString());
                }
            }
            catch (RosetteException e)
            {
                System.Diagnostics.Debug.WriteLine("Error Code " + e.Code.ToString() + ": " + e.Message);
            }

            try
            {
                cr = c.Sentences(new RosetteFile("C:/Users/wspitzer/Documents/test.txt"));
                foreach (string key in cr.Keys)
                {
                    System.Diagnostics.Debug.WriteLine(cr[key].ToString());
                }
            }
            catch (RosetteException e)
            {
                System.Diagnostics.Debug.WriteLine("Error Code " + e.Code.ToString() + ": " + e.Message);
            }


            try
            {
                cr = c.MatchedName(new Name("Bob"), new Name("Joe"));
                foreach (string key in cr.Keys)
                {
                    System.Diagnostics.Debug.WriteLine(cr[key].ToString());
                }
            }
            catch (RosetteException e)
            {
                System.Diagnostics.Debug.WriteLine("Error Code " + e.Code.ToString() + ": " + e.Message);
            }
            Console.WriteLine("Press enter to close...");
            Console.ReadLine();
        }
    }
}

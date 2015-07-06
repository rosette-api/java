using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Web.Script.Serialization;
namespace CBinding
{
    static class CBindingMain
    {
        /// <summary>
        /// 
        /// </summary>

        [STAThread]
        static void Main()
        {
            //Create an API instance with key
            CAPI NewCAPI = new CAPI("your API key");
            Dictionary<string, Object> pingResult = NewCAPI.Ping();
            Console.WriteLine(new JavaScriptSerializer().Serialize(pingResult));

            //Categorization
            CAPI CategoriesCAPI = new CAPI("your API key");
            Dictionary<string, Object> CategoriesResult = CategoriesCAPI.Categories("We need to spend several weeks fixing up our family tennis court.");
            Console.WriteLine(new JavaScriptSerializer().Serialize(CategoriesResult));

            //Entity Extraction
            CAPI EntityCAPI = new CAPI("your API key");
            Dictionary<string, Object> EntityResult = EntityCAPI.Entity("The first men to reach the moon -- Mr. Armstrong and his co-pilot, Col. Edwin E. Aldrin, Jr. of the Air Force -- brought their ship to rest on a level, rock-strewn plain near the southwestern shore of the arid Sea of Tranquility.");
            Console.WriteLine(new JavaScriptSerializer().Serialize(EntityResult));

            //Entity Linking
            CAPI EntitiesLinkedCAPI = new CAPI("your API key");
            Dictionary<string, Object> EntitiesLinkedResult = EntityCAPI.EntitiesLinked("The first men to reach the moon -- Mr. Armstrong and his co-pilot, Col. Edwin E. Aldrin, Jr. of the Air Force -- brought their ship to rest on a level, rock-strewn plain near the southwestern shore of the arid Sea of Tranquility.");
            Console.WriteLine(new JavaScriptSerializer().Serialize(EntitiesLinkedResult));

            //Sentiment Analysis
            CAPI SentimentCAPI = new CAPI("your API key");
            Dictionary<string, Object> SentimentResult = SentimentCAPI.Sentiment("We are looking forward to the upcoming release.");
            Console.WriteLine(new JavaScriptSerializer().Serialize(SentimentResult));

            //Language Detection
            CAPI LanguageCAPI = new CAPI("your API key");
            Dictionary<string, Object> LanguageResult = LanguageCAPI.Language("The quick brown fox jumped over the lazy dog. Yes he did.");
            Console.WriteLine(new JavaScriptSerializer().Serialize(LanguageResult));

            //Morphology
            CAPI MorphologyCAPI = new CAPI("your API key");
            Dictionary<string, Object> MorphologyResult = MorphologyCAPI.Morphology("The quick brown fox jumped over the lazy dog. Yes he did.");
            Console.WriteLine(new JavaScriptSerializer().Serialize(MorphologyResult));

            //Morphology
            CAPI MorphologyCAPI2 = new CAPI("your API key");
            Dictionary<string, Object> MorphologyResult2 = MorphologyCAPI2.Morphology("新华网联合国１月２２日电（记者 白洁　王湘江）第６４届联合国大会２２日一致通过决议，呼吁１９２个成员国尽快响应联合国发起的海地救援紧急募捐呼吁，强调各国应对联合国主导的救灾工作予以支持。", null, null, null, null, "han-readings");
            Console.WriteLine(new JavaScriptSerializer().Serialize(MorphologyResult2));

            //Tokens
            CAPI TokensCAPI = new CAPI("your API key");
            Dictionary<string, Object> TokensResult = TokensCAPI.Tokens("The brown fox's mother jumped over 3 lazy dogs. Yes she did.");
            Console.WriteLine(new JavaScriptSerializer().Serialize(TokensResult));

            //Sentences
            CAPI SentencesCAPI = new CAPI("your API key");
            Dictionary<string, Object> SentencesResult = SentencesCAPI.Sentences("The quick brown fox jumped over the lazy dog. Yes he did.");
            Console.WriteLine(new JavaScriptSerializer().Serialize(SentencesResult));

            //Name Translation
            CAPI TranslatedNameCAPI = new CAPI("your API key");
            Dictionary<string, Object> TranslatedNameResult = TranslatedNameCAPI.TranslatedName("صفية طالب السهيل", null, null, "eng", null, null, null, "PERSON");
            Console.WriteLine(new JavaScriptSerializer().Serialize(TranslatedNameResult));

            //Name Matching
            CAPI MatchedNameCAPI = new CAPI("your API key");
            Name name1 = new Name();
            Name name2 = new Name();
            name1.text = "Elizabeth Doe";
            name1.language = "eng";
            name1.entityType = "PERSON";
            name2.text = "Liz Doe";
            name2.entityType = "PERSON";            
            Dictionary<string, Object> MatchedNameResult = MatchedNameCAPI.MatchedName(name1, name2);
            Console.WriteLine(new JavaScriptSerializer().Serialize(MatchedNameResult));

        }
    }
        /*[STAThread]
        static void Main()
        {
            CAPI c = new CAPI("88afd6b4b18a11d1248639ecf399903c");
            Dictionary<string, Object> cr = null;
            int counter = 0;

            System.Diagnostics.Debug.WriteLine("Categories");
            while (cr == null && counter < 5)
            {
                cr = c.Categories(null, "Many children aren't signed up for the KidCare program because parents don't know it exists.", null, null, null);
                System.Diagnostics.Debug.WriteLine(string.Format("Attempting to get data: {0}", counter.ToString()));
                counter = counter + 1;
            }
            if (counter < 5)
            {
                foreach (string key in cr.Keys)
                {
                    System.Diagnostics.Debug.WriteLine(string.Format(key + ": {0}", cr[key].ToString()));
                }
            }
            else
            {
                System.Diagnostics.Debug.WriteLine("Attempt failed");
            }


            cr = null;
            counter = 0;
            System.Diagnostics.Debug.WriteLine("CategoriesInfo");
            while (cr == null && counter < 5)
            {
                cr = c.CategoriesInfo();
                System.Diagnostics.Debug.WriteLine(string.Format("Attempting to get data: {0}", counter.ToString()));
                counter = counter + 1;
            }
            if (counter < 5)
            {
                foreach (string key in cr.Keys)
                {
                    System.Diagnostics.Debug.WriteLine(string.Format(key + ": {0}", cr[key].ToString()));
                }
            }
            else
            {
                System.Diagnostics.Debug.WriteLine("Attempt failed");
            }

            cr = null;
            counter = 0;
            System.Diagnostics.Debug.WriteLine("EntitiesLinked");
            while (cr == null && counter < 5)
            {
                cr = c.EntitiesLinked(null, "The quick brown fox jumped over the lazy dog. Yes he did.", null, null, null);
                System.Diagnostics.Debug.WriteLine(string.Format("Attempting to get data: {0}", counter.ToString()));
                counter = counter + 1;
            }
            if (counter < 5)
            {
                foreach (string key in cr.Keys)
                {
                    System.Diagnostics.Debug.WriteLine(string.Format(key + ": {0}", cr[key].ToString()));
                }
            }
            else
            {
                System.Diagnostics.Debug.WriteLine("Attempt failed");
            }

            cr = null;
            counter = 0;
            System.Diagnostics.Debug.WriteLine("EntitiesLinkedInfo");
            while (cr == null && counter < 5)
            {
                cr = c.EntitiesLinkedInfo();
                System.Diagnostics.Debug.WriteLine(string.Format("Attempting to get data: {0}", counter.ToString()));
                counter = counter + 1;
            }
            if (counter < 5)
            {
                foreach (string key in cr.Keys)
                {
                    System.Diagnostics.Debug.WriteLine(string.Format(key + ": {0}", cr[key].ToString()));
                }
            }
            else
            {
                System.Diagnostics.Debug.WriteLine("Attempt failed");
            }

            cr = null;
            counter = 0;
            System.Diagnostics.Debug.WriteLine("Entity");
            while (cr == null && counter < 5)
            {
                cr = c.Entity(null, "The quick brown fox jumped over the lazy dog. Yes he did.", null, null, null);
                System.Diagnostics.Debug.WriteLine(string.Format("Attempting to get data: {0}", counter.ToString()));
                counter = counter + 1;
            }
            if (counter < 5)
            {
                foreach (string key in cr.Keys)
                {
                    System.Diagnostics.Debug.WriteLine(string.Format(key + ": {0}", cr[key].ToString()));
                }
            }
            else
            {
                System.Diagnostics.Debug.WriteLine("Attempt failed");
            }

            cr = null;
            counter = 0;
            System.Diagnostics.Debug.WriteLine("EntityInfo");
            while (cr == null && counter < 5)
            {
                cr = c.EntityInfo();
                System.Diagnostics.Debug.WriteLine(string.Format("Attempting to get data: {0}", counter.ToString()));
                counter = counter + 1;
            }
            if (counter < 5)
            {
                foreach (string key in cr.Keys)
                {
                    System.Diagnostics.Debug.WriteLine(string.Format(key + ": {0}", cr[key].ToString()));
                }
            }
            else
            {
                System.Diagnostics.Debug.WriteLine("Attempt failed");
            }

            cr = null;
            counter = 0;
            System.Diagnostics.Debug.WriteLine("Info");
            while (cr == null && counter < 5)
            {
                cr = c.Info();
                System.Diagnostics.Debug.WriteLine(string.Format("Attempting to get data: {0}", counter.ToString()));
                counter = counter + 1;
            }
            if (counter < 5)
            {
                foreach (string key in cr.Keys)
                {
                    System.Diagnostics.Debug.WriteLine(string.Format(key + ": {0}", cr[key].ToString()));
                }
            }
            else
            {
                System.Diagnostics.Debug.WriteLine("Attempt failed");
            }

            cr = null;
            counter = 0;
            System.Diagnostics.Debug.WriteLine("Language");
            while (cr == null && counter < 5)
            {
                cr = c.Language(null, "The quick brown fox jumped over the lazy dog. Yes he did.", null, null, null);
                System.Diagnostics.Debug.WriteLine(string.Format("Attempting to get data: {0}", counter.ToString()));
                counter = counter + 1;
            }
            if (counter < 5)
            {
                foreach (string key in cr.Keys)
                {
                    System.Diagnostics.Debug.WriteLine(string.Format(key + ": {0}", cr[key].ToString()));
                }
            }
            else
            {
                System.Diagnostics.Debug.WriteLine("Attempt failed");
            }

            cr = null;
            counter = 0;
            System.Diagnostics.Debug.WriteLine("LanguageInfo");
            while (cr == null && counter < 5)
            {
                cr = c.LanguageInfo();
                System.Diagnostics.Debug.WriteLine(string.Format("Attempting to get data: {0}", counter.ToString()));
                counter = counter + 1;
            }
            if (counter < 5)
            {
                foreach (string key in cr.Keys)
                {
                    System.Diagnostics.Debug.WriteLine(string.Format(key + ": {0}", cr[key].ToString()));
                }
            }
            else
            {
                System.Diagnostics.Debug.WriteLine("Attempt failed");
            }


            Name n1 = new Name();
            Name n2 = new Name();
            n1.text = "Elizabeth Doe";
            n2.text = "Liz Doe";

            cr = null;
            counter = 0;
            System.Diagnostics.Debug.WriteLine("MatchedName");
            while (cr == null && counter < 5)
            {
                cr = c.MatchedName(n1, n2);
                System.Diagnostics.Debug.WriteLine(string.Format("Attempting to get data: {0}", counter.ToString()));
                counter = counter + 1;
            }
            if (counter < 5)
            {
                foreach (string key in cr.Keys)
                {
                    System.Diagnostics.Debug.WriteLine(string.Format(key + ": {0}", cr[key].ToString()));
                }
            }
            else
            {
                System.Diagnostics.Debug.WriteLine("Attempt failed");
            }

            cr = null;
            counter = 0;
            System.Diagnostics.Debug.WriteLine("MatchedNameInfo");
            while (cr == null && counter < 5)
            {
                cr = c.MatchedNameInfo();
                System.Diagnostics.Debug.WriteLine(string.Format("Attempting to get data: {0}", counter.ToString()));
                counter = counter + 1;
            }
            if (counter < 5)
            {
                foreach (string key in cr.Keys)
                {
                    System.Diagnostics.Debug.WriteLine(string.Format(key + ": {0}", cr[key].ToString()));
                }
            }
            else
            {
                System.Diagnostics.Debug.WriteLine("Attempt failed");
            }

            cr = null;
            counter = 0;
            System.Diagnostics.Debug.WriteLine("Morphology");
            while (cr == null && counter < 5)
            {
                cr = c.Morphology(null, "The quick brown fox jumped over the lazy dog. Yes he did.", null, null, null);
                System.Diagnostics.Debug.WriteLine(string.Format("Attempting to get data: {0}", counter.ToString()));
                counter = counter + 1;
            }
            if (counter < 5)
            {
                foreach (string key in cr.Keys)
                {
                    System.Diagnostics.Debug.WriteLine(string.Format(key + ": {0}", cr[key].ToString()));
                }
            }
            else
            {
                System.Diagnostics.Debug.WriteLine("Attempt failed");
            }

            cr = null;
            counter = 0;
            System.Diagnostics.Debug.WriteLine("Ping");
            while (cr == null && counter < 5)
            {
                cr = c.Ping();
                System.Diagnostics.Debug.WriteLine(string.Format("Attempting to get data: {0}", counter.ToString()));
                counter = counter + 1;
            }
            if (counter < 5)
            {
                foreach (string key in cr.Keys)
                {
                    System.Diagnostics.Debug.WriteLine(string.Format(key + ": {0}", cr[key].ToString()));
                }
            }
            else
            {
                System.Diagnostics.Debug.WriteLine("Attempt failed");
            }

            cr = null;
            counter = 0;
            System.Diagnostics.Debug.WriteLine("Sentences");
            while (cr == null && counter < 5)
            {
                cr = c.Sentences(null, "The quick brown fox jumped over the lazy dog. Yes he did.", null, null, null);
                System.Diagnostics.Debug.WriteLine(string.Format("Attempting to get data: {0}", counter.ToString()));
                counter = counter + 1;
            }
            if (counter < 5)
            {
                foreach (string key in cr.Keys)
                {
                    System.Diagnostics.Debug.WriteLine(string.Format(key + ": {0}", cr[key].ToString()));
                }
            }
            else
            {
                System.Diagnostics.Debug.WriteLine("Attempt failed");
            }

            cr = null;
            counter = 0;
            System.Diagnostics.Debug.WriteLine("Sentiment");
            while (cr == null && counter < 5)
            {
                cr = c.Sentiment(null, "The quick brown fox jumped over the lazy dog. Yes he did.", null, null, null);
                System.Diagnostics.Debug.WriteLine(string.Format("Attempting to get data: {0}", counter.ToString()));
                counter = counter + 1;
            }
            if (counter < 5)
            {
                foreach (string key in cr.Keys)
                {
                    System.Diagnostics.Debug.WriteLine(string.Format(key + ": {0}", cr[key].ToString()));
                }
            }
            else
            {
                System.Diagnostics.Debug.WriteLine("Attempt failed");
            }

            cr = null;
            counter = 0;
            System.Diagnostics.Debug.WriteLine("SentimentInfo");
            while (cr == null && counter < 5)
            {
                cr = c.SentimentInfo();
                System.Diagnostics.Debug.WriteLine(string.Format("Attempting to get data: {0}", counter.ToString()));
                counter = counter + 1;
            }
            if (counter < 5)
            {
                foreach (string key in cr.Keys)
                {
                    System.Diagnostics.Debug.WriteLine(string.Format(key + ": {0}", cr[key].ToString()));
                }
            }
            else
            {
                System.Diagnostics.Debug.WriteLine("Attempt failed");
            }

            cr = null;
            counter = 0;
            System.Diagnostics.Debug.WriteLine("Tokens");
            while (cr == null && counter < 5)
            {
                cr = c.Tokens(null, "The quick brown fox jumped over the lazy dog. Yes he did.", null, null, null);
                System.Diagnostics.Debug.WriteLine(string.Format("Attempting to get data: {0}", counter.ToString()));
                counter = counter + 1;
            }
            if (counter < 5)
            {
                foreach (string key in cr.Keys)
                {
                    System.Diagnostics.Debug.WriteLine(string.Format(key + ": {0}", cr[key].ToString()));
                }
            }
            else
            {
                System.Diagnostics.Debug.WriteLine("Attempt failed");
            }

            cr = null;
            counter = 0;
            System.Diagnostics.Debug.WriteLine("TranslatedName");
            while (cr == null && counter < 5)
            {
                cr = c.TranslatedName("Abu Bakr al-Baghdadi", null, null, "ara", null, null, "ara", "PERSON");
                System.Diagnostics.Debug.WriteLine(string.Format("Attempting to get data: {0}", counter.ToString()));
                counter = counter + 1;
            }
            if (counter < 5)
            {
                foreach (string key in cr.Keys)
                {
                    System.Diagnostics.Debug.WriteLine(string.Format(key + ": {0}", cr[key].ToString()));
                }
            }
            else
            {
                System.Diagnostics.Debug.WriteLine("Attempt failed");
            }

            cr = null;
            counter = 0;
            System.Diagnostics.Debug.WriteLine("TranslatedNameInfo");
            while (cr == null && counter < 5)
            {
                cr = c.TranslatedNameInfo();
                System.Diagnostics.Debug.WriteLine(string.Format("Attempting to get data: {0}", counter.ToString()));
                counter = counter + 1;
            }
            if (counter < 5)
            {
                foreach (string key in cr.Keys)
                {
                    System.Diagnostics.Debug.WriteLine(string.Format(key + ": {0}", cr[key].ToString()));
                }
            }
            else
            {
                System.Diagnostics.Debug.WriteLine("Attempt failed");
            }

        }
    }*/
}

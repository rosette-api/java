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
            CAPI c = new CAPI("88afd6b4b18a11d1248639ecf399903c");
            Dictionary<string, Object> cr = null;
            cr = c.Categories("الخميس 5/2/1431 هـ - الموافق 21/1/2010 م (آخر تحديث) الساعة 10:01 (مكة المكرمة)، 7:01 (غرينتش)\n\nناتو يفكر بمسؤول مدني لأفغانستان\n\nيخطط حلف شمال الأطلسي (ناتو) لتعيين مسؤول مدني كبير في أفغانستان، وسط دعوات لتحسين التنسيق السياسي والتنموي في البلاد وفق ما نقلته صحيفة وول ستريت.  وقالت الصحيفة إن السفير البريطاني في كابل في مقدمة المرشحين لهذا المنصب والذي من المحتمل أن يعلن بالتزامن مع مؤتمر دولي عن مستقبل أفغانستان المقرر عقده في لندن في 28 يناير/كانون الثاني المقبل.\n وأضافت الصحيفة -في تقرير لها من كابل- أن المسؤول الجديد سيترأس دعامة مدنية للتحالف الذي تقوده الولايات المتحدة لإدارة التمويل والمساعدات للولايات الأفغانية لتحاشي المؤسسات الأفغانية الفاسدة\".  وكان الأمين العام للأمم المتحدة بان كي مون دعا هذا الشهر لتعيين مسؤول مدني رفيع ضمن قوة المساعدة الدولية لإرساء الأمن في أفغانستان (إيساف) التي يقودها حلف الأطلسي للمساعدة في تنسيق الجهود السياسية والتنموية في الحرب التي دخلت عامها التاسع.  وأضاف أن تعيين هذا المسؤول سيتيح تحسين التنسيق بين العمل السياسي والتنموي وخصوصا عبر فرق إعادة البناء في الولايات الأفغانية.  وقالت جورنال ستريت إن المنصب الجديد سيكون نظيرا للأميركي ستانلي ماكريستال قائد القوات الأميركية وقوات حلف الأطلسي في أفغانستان.  ويتوقع وصول أربعين ألف جندي آخرين لأفغانستان في الأشهر القليلة المقبلة في إطار إستراتيجية لمجابهة العمليات المسلحة لحركة طالبان.  وأشارت الصحيفة إلى أن خطة تعيين السفير البريطاني مارك سيدويل وجدت تأييد الولايات المتحدة ومن المرجح أن يصادق عليها باقي الحلفاء.","ara", null, "doc", null);
            foreach (string key in cr.Keys)
            {
                System.Diagnostics.Debug.WriteLine(cr[key].ToString());
            }
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

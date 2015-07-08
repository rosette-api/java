using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CAPIExamples
{
    static class CAPIExamplesMain
    {
        /// <summary>
        /// 
        /// </summary>

        [STAThread]
        static void Main()
        {

            Categories cExample = new Categories();
            cExample.CategoriesEx();

            EntitiesLinked elExample = new EntitiesLinked();
            elExample.EntitiesLinkedEx();

            EntityExample eExample = new EntityExample();
            eExample.EntityEx();

            Language lExample = new Language();
            lExample.LanguageEx();

            MatchedName mnExample = new MatchedName();
            mnExample.MatchedNameEx();

            Morphology mExample = new Morphology();
            mExample.MorphologyEx();

            NewAPI nAPIExample = new NewAPI();
            nAPIExample.NewAPIEx();

            Sentences sExample = new Sentences();
            sExample.SentencesEx();

            Sentiment sExample2 = new Sentiment();
            sExample2.SentimentEx();

            Tokens tExample = new Tokens();
            tExample.TokensEx();

            TranslatedName tnExample = new TranslatedName();
            tnExample.TranslatedNameEx();
        }
    }
}

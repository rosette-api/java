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

            CategoriesExample cExample = new CategoriesExample();
            cExample.CategoriesEx();

            EntitiesLinkedExample elExample = new EntitiesLinkedExample();
            elExample.EntitiesLinkedEx();

            EntityExample eExample = new EntityExample();
            eExample.EntityEx();

            LanguageExample lExample = new LanguageExample();
            lExample.LanguageEx();

            MatchedNameExample mnExample = new MatchedNameExample();
            mnExample.MatchedNameEx();

            MorphologyExample mExample = new MorphologyExample();
            mExample.MorphologyEx();

            NewAPIExample nAPIExample = new NewAPIExample();
            nAPIExample.NewAPIEx();

            SentencesExample sExample = new SentencesExample();
            sExample.SentencesEx();

            SentimentExample sExample2 = new SentimentExample();
            sExample2.SentimentEx();

            TokensExample tExample = new TokensExample();
            tExample.TokensEx();

            TranslatedNameExample tnExample = new TranslatedNameExample();
            tnExample.TranslatedNameEx();
        }
    }
}

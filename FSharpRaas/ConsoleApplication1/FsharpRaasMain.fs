open Raas

[<EntryPoint>]
(*
Simple Main method to run F# Code without using tests
*)
let Main(_) = 

    printfn "Example of RAAS Testing"
    let Raas = new Raas()
    //let sampleText = """In Kerbala, ‭ Moustaffa Al-Assad Farid, abandoned the car and the British uniform he'd worn for safety and walked to an American base near Bucca.  Posing as a stranded motorist, Farid was given water, lunch, and a ride to the train station. ‬‬‬‬‬‬ Captain Samir Khalel, drove from Kabul to meet with Moustaffa Al-Assad Farid at the train station.  He brought along a list of potential soccer players that might join the team.""";
    //let sampleText = """当地时间7月31日，2013巴塞罗那游泳世锦赛游泳项目的第四日个比赛日上，中国选手孙杨再传捷报，他以7分41秒36的成绩问鼎男子800米自由泳冠军。这不仅是一次成功的卫冕、是他在本届世锦赛继400自之后的第二金，更是将中国代表团在游泳世锦赛历史上的第100枚金牌的荣誉留在了自己名下。""";
    let sampleText = "برج خليفة (سابقاً برج دبي)[4]، هو ناطحة سحاب ويعد أعلى بناء شيده الإنسان وأطول برج في العالم بارتفاع 828 متراً. بدأ بنائه في إمارة دبي بالإمارات العربية المتحدة في 21 سبتمبر 2004 وتم الانتهاء من الهيكلة الخارجية في الأول من أكتوبر 2009، وتم افتتاحه رسمياً في 4 يناير 2010، ليصبح البناء الأعلى في العالم حالا محل برج تايبيه 101 في تايوان. الإنشاء بدأ العمل في يناير 2004 الذي يتم بناءه في وسط دبي وبلغت تكلفته الإجمالية 1.5 مليار دولار أميركي[5]؛ وتم افتتاحه في 4 يناير 2010 بحضور الشيخ محمد بن راشد آل مكتوم حاكم دبي. ويبلغ طول البرج 828 مترا وستكون المساحة الإجمالية 4,000,000 متر مربع وسيضم 37 طابقاً كفندق ليضم 403 جناح فندقي، وسيضم 57 مصعد كهربائي وسيكون أسرعهم 10م/ثانية، وللوصول إلى 500 م تحتاج إلى 55 ثانية وتمتلكه شركة إعمار العقارية وتعد واحدة من أكبر الشركات العقارية في العالم.و قد تولت عملية البناء شركة Samsung C&T.  وهذا البرج الذي يرتفع بسرعة طابق كل ثلاث أيام (تقريبا) شكل البناء الرئيسي في مشروع عمراني ضخم بقيمة 20 مليار دولار يتوقع أن يغير ملامح المدينة.  وأوضح روبرت بوث المدير التنفيذي في الشركة الإماراتية التي تنفذ المشروع أنه سيتم استخدام المبنى لأغراض متعددة، وسيضم المبنى محلات تجارية وأماكن للترفيه وفندقاً ووحدات سكنية وأجنحة خاصة للمؤسسات وحديقة بانورامية. وتم افتتاحه في 4";

    //Using the ProcessTextStr Value
    let raasStr = Raas.ProcessTextStr(sampleText)
    printfn "String representation of the Json object: %s" raasStr

    //Using the ProcessText Value
    let raas = Raas.ProcessText(sampleText)
    printfn "Data input: %s" raas.data  
    printfn "Mentions: %i" raas.mentions.Length
    printfn "Tokens: %i" raas.tokens.Length

    printfn "Enter anything to Quit:"
    let x = System.Console.ReadLine()
    0

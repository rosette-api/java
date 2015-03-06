module Raas

open System
open System.Collections.Generic
open System.Text
open System.Net
open System.IO
open System.Web
open FSharp.Data
open FSharp.Data.HttpRequestHeaders
open Microsoft.FSharp.Control
open Microsoft.FSharp.Control.CommonExtensions
open Microsoft.FSharp.Control.WebExtensions
open Newtonsoft.Json
open ImperativeBuilder

let private ISO_Latin_1 = "ISO-8859-1"

(*
JSON parser to decipher the json from Raas server
Needs a sample json object to extract structure from
*)
type JParser = JsonProvider<"""{"requestId":"47d6c494-18e4-4372-8a04-6dde116adf5c","result":{"data":"当地时间7月31日，2013巴塞罗那游泳世锦赛游泳项目的第四日个比赛日上，中国选手孙杨再传捷报，他以7分41秒36的成绩问鼎男子800米自由泳冠军。这不仅是一次成功的卫冕、是他在本届世锦赛继400自之后的第二金，更是将中国代表团在游泳世锦赛历史上的第100枚金牌的荣誉留在了自己名下。","attributes":{"entityMentions":{"type":"list","itemType":"entityMentions","items":[{"startOffset":4,"endOffset":9,"entityType":"TEMPORAL:DATE","coreferenceChainId":0,"confidence":1.0,"source":"regex","subsource":"zhs_6","normalized":"7月31日"},{"startOffset":14,"endOffset":18,"entityType":"LOCATION","coreferenceChainId":1,"confidence":0.009252488613128662,"source":"statistical","subsource":"","normalized":"巴塞罗那"},{"startOffset":37,"endOffset":39,"entityType":"LOCATION","coreferenceChainId":2,"confidence":0.017615973949432373,"source":"statistical","subsource":"","normalized":"中国"},{"startOffset":41,"endOffset":43,"entityType":"PERSON","coreferenceChainId":3,"confidence":0.00836634635925293,"source":"statistical","subsource":"","normalized":"孙杨"},{"startOffset":50,"endOffset":52,"entityType":"TEMPORAL:TIME","coreferenceChainId":4,"confidence":1.0,"source":"regex","subsource":"zhs_21","normalized":"7分"},{"startOffset":52,"endOffset":55,"entityType":"TEMPORAL:TIME","coreferenceChainId":5,"confidence":1.0,"source":"regex","subsource":"zhs_21","normalized":"41秒"},{"startOffset":64,"endOffset":68,"entityType":"IDENTIFIER:DISTANCE","coreferenceChainId":6,"confidence":1.0,"source":"regex","subsource":"zhs_22","normalized":"800米"},{"startOffset":109,"endOffset":111,"entityType":"LOCATION","coreferenceChainId":2,"confidence":0.007920980453491211,"source":"statistical","subsource":"","normalized":"中国"}]},"token":{"type":"list","itemType":"token","items":[{"startOffset":0,"endOffset":2,"text":"当地","analyses":[{"partOfSpeech":"A","lemma":"当地","readings":["dang1-di4"]},{"partOfSpeech":"NC","lemma":"当地","readings":["dang1-di4"]}]},{"startOffset":2,"endOffset":4,"text":"时间","analyses":[{"partOfSpeech":"NC","lemma":"时间","readings":["shi2-jian1"]}]},{"startOffset":4,"endOffset":6,"text":"7月","analyses":[{"partOfSpeech":"GUESS","lemma":"7月"}]},{"startOffset":6,"endOffset":9,"text":"31日","analyses":[{"partOfSpeech":"GUESS","lemma":"31日"}]},{"startOffset":9,"endOffset":10,"text":"，","analyses":[{"partOfSpeech":"GUESS","lemma":"，"}]},{"startOffset":10,"endOffset":14,"text":"2013","analyses":[{"partOfSpeech":"GUESS","lemma":"2013"}]},{"startOffset":14,"endOffset":18,"text":"巴塞罗那","analyses":[{"partOfSpeech":"GUESS","lemma":"巴塞罗那"}]},{"startOffset":18,"endOffset":20,"text":"游泳","analyses":[{"partOfSpeech":"V","lemma":"游泳","readings":["you2-yong3"]}]},{"startOffset":20,"endOffset":23,"text":"世锦赛","analyses":[{"partOfSpeech":"GUESS","lemma":"世锦赛"}]},{"startOffset":23,"endOffset":25,"text":"游泳","analyses":[{"partOfSpeech":"V","lemma":"游泳","readings":["you2-yong3"]}]},{"startOffset":25,"endOffset":27,"text":"项目","analyses":[{"partOfSpeech":"NC","lemma":"项目","readings":["xiang4-mu4"]}]},{"startOffset":27,"endOffset":28,"text":"的","analyses":[{"partOfSpeech":"OC","lemma":"的","readings":["de0"]},{"partOfSpeech":"PL","lemma":"的","readings":["de0"]},{"partOfSpeech":"XS","lemma":"的","readings":["di0"]},{"partOfSpeech":"NC","lemma":"的","readings":["di1"]},{"partOfSpeech":"W","lemma":"的","readings":["di2"]},{"partOfSpeech":"W","lemma":"的","readings":["di4"]}]},{"startOffset":28,"endOffset":30,"text":"第四","analyses":[{"partOfSpeech":"NN","lemma":"第四","readings":["di4-si4"]}]},{"startOffset":30,"endOffset":32,"text":"日个","analyses":[{"partOfSpeech":"GUESS","lemma":"日个"}]},{"startOffset":32,"endOffset":34,"text":"比赛","analyses":[{"partOfSpeech":"NC","lemma":"比赛","readings":["bi3-sai4"]}]},{"startOffset":34,"endOffset":35,"text":"日","analyses":[{"partOfSpeech":"NA","lemma":"日","readings":["ri4"]},{"partOfSpeech":"W","lemma":"日","readings":["ri4"]}]},{"startOffset":35,"endOffset":36,"text":"上","analyses":[{"partOfSpeech":"XS","lemma":"上","readings":["shang0"]},{"partOfSpeech":"W","lemma":"上","readings":["shang3"]},{"partOfSpeech":"E","lemma":"上","readings":["shang4"]},{"partOfSpeech":"NC","lemma":"上","readings":["shang4"]},{"partOfSpeech":"OC","lemma":"上","readings":["shang4"]},{"partOfSpeech":"V","lemma":"上","readings":["shang4"]},{"partOfSpeech":"W","lemma":"上","readings":["shang4"]}]},{"startOffset":36,"endOffset":37,"text":"，","analyses":[{"partOfSpeech":"GUESS","lemma":"，"}]},{"startOffset":37,"endOffset":39,"text":"中国","analyses":[{"partOfSpeech":"NP","lemma":"中国","readings":["Zhong1-guo2"]}]},{"startOffset":39,"endOffset":41,"text":"选手","analyses":[{"partOfSpeech":"NC","lemma":"选手","readings":["xuan3-shou3"]}]},{"startOffset":41,"endOffset":42,"text":"孙","analyses":[{"partOfSpeech":"NC","lemma":"孙","readings":["sun1"]},{"partOfSpeech":"W","lemma":"孙","readings":["sun1"]}]},{"startOffset":42,"endOffset":43,"text":"杨","analyses":[{"partOfSpeech":"NC","lemma":"杨","readings":["yang2"]},{"partOfSpeech":"W","lemma":"杨","readings":["yang2"]}]},{"startOffset":43,"endOffset":44,"text":"再","analyses":[{"partOfSpeech":"D","lemma":"再","readings":["zai4"]},{"partOfSpeech":"V","lemma":"再","readings":["zai4"]}]},{"startOffset":44,"endOffset":47,"text":"传捷报","analyses":[{"partOfSpeech":"GUESS","lemma":"传捷报"}]},{"startOffset":47,"endOffset":48,"text":"，","analyses":[{"partOfSpeech":"GUESS","lemma":"，"}]},{"startOffset":48,"endOffset":49,"text":"他","analyses":[{"partOfSpeech":"NR","lemma":"他","readings":["ta1"]},{"partOfSpeech":"W","lemma":"他","readings":["ta1"]}]},{"startOffset":49,"endOffset":50,"text":"以","analyses":[{"partOfSpeech":"OC","lemma":"以","readings":["yi3"]},{"partOfSpeech":"PR","lemma":"以","readings":["yi3"]},{"partOfSpeech":"W","lemma":"以","readings":["yi3"]},{"partOfSpeech":"XS","lemma":"以","readings":["yi3"]}]},{"startOffset":50,"endOffset":52,"text":"7分","analyses":[{"partOfSpeech":"GUESS","lemma":"7分"}]},{"startOffset":52,"endOffset":54,"text":"41","analyses":[{"partOfSpeech":"GUESS","lemma":"41"}]},{"startOffset":54,"endOffset":55,"text":"秒","analyses":[{"partOfSpeech":"NM","lemma":"秒","readings":["miao3"]}]},{"startOffset":55,"endOffset":57,"text":"36","analyses":[{"partOfSpeech":"GUESS","lemma":"36"}]},{"startOffset":57,"endOffset":58,"text":"的","analyses":[{"partOfSpeech":"OC","lemma":"的","readings":["de0"]},{"partOfSpeech":"PL","lemma":"的","readings":["de0"]},{"partOfSpeech":"XS","lemma":"的","readings":["di0"]},{"partOfSpeech":"NC","lemma":"的","readings":["di1"]},{"partOfSpeech":"W","lemma":"的","readings":["di2"]},{"partOfSpeech":"W","lemma":"的","readings":["di4"]}]},{"startOffset":58,"endOffset":60,"text":"成绩","analyses":[{"partOfSpeech":"NC","lemma":"成绩","readings":["cheng2-ji4"]}]},{"startOffset":60,"endOffset":62,"text":"问鼎","analyses":[{"partOfSpeech":"V","lemma":"问鼎","readings":["wen4-ding3"]}]},{"startOffset":62,"endOffset":64,"text":"男子","analyses":[{"partOfSpeech":"NC","lemma":"男子","readings":["nan2-zi3"]}]},{"startOffset":64,"endOffset":67,"text":"800","analyses":[{"partOfSpeech":"GUESS","lemma":"800"}]},{"startOffset":67,"endOffset":68,"text":"米","analyses":[{"partOfSpeech":"NC","lemma":"米","readings":["mi3"]},{"partOfSpeech":"NM","lemma":"米","readings":["mi3"]},{"partOfSpeech":"W","lemma":"米","readings":["mi3"]}]},{"startOffset":68,"endOffset":71,"text":"自由泳","analyses":[{"partOfSpeech":"NC","lemma":"自由泳","readings":["zi4-you2-yong3"]}]},{"startOffset":71,"endOffset":73,"text":"冠军","analyses":[{"partOfSpeech":"NC","lemma":"冠军","readings":["guan4-jun1"]}]},{"startOffset":73,"endOffset":74,"text":"。","analyses":[{"partOfSpeech":"GUESS","lemma":"。"}]},{"startOffset":74,"endOffset":75,"text":"这","analyses":[{"partOfSpeech":"D","lemma":"这","readings":["zhe4"]},{"partOfSpeech":"NR","lemma":"这","readings":["zhe4"]},{"partOfSpeech":"NR","lemma":"这","readings":["zhei4"]}]},{"startOffset":75,"endOffset":77,"text":"不仅","analyses":[{"partOfSpeech":"D","lemma":"不仅","readings":["bu4-jin3"]},{"partOfSpeech":"J","lemma":"不仅","readings":["bu4-jin3"]}]},{"startOffset":77,"endOffset":78,"text":"是","analyses":[{"partOfSpeech":"A","lemma":"是","readings":["shi4"]},{"partOfSpeech":"D","lemma":"是","readings":["shi4"]},{"partOfSpeech":"NR","lemma":"是","readings":["shi4"]},{"partOfSpeech":"OC","lemma":"是","readings":["shi4"]},{"partOfSpeech":"V","lemma":"是","readings":["shi4"]},{"partOfSpeech":"W","lemma":"是","readings":["shi4"]}]},{"startOffset":78,"endOffset":79,"text":"一","analyses":[{"partOfSpeech":"NN","lemma":"1","readings":["yi1"]},{"partOfSpeech":"D","lemma":"一","readings":["yi1"]},{"partOfSpeech":"NC","lemma":"一","readings":["yi1"]},{"partOfSpeech":"OC","lemma":"一","readings":["yi1"]},{"partOfSpeech":"W","lemma":"一","readings":["yi1"]}]},{"startOffset":79,"endOffset":80,"text":"次","analyses":[{"partOfSpeech":"A","lemma":"次","readings":["ci4"]},{"partOfSpeech":"NM","lemma":"次","readings":["ci4"]},{"partOfSpeech":"W","lemma":"次","readings":["ci4"]},{"partOfSpeech":"XP","lemma":"次","readings":["ci4"]}]},{"startOffset":80,"endOffset":82,"text":"成功","analyses":[{"partOfSpeech":"NC","lemma":"成功","readings":["cheng2-gong1"]},{"partOfSpeech":"V","lemma":"成功","readings":["cheng2-gong1"]}]},{"startOffset":82,"endOffset":83,"text":"的","analyses":[{"partOfSpeech":"OC","lemma":"的","readings":["de0"]},{"partOfSpeech":"PL","lemma":"的","readings":["de0"]},{"partOfSpeech":"XS","lemma":"的","readings":["di0"]},{"partOfSpeech":"NC","lemma":"的","readings":["di1"]},{"partOfSpeech":"W","lemma":"的","readings":["di2"]},{"partOfSpeech":"W","lemma":"的","readings":["di4"]}]},{"startOffset":83,"endOffset":85,"text":"卫冕","analyses":[{"partOfSpeech":"V","lemma":"卫冕","readings":["wei4-mian3"]}]},{"startOffset":85,"endOffset":86,"text":"、","analyses":[{"partOfSpeech":"GUESS","lemma":"、"}]},{"startOffset":86,"endOffset":87,"text":"是","analyses":[{"partOfSpeech":"A","lemma":"是","readings":["shi4"]},{"partOfSpeech":"D","lemma":"是","readings":["shi4"]},{"partOfSpeech":"NR","lemma":"是","readings":["shi4"]},{"partOfSpeech":"OC","lemma":"是","readings":["shi4"]},{"partOfSpeech":"V","lemma":"是","readings":["shi4"]},{"partOfSpeech":"W","lemma":"是","readings":["shi4"]}]},{"startOffset":87,"endOffset":88,"text":"他","analyses":[{"partOfSpeech":"NR","lemma":"他","readings":["ta1"]},{"partOfSpeech":"W","lemma":"他","readings":["ta1"]}]},{"startOffset":88,"endOffset":89,"text":"在","analyses":[{"partOfSpeech":"D","lemma":"在","readings":["zai4"]},{"partOfSpeech":"OC","lemma":"在","readings":["zai4"]},{"partOfSpeech":"PR","lemma":"在","readings":["zai4"]},{"partOfSpeech":"V","lemma":"在","readings":["zai4"]}]},{"startOffset":89,"endOffset":91,"text":"本届","analyses":[{"partOfSpeech":"NC","lemma":"本届","readings":["ben3-jie4"]}]},{"startOffset":91,"endOffset":94,"text":"世锦赛","analyses":[{"partOfSpeech":"GUESS","lemma":"世锦赛"}]},{"startOffset":94,"endOffset":95,"text":"继","analyses":[{"partOfSpeech":"D","lemma":"继","readings":["ji4"]},{"partOfSpeech":"W","lemma":"继","readings":["ji4"]}]},{"startOffset":95,"endOffset":98,"text":"400","analyses":[{"partOfSpeech":"GUESS","lemma":"400"}]},{"startOffset":98,"endOffset":99,"text":"自","analyses":[{"partOfSpeech":"D","lemma":"自","readings":["zi4"]},{"partOfSpeech":"NR","lemma":"自","readings":["zi4"]},{"partOfSpeech":"PR","lemma":"自","readings":["zi4"]}]},{"startOffset":99,"endOffset":101,"text":"之后","analyses":[{"partOfSpeech":"XS","lemma":"之后","readings":["zhi1-hou4"]}]},{"startOffset":101,"endOffset":102,"text":"的","analyses":[{"partOfSpeech":"OC","lemma":"的","readings":["de0"]},{"partOfSpeech":"PL","lemma":"的","readings":["de0"]},{"partOfSpeech":"XS","lemma":"的","readings":["di0"]},{"partOfSpeech":"NC","lemma":"的","readings":["di1"]},{"partOfSpeech":"W","lemma":"的","readings":["di2"]},{"partOfSpeech":"W","lemma":"的","readings":["di4"]}]},{"startOffset":102,"endOffset":104,"text":"第二","analyses":[{"partOfSpeech":"NN","lemma":"第二","readings":["di4-er4"]}]},{"startOffset":104,"endOffset":105,"text":"金","analyses":[{"partOfSpeech":"NC","lemma":"金","readings":["jin1"]},{"partOfSpeech":"W","lemma":"金","readings":["jin1"]}]},{"startOffset":105,"endOffset":106,"text":"，","analyses":[{"partOfSpeech":"GUESS","lemma":"，"}]},{"startOffset":106,"endOffset":108,"text":"更是","analyses":[{"partOfSpeech":"GUESS","lemma":"更是"}]},{"startOffset":108,"endOffset":109,"text":"将","analyses":[{"partOfSpeech":"D","lemma":"将","readings":["jiang1"]},{"partOfSpeech":"PR","lemma":"将","readings":["jiang1"]},{"partOfSpeech":"V","lemma":"将","readings":["jiang1"]},{"partOfSpeech":"NC","lemma":"将","readings":["jiang4"]},{"partOfSpeech":"V","lemma":"将","readings":["jiang4"]},{"partOfSpeech":"W","lemma":"将","readings":["jiang4"]},{"partOfSpeech":"W","lemma":"将","readings":["qiang1"]}]},{"startOffset":109,"endOffset":111,"text":"中国","analyses":[{"partOfSpeech":"NP","lemma":"中国","readings":["Zhong1-guo2"]}]},{"startOffset":111,"endOffset":114,"text":"代表团","analyses":[{"partOfSpeech":"NC","lemma":"代表团","readings":["dai4-biao3-tuan2"]}]},{"startOffset":114,"endOffset":115,"text":"在","analyses":[{"partOfSpeech":"D","lemma":"在","readings":["zai4"]},{"partOfSpeech":"OC","lemma":"在","readings":["zai4"]},{"partOfSpeech":"PR","lemma":"在","readings":["zai4"]},{"partOfSpeech":"V","lemma":"在","readings":["zai4"]}]},{"startOffset":115,"endOffset":117,"text":"游泳","analyses":[{"partOfSpeech":"V","lemma":"游泳","readings":["you2-yong3"]}]},{"startOffset":117,"endOffset":120,"text":"世锦赛","analyses":[{"partOfSpeech":"GUESS","lemma":"世锦赛"}]},{"startOffset":120,"endOffset":122,"text":"历史","analyses":[{"partOfSpeech":"NC","lemma":"历史","readings":["li4-shi3"]}]},{"startOffset":122,"endOffset":123,"text":"上","analyses":[{"partOfSpeech":"XS","lemma":"上","readings":["shang0"]},{"partOfSpeech":"W","lemma":"上","readings":["shang3"]},{"partOfSpeech":"E","lemma":"上","readings":["shang4"]},{"partOfSpeech":"NC","lemma":"上","readings":["shang4"]},{"partOfSpeech":"OC","lemma":"上","readings":["shang4"]},{"partOfSpeech":"V","lemma":"上","readings":["shang4"]},{"partOfSpeech":"W","lemma":"上","readings":["shang4"]}]},{"startOffset":123,"endOffset":124,"text":"的","analyses":[{"partOfSpeech":"OC","lemma":"的","readings":["de0"]},{"partOfSpeech":"PL","lemma":"的","readings":["de0"]},{"partOfSpeech":"XS","lemma":"的","readings":["di0"]},{"partOfSpeech":"NC","lemma":"的","readings":["di1"]},{"partOfSpeech":"W","lemma":"的","readings":["di2"]},{"partOfSpeech":"W","lemma":"的","readings":["di4"]}]},{"startOffset":124,"endOffset":128,"text":"第100","analyses":[{"partOfSpeech":"GUESS","lemma":"第100"}]},{"startOffset":128,"endOffset":129,"text":"枚","analyses":[{"partOfSpeech":"NC","lemma":"枚","readings":["mei2"]},{"partOfSpeech":"NM","lemma":"枚","readings":["mei2"]},{"partOfSpeech":"W","lemma":"枚","readings":["mei2"]}]},{"startOffset":129,"endOffset":131,"text":"金牌","analyses":[{"partOfSpeech":"NC","lemma":"金牌","readings":["jin1-pai2"]}]},{"startOffset":131,"endOffset":132,"text":"的","analyses":[{"partOfSpeech":"OC","lemma":"的","readings":["de0"]},{"partOfSpeech":"PL","lemma":"的","readings":["de0"]},{"partOfSpeech":"XS","lemma":"的","readings":["di0"]},{"partOfSpeech":"NC","lemma":"的","readings":["di1"]},{"partOfSpeech":"W","lemma":"的","readings":["di2"]},{"partOfSpeech":"W","lemma":"的","readings":["di4"]}]},{"startOffset":132,"endOffset":134,"text":"荣誉","analyses":[{"partOfSpeech":"A","lemma":"荣誉","readings":["rong2-yu4"]},{"partOfSpeech":"NC","lemma":"荣誉","readings":["rong2-yu4"]}]},{"startOffset":134,"endOffset":135,"text":"留","analyses":[{"partOfSpeech":"V","lemma":"留","readings":["liu2"]}]},{"startOffset":135,"endOffset":136,"text":"在","analyses":[{"partOfSpeech":"D","lemma":"在","readings":["zai4"]},{"partOfSpeech":"OC","lemma":"在","readings":["zai4"]},{"partOfSpeech":"PR","lemma":"在","readings":["zai4"]},{"partOfSpeech":"V","lemma":"在","readings":["zai4"]}]},{"startOffset":136,"endOffset":137,"text":"了","analyses":[{"partOfSpeech":"PL","lemma":"了","readings":["le0"]},{"partOfSpeech":"W","lemma":"了","readings":["liao1"]},{"partOfSpeech":"W","lemma":"了","readings":["liao2"]},{"partOfSpeech":"D","lemma":"了","readings":["liao3"]},{"partOfSpeech":"E","lemma":"了","readings":["liao3"]},{"partOfSpeech":"V","lemma":"了","readings":["liao3"]},{"partOfSpeech":"W","lemma":"了","readings":["liao3"]},{"partOfSpeech":"W","lemma":"了","readings":["liao4"]}]},{"startOffset":137,"endOffset":139,"text":"自己","analyses":[{"partOfSpeech":"NR","lemma":"自己","readings":["zi4-ji3"]}]},{"startOffset":139,"endOffset":140,"text":"名","analyses":[{"partOfSpeech":"NM","lemma":"名","readings":["ming2"]},{"partOfSpeech":"W","lemma":"名","readings":["ming2"]}]},{"startOffset":140,"endOffset":141,"text":"下","analyses":[{"partOfSpeech":"D","lemma":"下","readings":["xia4"]},{"partOfSpeech":"NM","lemma":"下","readings":["xia4"]},{"partOfSpeech":"V","lemma":"下","readings":["xia4"]},{"partOfSpeech":"W","lemma":"下","readings":["xia4"]},{"partOfSpeech":"XS","lemma":"下","readings":["xia4"]}]},{"startOffset":141,"endOffset":142,"text":"。","analyses":[{"partOfSpeech":"GUESS","lemma":"。"}]}]},"languageDetection":{"type":"languageDetection","startOffset":0,"endOffset":142,"detectionResults":[{"language":"zhs","encoding":"UTF-16BE","script":"Hans","confidence":0.041046766671338886},{"language":"zht","encoding":"UTF-16BE","script":"Hant","confidence":0.020386318264433934},{"language":"jpn","encoding":"UTF-16BE","script":"Jpan","confidence":0.0033439207852082875},{"language":"ara","encoding":"UTF-16BE","script":"Arab","confidence":0.0},{"language":"ara","encoding":"UTF-16BE","script":"Latn","confidence":0.0}]},"scriptRegion":{"type":"list","itemType":"scriptRegion","items":[{"startOffset":0,"endOffset":142,"script":"Hani"}]},"sentence":{"type":"list","itemType":"sentence","items":[{"startOffset":0,"endOffset":74},{"startOffset":74,"endOffset":142}]}},"documentMetadata":{}}}""">

(*
Setup the input json object
*)
type j1 = {
    content: string
}

(*
Raas Data structures
To access the subobjects ->
RaasResult.mention.normalized to get the normalized string of the mention object

RaasResult:
    Overall Json Structure of the Raas Record
    mentions: list of mention Objects
    languageDetection: languageDetection Object
    regions: list of region Objects
    sentences: list of sentence Objects
    tokens: list of Token objects
    data: string raw text string

mention: 
    Object describing an entity mention
    confidence: decimal confidence value (between 0 and 1)
    coreferenceChainId: int Chain ID value that matches different mentions together if they refer to the same entity
    endOffset: int Offset value that describes the end location of the mention respective to the entire string
    entityType: string description of the entity type of the mention
    normalized: string normalized text of the mention
    source: string description of the method to find entity
    startOffset: int Offset value that describes the start location of the mention respective to the entire string
    subsource: string option

languageDetection:
    Object describing the language of the text input
    detectionResults: list of language Detection Results that describe the language of the text input sorted in a list from most likely language to least likely language
    endOffset: int value of the end of the text that the language Detection applies
    startOffset: int value of the start of the text that the language Detection applies

languageDetection Results:
    language with a confidence and properties
    confidence: decimal confidence value describing how likely the text matches this language (0-1)
    encoding: string encoding (e.g. UTF-16) of the text
    language: string code of the language type (e.g. zhs)
    script: string script of the language (e.g. Hans)

region:
    description of the script region
    endOffset: int Offset value that describes the end location of the text with this region respective to the entire string
    script: string script of the language (e.g. Hans)
    startOffset: int Offset value that describes the start location of the text with this region respective to the entire string


sentence:
    sentence object that describes the substring partitions of text from the initial object
    endOffset: int Offset value that describes the end location of the sentence with respective to the entire string
    startOffset: int Offset value that describes the start location of the sentence with respective to the entire string

token:
    each character or phrase in the text input
    endOffset: int Offset value that describes the end location of the token with respective to the entire string
    text: string text representation of the token
    startOffset: int Offset value that describes the end location of the token with respective to the entire string
    analysis: list of analysis options for the given token

analysis:
    analysis of the token
    lemma: string description of the lemma for this token
    partofSpeech: string description of the part of speech for this token
*)

type mention = {
    confidence: decimal
    coreferenceChainId: int
    endOffset: int
    entityType: string
    normalized: string
    source: string
    startOffset: int
    subsource: string option
}

type languageDetectionResults = {
    confidence: decimal
    encoding: string
    language: string
    script: string
}

type languageDetection = {
    mutable detectionResults: list<languageDetectionResults>
    endOffset: int
    startOffset: int
}

type region = {
    endOffset: int
    script: string
    startOffset: int
}

type sentence = {
    endOffset: int
    startOffset: int
}

type analysis = {
    lemma: string
    partofSpeech: string
}

type token = {
    endOffset: int
    text: string
    startOffset: int
    mutable analyses: list<analysis>
}

type RaasResult = {
    mutable mentions: list<mention>
    languageDetection: languageDetection
    mutable regions: list<region>
    mutable sentences: list<sentence>
    mutable tokens: list<token>
    data: string
}

(*
Start Raas body program
Stripped out all pinning aspects
*)
type Raas() = 
    let mutable _once = false
    let mutable _client:WebRequest = null
    let mutable uri = ""
    (*
    Placeholder for DEBUG mode
    *)
    let DEBUG = true
    let config = new System.Collections.Generic.Dictionary<string,string>()
    (*
    Hard coding config attributes for now
    *)
    let Raas = 
        config.["debug"] <- "http://jugmaster.basistech.net:80/rest/v1/entities/"
        config.["live"] <- "http://localhost:9020/rws/services/doc/"
        config.["timeout"] <- "120"
        config.["enabled"] <- "true" 

        if DEBUG then uri <- config.["debug"]
        else uri <- config.["live"]

    let imperative = new ImperativeBuilder()

    (*
    setBody method: encodes the input as bytes in utf-8 format
    *)
    let setBody (text:string) (webRequest:HttpWebRequest) =
        let body = Encoding.GetEncoding("utf-8").GetBytes(text)
        use requestStream = webRequest.GetRequestStream() 
        requestStream.AsyncWrite(body, 0, body.Length) |> Async.RunSynchronously

    (*
    readBody method: encodes the output as bytes in utf-8 format
    *)
    let readBody encoding (response:WebResponse) = 
        use responseStream = new StreamReader(response.GetResponseStream(), Encoding.GetEncoding("utf-8"))
        let body = responseStream.ReadToEnd()
        body
    
    (*
    callProcess method: sends the http request to the Raas server using utf-8 string representation of json object
                        and get back another utf-8 json object
    *)
    let callProcess(text:string):string= 
        let webRequest = HttpWebRequest.Create(uri + "?output=rosette") :?> HttpWebRequest
        webRequest.Method <- "POST"
        webRequest.ProtocolVersion <- HttpVersion.Version11
        webRequest.Accept <- "application/json"
        webRequest.ContentType <- "application/json"
        webRequest |> setBody text

        let response = webRequest.GetResponse()
        let body = response |> readBody "utf-8"

        body

    (*
    buildJsoninput method: Builds the json input given a string text, list of comments, and jsonResponse string.
                           Returns a string representation of the json object
    *)
    let buildJsoninput(text:string, comments:List<string>, jsonResponse:string):string=

        let j1 = {
            content = text
        }

        let jsonObject = JsonConvert.SerializeObject(j1)

        let mutable returnJson = "";
        if (comments = null && jsonResponse = null) then returnJson <- JsonConvert.DeserializeObject(jsonObject).ToString()
        returnJson
        
    (*
    processJson method: Uses the JSON parser to parse the Json response from the server
                        Returns a JsonProvider object
                        Access the json fields using .notation e.g. 
                        parsedText.Regions.[0].ResultAccess.Tokens.Strings
    *)
    let processJson(text:string) = 
        let parsedText = JParser.Parse(text)
        parsedText

    (*
    Raas.ProcessText public method: Accessable from outside files. 
                                   Takes a string and runs it through Raas and returns a Json object
                                   Input: String text
                                   Output: RaasResult Object
    *)
    member Raas.ProcessText(text:string):RaasResult= imperative{
        let emptyLD = {
            detectionResults = List.empty
            endOffset = 0
            startOffset = 0
        }
        let emptyRR = {
            mentions = List.empty
            languageDetection = emptyLD
            regions = List.empty
            sentences = List.empty
            tokens = List.empty
            data = ""
        }

        let text = text.Replace('\v', ' ').Replace('\t', ' ')

        let checkNull(value:string) = 
            match value with
            | null -> true
            | " " -> true
            | _ -> false
    
        if (checkNull text) then return emptyRR
        let input = buildJsoninput(text,null,null)
        let result = callProcess(input)
        let JResult = processJson(result)
        
        let mentions = JResult.Result.Attributes.EntityMentions.Items
        let regions = JResult.Result.Attributes.ScriptRegion.Items
        let tokens = JResult.Result.Attributes.Token.Items
        let sentences = JResult.Result.Attributes.Sentence.Items
        let languageDetection = JResult.Result.Attributes.LanguageDetection.DetectionResults     

        let lLanguageDetection = 
            seq {
                for i in languageDetection do
                    let nlanguageDetectionResults = {
                        confidence = i.Confidence
                        encoding = i.Encoding
                        language = i.Language
                        script = i.Script
                    }
                    yield nlanguageDetectionResults
            }
            |> Seq.toList

        let lMentions = 
            seq {
                for i in mentions do
                    let nMentions = {
                        confidence = decimal i.Confidence
                        coreferenceChainId = i.CoreferenceChainId
                        endOffset = i.EndOffset
                        entityType = i.EntityType
                        normalized = i.Normalized.String.Value
                        source = i.Source
                        startOffset = i.StartOffset
                        subsource = i.Subsource
                    }
                    yield nMentions
            }
            |> Seq.toList
        
        let lRegions = 
            seq {
                for i in regions do
                    let nRegions = {
                        endOffset = i.EndOffset
                        script = i.Script
                        startOffset = i.StartOffset
                    }
                    yield nRegions
            }
            |> Seq.toList

        let lSentences = 
            seq {
                for i in sentences do
                    let nSentences = {
                        sentence.endOffset = i.EndOffset
                        sentence.startOffset = i.StartOffset
                    }
                    yield nSentences
            }
            |> Seq.toList

        let lTokens = 
            seq {
                for i in tokens do
                    let analyses = i.Analyses
                    let lAnalysis = 
                        seq {
                            for z in analyses do
                                let nAnalysis = {
                                    lemma = z.Lemma.String.Value;
                                    partofSpeech = z.PartOfSpeech;
                                }
                                yield nAnalysis
                        }
                        |> Seq.toList


                    let nTokens = {
                        endOffset = i.EndOffset
                        text = i.Text.String.Value
                        startOffset = i.StartOffset
                        analyses = lAnalysis
                    }
                    yield nTokens
            }
            |> Seq.toList
        
        let languageDetectionBuilder = {
            detectionResults = lLanguageDetection
            endOffset = JResult.Result.Attributes.LanguageDetection.EndOffset
            startOffset = JResult.Result.Attributes.LanguageDetection.StartOffset        
        }

        let RaasResultBuilder = {
            mentions = lMentions
            languageDetection = languageDetectionBuilder
            regions = lRegions
            sentences = lSentences
            tokens = lTokens
            data = JResult.Result.Data
        }
        
        return RaasResultBuilder
        
    }
    (*
    Raas.ProcessText public method: Accessable from outside files. 
                                   Takes a string and runs it through Raas and returns a Json object
                                   Input: String text
                                   Output: String representation of JSON Object from Raas
    *)
    member Raas.ProcessTextStr(text:string):string = imperative{
        let text = text.Replace('\v', ' ').Replace('\t', ' ')

        let checkNull(value:string) = 
            match value with
            | null -> true
            | " " -> true
            | _ -> false
    
        if (checkNull text) then return ""
        let input = buildJsoninput(text,null,null)
        let result = callProcess(input)
        let JResult = processJson(result)
        
        let mentions = JResult.Result.Attributes.EntityMentions.Items
        let regions = JResult.Result.Attributes.ScriptRegion.Items
        let tokens = JResult.Result.Attributes.Token.Items
        let sentences = JResult.Result.Attributes.Sentence.Items
        let languageDetection = JResult.Result.Attributes.LanguageDetection.DetectionResults     

        let lLanguageDetection = 
            seq {
                for i in languageDetection do
                    let nlanguageDetectionResults = {
                        confidence = i.Confidence
                        encoding = i.Encoding
                        language = i.Language
                        script = i.Script
                    }
                    yield nlanguageDetectionResults
            }
            |> Seq.toList

        let lMentions = 
            seq {
                for i in mentions do
                    let nMentions = {
                        confidence = decimal i.Confidence
                        coreferenceChainId = i.CoreferenceChainId
                        endOffset = i.EndOffset
                        entityType = i.EntityType
                        normalized = i.Normalized.String.Value
                        source = i.Source
                        startOffset = i.StartOffset
                        subsource = i.Subsource
                    }
                    yield nMentions
            }
            |> Seq.toList
        
        let lRegions = 
            seq {
                for i in regions do
                    let nRegions = {
                        endOffset = i.EndOffset
                        script = i.Script
                        startOffset = i.StartOffset
                    }
                    yield nRegions
            }
            |> Seq.toList

        let lSentences = 
            seq {
                for i in sentences do
                    let nSentences = {
                        sentence.endOffset = i.EndOffset
                        sentence.startOffset = i.StartOffset
                    }
                    yield nSentences
            }
            |> Seq.toList

        let lTokens = 
            seq {
                for i in tokens do
                    let analyses = i.Analyses
                    let lAnalysis = 
                        seq {
                            for z in analyses do
                                let nAnalysis = {
                                    lemma = z.Lemma.String.Value;
                                    partofSpeech = z.PartOfSpeech;
                                }
                                yield nAnalysis
                        }
                        |> Seq.toList


                    let nTokens = {
                        endOffset = i.EndOffset
                        text = i.Text.String.Value
                        startOffset = i.StartOffset
                        analyses = lAnalysis
                    }
                    yield nTokens
            }
            |> Seq.toList
        
        let languageDetectionBuilder = {
            detectionResults = lLanguageDetection
            endOffset = JResult.Result.Attributes.LanguageDetection.EndOffset
            startOffset = JResult.Result.Attributes.LanguageDetection.StartOffset        
        }

        let RaasResultBuilder = {
            mentions = lMentions
            languageDetection = languageDetectionBuilder
            regions = lRegions
            sentences = lSentences
            tokens = lTokens
            data = JResult.Result.Data
        }
        
        
        return (JsonConvert.DeserializeObject(JsonConvert.SerializeObject(RaasResultBuilder)).ToString())
    }

    member Raas.printThing =
        printfn "TEST"

    member Raas.getNormalized(text:RaasResult) = 
        let mentions = text.mentions
        let lMentions = 
            seq {
                for i in mentions do
                    let nMentions = i.normalized
                    yield nMentions
            }
            |> Seq.toList

        lMentions.ToString()
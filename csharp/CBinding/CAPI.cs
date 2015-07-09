using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.Runtime.Serialization.Json;
using System.Runtime.Serialization;
using System.IO;
using System.Net.Http;
using System.Net;
using System.Web.Script.Serialization;

namespace CBinding
{
    /// <summary>C# Rosette API.
    /// <para>
    /// Primary class for interfacing with the Rosette API
    /// @copyright 2014-2015 Basis Technology Corporation.
    /// Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance
    /// with the License. You may obtain a copy of the License at @license http://www.apache.org/licenses/LICENSE-2.0
    /// Unless required by applicable law or agreed to in writing, software distributed under the License is
    /// distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    /// See the License for the specific language governing permissions and limitations under the License.
    /// </para>
    /// </summary>


    public class CAPI
    {
        private string _uri = null;
        private string _compatibleVersion = "0.5";
        private List<string> Morphofeatures = null;

        public CAPI(string user_key, string service_url = null, int maxRetry = 3)
        {
            APIkey = user_key;
            if (service_url == null)
            {
                URIstring = "https://api.rosette.com/rest/v1/";
            }
            else
            {
                URIstring = service_url;
            }
            Morphofeatures = new List<string> { "complete", "lemmas", "parts-of-speech", "compound-components", "han-readings" };
            MaxRetry = maxRetry;
            Debug = false;
            Multipart = false;
            Version_checked = false;
            Timeout = 300;
            Client = null;
            IsFirstTime = true;
        }

        public bool IsFirstTime { get; set; }
        public string APIkey { get; set; }
        public string URIstring { get; set; }
        public int MaxRetry { get; set; }
        public bool Debug { get; set; }
        public bool Multipart { get; set; }
        private bool Version_checked { get; set; }
        public int Timeout { get; set; }
        public HttpClient Client { get; set; }

        public Dictionary<string, object> Categories(string content, string language = null, string contentType = null, string unit = null, string contentUri = null)
        {
            _uri = "categories/";
            return Process(language, content, contentType, unit, contentUri);
        }

        public Dictionary<string, object> Categories(Dictionary<object, object> dict)
        {
            _uri = "categories/";
            return getResponse(SetupClient(), new JavaScriptSerializer().Serialize(dict));
        }

        public Dictionary<string, object> Categories(RosetteFile file)
        {
            _uri = "categories/";
            Multipart = true;
            return Process(file);
        }

        public Dictionary<string, object> CategoriesInfo()
        {
            _uri = "categories/info";
            return getResponse(SetupClient());
        }

        public Dictionary<string, object> EntitiesLinked(string content, string language = null, string contentType = null, string unit = null, string contentUri = null)
        {
            _uri = "entities/linked/";
            return Process(language, content, contentType, unit, contentUri);
        }

        public Dictionary<string, object> EntitiesLinked(Dictionary<object, object> dict)
        {
            _uri = "entities/linked/";
            return getResponse(SetupClient(), new JavaScriptSerializer().Serialize(dict));
        }

        public Dictionary<string, object> EntitiesLinked(RosetteFile file)
        {
            _uri = "entities/linked/";
            Multipart = true;
            return Process(file);
        }

        public Dictionary<string, object> EntitiesLinkedInfo()
        {
            _uri = "entities/linked/info";
            return getResponse(SetupClient());
        }

        public Dictionary<string, object> Entity(string content, string language = null, string contentType = null, string unit = null, string contentUri = null)
        {
            _uri = "entities/";
            return Process(language, content, contentType, unit, contentUri);
        }

        public Dictionary<string, object> Entity(Dictionary<object, object> dict)
        {
            _uri = "entities/";
            return getResponse(SetupClient(), new JavaScriptSerializer().Serialize(dict));
        }

        public Dictionary<string, object> Entity(RosetteFile file)
        {
            _uri = "entities/";
            Multipart = true;
            return Process(file);
        }

        public Dictionary<string, object> EntityInfo()
        {
            _uri = "entities/info";
            return getResponse(SetupClient());
        }

        public Dictionary<string, object> Info()
        {
            _uri = "info/";
            return getResponse(SetupClient());
        }

        public Dictionary<string, object> Language(string content, string language = null, string contentType = null, string unit = null, string contentUri = null)
        {
            _uri = "language/";
            return Process(language, content, contentType, unit, contentUri);
        }

        public Dictionary<string, object> Language(Dictionary<object, object> dict)
        {
            _uri = "language/";
            return getResponse(SetupClient(), new JavaScriptSerializer().Serialize(dict));
        }

        public Dictionary<string, object> Language(RosetteFile file)
        {
            _uri = "language/";
            Multipart = true;
            return Process(file);
        }

        public Dictionary<string, object> LanguageInfo()
        {
            _uri = "language/info";
            return getResponse(SetupClient());
        }

        public Dictionary<string, object> Morphology(string content, string language = null, string contentType = null, string unit = null, string contentUri = null, string feature = "complete")
        {
            _uri = Morphofeatures.Contains(feature) ? "morphology/" + feature : "morphology/complete";
            return Process(language, content, contentType, unit, contentUri);
        }

        public Dictionary<string, object> Morphology(Dictionary<object, object> dict, string feature = "complete")
        {
            _uri = Morphofeatures.Contains(feature) ? "morphology/" + feature : "morphology/complete";
            return getResponse(SetupClient(), new JavaScriptSerializer().Serialize(dict));
        }

        public Dictionary<string, object> Morphology(RosetteFile file, string feature = "complete")
        {
            _uri = Morphofeatures.Contains(feature) ? "morphology/" + feature : "morphology/complete";
            Multipart = true;
            return Process(file);
        }

        public Dictionary<string, object> MatchedName(Name n1, Name n2)
        {
            _uri = "matched-name/";

            return getResponse(SetupClient(), new JavaScriptSerializer().Serialize(new Dictionary<string, Name>(){
                { "name1", n1},
                { "name2", n2}
            }));
        }

        public Dictionary<string, object> MatchedName(Dictionary<object, object> dict)
        {
            _uri = "matched-name/";
            return getResponse(SetupClient(), new JavaScriptSerializer().Serialize(dict));
        }

        public Dictionary<string, object> MatchedNameInfo()
        {
            _uri = "matched-name/info";
            return getResponse(SetupClient());
        }

        public Dictionary<string, object> Ping()
        {
            _uri = "ping";
            return getResponse(SetupClient());
        }

        public Dictionary<string, object> Sentences(string content, string language = null, string contentType = null, string unit = null, string contentUri = null)
        {
            _uri = "sentences/";
            return Process(language, content, contentType, unit, contentUri);
        }

        public Dictionary<string, object> Sentences(Dictionary<object, object> dict)
        {
            _uri = "sentences/";
            return getResponse(SetupClient(), new JavaScriptSerializer().Serialize(dict));
        }

        public Dictionary<string, object> Sentences(RosetteFile file)
        {
            _uri = "sentences/";
            Multipart = true;
            return Process(file);
        }

        public Dictionary<string, object> Sentiment(string content, string language = null, string contentType = null, string unit = null, string contentUri = null)
        {
            _uri = "sentiment/";
            return Process(language, content, contentType, unit, contentUri);
        }

        public Dictionary<string, object> Sentiment(Dictionary<object, object> dict)
        {
            _uri = "sentiment/";
            return getResponse(SetupClient(), new JavaScriptSerializer().Serialize(dict));
        }

        public Dictionary<string, object> Sentiment(RosetteFile file)
        {
            _uri = "sentiment/";
            Multipart = true;
            return Process(file);
        }

        public Dictionary<string, object> SentimentInfo()
        {
            _uri = "sentiment/info";
            return getResponse(SetupClient());
        }

        public Dictionary<string, object> Tokens(string content, string language = null, string contentType = null, string unit = null, string contentUri = null)
        {
            _uri = "tokens/";
            return Process(language, content, contentType, unit, contentUri);
        }

        public Dictionary<string, object> Tokens(Dictionary<object, object> dict)
        {
            _uri = "tokens/";
            return getResponse(SetupClient(), new JavaScriptSerializer().Serialize(dict));
        }

        public Dictionary<string, object> Tokens(RosetteFile file)
        {
            _uri = "tokens/";
            Multipart = true;
            return Process(file);
        }

        public Dictionary<string, object> TranslatedName(string name, string sourceLanguageOfUse = null, string sourceScript = null, string targetLanguage = null, string targetScript = null, string targetScheme = null, string sourceLanguageOfOrigin = null, string entityType = null)
        {
            _uri = "translated-name/";

            return getResponse(SetupClient(), new JavaScriptSerializer().Serialize(new Dictionary<string, string>(){
                { "name", name},
                { "sourceLanguageOfUse", sourceLanguageOfUse},
                { "sourceScript", sourceScript},
                { "targetLanguage", targetLanguage},
                { "targetScript", targetScript},
                { "targetScheme", targetScheme},
                { "sourceLanguageOfOrigin", sourceLanguageOfOrigin},
                { "entityType", entityType}
            }));
        }

        public Dictionary<string, object> TranslatedName(Dictionary<object, object> dict)
        {
            _uri = "translated-name/";
            return getResponse(SetupClient(), new JavaScriptSerializer().Serialize(dict));
        }

        public Dictionary<string, object> TranslatedNameInfo()
        {
            _uri = "translated-name/info";
            return getResponse(SetupClient());
        }

        private Dictionary<string, Object> getResponse(HttpClient client, string jsonRequest = null)
        {
            if (client != null && checkVersion())
            {     
                HttpResponseMessage responseMsg = null;
                int retry = 0;
                string wholeURI = Debug ? _uri + "?debug=true" : _uri;

                while (responseMsg == null || (!responseMsg.IsSuccessStatusCode && retry <= MaxRetry))
                {
                    if (jsonRequest != null)
                    {
                        HttpContent content = new StringContent(jsonRequest);
                        content.Headers.ContentType = new System.Net.Http.Headers.MediaTypeHeaderValue("application/json");
                        responseMsg = client.PostAsync(wholeURI, content).Result;
                    }
                    else
                    {
                        responseMsg = client.GetAsync(wholeURI).Result;
                    }
                    retry = retry + 1;
                }

                if (responseMsg.IsSuccessStatusCode)
                {
                    byte[] byteArray = Encoding.UTF8.GetBytes(responseMsg.Content.ReadAsStringAsync().Result);
                    MemoryStream stream = new MemoryStream(byteArray);
                    StreamReader reader = new StreamReader(stream);
                    string text = reader.ReadToEnd();

                    return new JavaScriptSerializer().Deserialize<dynamic>(text);
                }
                else
                {
                    throw new RosetteException(responseMsg.ReasonPhrase, (int)responseMsg.StatusCode);
                }
                
            }
            return null;
        }

        private Dictionary<string, Object> Process(RosetteFile file)
        {
            Dictionary<string, string> dict = new Dictionary<string, string>(){
                { "content", file.getFileDataString()},
                { "contentType", file.getDataType()},
                { "unit", "doc"},
            };

            if(file.getOptions() != null){
                Dictionary<string, string> opts = new JavaScriptSerializer().Deserialize<Dictionary<string, string>>(file.getOptions());
                foreach (string key in opts.Keys){
                    if (!dict.Keys.Contains(key)){
                        dict.Add(key, opts[key]);
                    }
                }
            }

            return getResponse(SetupClient(), new JavaScriptSerializer().Serialize(dict));
        }

        private Dictionary<string, Object> Process(string content, string language = null, string contentType = null, string unit = null, string contentUri = null)
        {
            if (content == null){
                if(contentUri == null){
                    throw new RosetteException("Must supply one of Content or ContentUri", -3);
                } 
            }else{
                if (contentUri != null){
                    throw new RosetteException("Cannot supply both Content and ContentUri", -3);
                }
            }

            if (unit == null)
            {
                unit = "doc";
            }
            
            Dictionary<string, string> dict = new Dictionary<string, string>(){
                { "language", language},
                { "content", content},
                { "contentType", contentType},
                { "unit", unit},
                { "contentUri", contentUri}
            };

            return getResponse(SetupClient(), new JavaScriptSerializer().Serialize(dict));
        }

        private HttpClient SetupClient()
        {
            HttpClient client;
            if (Client == null)
            {
                client = new HttpClient();
            }
            else
            {
                client = Client;
            }
            client.BaseAddress = new Uri(URIstring);
            client.DefaultRequestHeaders.Accept.Add(new System.Net.Http.Headers.MediaTypeWithQualityHeaderValue("application/json"));
            client.DefaultRequestHeaders.Accept.Add(new System.Net.Http.Headers.MediaTypeWithQualityHeaderValue("text/javascript"));
            client.DefaultRequestHeaders.Add("user_key", APIkey);
            client.DefaultRequestHeaders.AcceptEncoding.Add(new System.Net.Http.Headers.StringWithQualityHeaderValue("gzip"));
            client.DefaultRequestHeaders.AcceptEncoding.Add(new System.Net.Http.Headers.StringWithQualityHeaderValue("deflate"));
            return client;
        }

        public bool checkVersion(string versionToCheck = null)
        {
            if (!Version_checked)
            {
                if (versionToCheck == null)
                {
                    versionToCheck = _compatibleVersion;
                }
                HttpClient client = SetupClient();
                HttpResponseMessage responseMsg = null;
                int retry = 0;

                while (responseMsg == null || (!responseMsg.IsSuccessStatusCode && retry <= MaxRetry))
                {
                    responseMsg = client.GetAsync("info/").Result;
                    retry = retry + 1;
                }
                string text = null; 
                if (responseMsg.IsSuccessStatusCode)
                {
                    byte[] byteArray = Encoding.UTF8.GetBytes(responseMsg.Content.ReadAsStringAsync().Result);
                    MemoryStream stream = new MemoryStream(byteArray);
                    StreamReader reader = new StreamReader(stream);
                    text = reader.ReadToEnd();
                }
                var result = new JavaScriptSerializer().Deserialize<dynamic>(text);
                // compatibility with server side is at minor version level of semver
                string serverVersion = result["version"].ToString();
                if (!serverVersion.Contains(versionToCheck))
                {
                    throw new RosetteException("The server version is not " + versionToCheck, -6);
                }
                else
                {
                    Version_checked = true;
                }
            }
            return Version_checked;
        }
    }

    public class RosetteException : Exception
    {
        public RosetteException(string message = null, int code = 0, string requestid = null, string file = null, string line = null)
        {
            Message = message;
            Code = code;
            RequestID = requestid;
            File = file;
            Line = line;
        }

        public string Message { get; set; }
        public int Code { get; set; }
        public string File { get; set; }
        public string Line { get; set; }
        public string RequestID { get; set; }
    }

    public class RosetteFile
    {
        private string _file;
        private string _dataType;
        private string _options;
        public RosetteFile(string file, string dataType = "application/octet-stream", string options = null)
        {
            _file = file;
            _dataType = dataType;
            _options = options;
        }

        public string getFilename()
        {
            return _file;
        }

        public string getDataType()
        {
            return _dataType;
        }

        public byte[] getFileData()
        {
            byte[] bytes = null;
            try
            {
                bytes = File.ReadAllBytes(_file);
            }
            catch (Exception e)
            {
                System.Diagnostics.Debug.WriteLine(e.ToString());
            }
            return bytes;
        }

        public string getFileDataString(){

            try
            {
                using (StreamReader ff = File.OpenText(_file))
                {
                    return System.Convert.ToBase64String(System.Text.Encoding.UTF8.GetBytes(ff.ReadToEnd()));
                }
            }
            catch (Exception e)
            {
                System.Diagnostics.Debug.WriteLine(e.ToString());
            }
            return null;
        }

        public string getOptions(){
            try{
                using (StreamReader ff = File.OpenText(_options))
                {
                    return ff.ReadToEnd();
                }
            }catch (Exception e)
            {
                System.Diagnostics.Debug.WriteLine(e.ToString());
            }
            return null;
        }
    }

    [DataContract]
    public class Name
    {
        [DataMember(Name = "text", Order = 1, IsRequired = false, EmitDefaultValue = false)]
        public string text { get; set; }

        [DataMember(Name = "language", Order = 2, IsRequired = false, EmitDefaultValue = false)]
        public string language { get; set; }

        [DataMember(Name = "script", Order = 3, IsRequired = false, EmitDefaultValue = false)]
        public string script { get; set; }

        [DataMember(Name = "entityType", Order = 4, IsRequired = false, EmitDefaultValue = false)]
        public string entityType { get; set; }
    }
}

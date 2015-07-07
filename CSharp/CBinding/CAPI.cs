using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.Runtime.Serialization.Json;
using System.Runtime.Serialization;
using System.IO;
using System.Net.Http;
using System.Web.Script.Serialization;

namespace CBinding
{
    /**
     * Api.
     *
     * Primary class for interfacing with the Rosette API
     *
     * @copyright 2014-2015 Basis Technology Corporation.
     *
     * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance
     * with the License. You may obtain a copy of the License at
     * @license http://www.apache.org/licenses/LICENSE-2.0
     *
     * Unless required by applicable law or agreed to in writing, software distributed under the License is
     * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
     * See the License for the specific language governing permissions and limitations under the License.
     **/

    public class CAPI
    {
        private string _uri = null;
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
        }

        public string APIkey { get; set; }
        public string URIstring { get; set; }
        public int MaxRetry { get; set; }
        public bool Debug { get; set; }
        public bool Multipart { get; set; }
        public bool Version_checked { get; set; }
        public int Timeout { get; set; }

        public Dictionary<string, object> Categories(string content, string language = null, string contentType = null, string unit = null, string contentUri = null)
        {
            _uri = "categories/";
            return Process(language, content, contentType, unit, contentUri);
        }

        public Dictionary<string, object> CategoriesInfo()
        {
            _uri = "categories/info";
            return Process();
        }

        public Dictionary<string, object> EntitiesLinked(string content, string language = null, string contentType = null, string unit = null, string contentUri = null)
        {
            _uri = "entities/linked/";
            return Process(language, content, contentType, unit, contentUri);
        }

        public Dictionary<string, object> EntitiesLinkedInfo()
        {
            _uri = "entities/linked/info";
            return Process();
        }

        public Dictionary<string, object> Entity(string content, string language = null, string contentType = null, string unit = null, string contentUri = null)
        {
            _uri = "entities/";
            return Process(language, content, contentType, unit, contentUri);
        }

        public Dictionary<string, object> EntityInfo()
        {
            _uri = "entities/info";
            return Process();
        }

        public Dictionary<string, object> Info()
        {
            _uri = "info/";
            return Process();
        }

        public Dictionary<string, object> Language(string content, string language = null, string contentType = null, string unit = null, string contentUri = null)
        {
            _uri = "language/";
            return Process(language, content, contentType, unit, contentUri);
        }

        public Dictionary<string, object> LanguageInfo()
        {
            _uri = "language/info";
            return Process();
        }

        public Dictionary<string, object> Morphology(string content, string language = null, string contentType = null, string unit = null, string contentUri = null, string feature = "complete")
        {
            if (Morphofeatures.Contains(feature))
            {
                _uri = "morphology/" + feature;
            }
            else
            {
                _uri = "morphology/complete";
            }
            return Process(language, content, contentType, unit, contentUri);
        }

        public Dictionary<string, object> MatchedName(Name n1, Name n2)
        {
            _uri = "matched-name/";
            return Process(n1, n2);
        }

        public Dictionary<string, object> MatchedNameInfo()
        {
            _uri = "matched-name/info";
            return Process();
        }

        public Dictionary<string, object> Ping()
        {
            _uri = "ping";
            return Process();
        }

        public Dictionary<string, object> Sentences(string content, string language = null, string contentType = null, string unit = null, string contentUri = null)
        {
            _uri = "sentences/";
            return Process(language, content, contentType, unit, contentUri);
        }

        public Dictionary<string, object> Sentiment(string content, string language = null, string contentType = null, string unit = null, string contentUri = null)
        {
            _uri = "sentiment/";
            return Process(language, content, contentType, unit, contentUri);
        }

        public Dictionary<string, object> SentimentInfo()
        {
            _uri = "sentiment/info";
            return Process();
        }

        public Dictionary<string, object> Tokens(string content, string language = null, string contentType = null, string unit = null, string contentUri = null)
        {
            _uri = "tokens/";
            return Process(language, content, contentType, unit, contentUri);
        }

        public Dictionary<string, object> TranslatedName(string name, string sourceLanguageOfUse = null, string sourceScript = null, string targetLanguage = null, string targetScript = null, string targetScheme = null, string sourceLanguageOfOrigin = null, string entityType = null)
        {
            _uri = "translated-name/";
            return Process(name, sourceLanguageOfUse, sourceScript, targetLanguage, targetScript, targetScheme, sourceLanguageOfOrigin, entityType);
        }

        public Dictionary<string, object> TranslatedNameInfo()
        {
            _uri = "translated-name/info";
            return Process();
        }

        private string buildRequest(string name, string sourceLanguageOfUse = null, string sourceScript = null, string targetLanguage = null, string targetScript = null, string targetScheme = null, string sourceLanguageOfOrigin = null, string entityType = null)
        {
            Dictionary<string, string> dict = new Dictionary<string, string>(){
                { "name", name},
                { "sourceLanguageOfUse", sourceLanguageOfUse},
                { "sourceScript", sourceScript},
                { "targetLanguage", targetLanguage},
                { "targetScript", targetScript},
                { "targetScheme", targetScheme},
                { "sourceLanguageOfOrigin", sourceLanguageOfOrigin},
                { "entityType", entityType}
            };

            return new JavaScriptSerializer().Serialize(dict);
        }

        private string buildRequest(string content, string language = null, string contentType = null, string unit = null, string contentUri = null)
        {
            Dictionary<string, string> dict = new Dictionary<string, string>(){
                { "language", language},
                { "content", content},
                { "contentType", contentType},
                { "unit", unit},
                { "contentUri", contentUri}
            };

            return new JavaScriptSerializer().Serialize(dict);
        }

        private string buildRequest(Name n1, Name n2)
        {
            Dictionary<string, Name> dict = new Dictionary<string, Name>(){
                { "name1", n1},
                { "name2", n2}
            };

            return new JavaScriptSerializer().Serialize(dict);
        }

        private Dictionary<string, Object> getResponse(HttpClient client, string jsonRequest = null)
        {
            if (client != null)
            {
                HttpResponseMessage responseMsg = null;
                client.DefaultRequestHeaders.Accept.Add(new System.Net.Http.Headers.MediaTypeWithQualityHeaderValue("application/json"));
                client.DefaultRequestHeaders.Add("user_key", APIkey);
                client.DefaultRequestHeaders.Add("timeout", Timeout.ToString());
                int retry = 0;
                string wholeURI = Debug ? _uri + "?debug=true" : _uri;

                while (responseMsg == null ||( !responseMsg.IsSuccessStatusCode && retry <= MaxRetry))
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
                try
                {
                    client.DefaultRequestHeaders.Accept.Clear();
                }
                catch (NullReferenceException)
                {
                    client = new HttpClient();
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
                    return new Dictionary<string, object>(){
                        {((int)responseMsg.StatusCode).ToString(), responseMsg.ReasonPhrase}
                    };
                }

            }
            return null;
        }

        public Dictionary<string, Object> Process(string name, string sourceLanguageOfUse = null, string sourceScript = null, string targetLanguage = null, string targetScript = null, string targetScheme = null, string sourceLanguageOfOrigin = null, string entityType = null)
        {
            HttpClient client = new HttpClient();
            client.BaseAddress = new Uri(URIstring);

            return getResponse(client, buildRequest(name, sourceLanguageOfUse, sourceScript, targetLanguage, targetScript, targetScheme, sourceLanguageOfOrigin, entityType));
        }

        public Dictionary<string, Object> Process(string content, string language = null, string contentType = null, string unit = null, string contentUri = null)
        {
            HttpClient client = new HttpClient();
            client.BaseAddress = new Uri(URIstring);

            return getResponse(client, buildRequest(language, content, contentType, unit, contentUri));
        }

        public Dictionary<string, Object> Process(Name n1 = null, Name n2 = null)
        {
            HttpClient client = new HttpClient();
            client.BaseAddress = new Uri(URIstring);

            return getResponse(client, buildRequest(n1, n2));
        }

        public Dictionary<string, Object> Process()
        {
            HttpClient client = new HttpClient();
            client.BaseAddress = new Uri(URIstring);

            return getResponse(client);
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

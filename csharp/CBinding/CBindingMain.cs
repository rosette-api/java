using System;
using System.Collections.Generic;
using System.IO;
using System.IO.Compression;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;
using System.Web.Script.Serialization;

namespace CBinding
{
    static class CBindingMain
    {
        /// <summary>
        /// Quick test on GZIP from real site
        /// </summary>
        [STAThread]
        static void Main()
        {
            
            HttpClient client =
                new HttpClient(
                    new HttpClientHandler
                    {
                        AutomaticDecompression = DecompressionMethods.GZip
                                                 | DecompressionMethods.Deflate
                    });
            
            //HttpClient client = new HttpClient();
            client.DefaultRequestHeaders.Accept.Add(new System.Net.Http.Headers.MediaTypeWithQualityHeaderValue("application/json"));
            client.DefaultRequestHeaders.Accept.Add(new System.Net.Http.Headers.MediaTypeWithQualityHeaderValue("text/javascript"));
            client.DefaultRequestHeaders.Add("user_key", "");
            client.DefaultRequestHeaders.AcceptEncoding.Add(new System.Net.Http.Headers.StringWithQualityHeaderValue("gzip"));
            client.DefaultRequestHeaders.AcceptEncoding.Add(new System.Net.Http.Headers.StringWithQualityHeaderValue("deflate"));

            Dictionary<string, string> inp = new Dictionary<string,string>(){
                {"content", "The quick brown fox jumped over the lazy dog. Yes he did."}
            };

            HttpContent content = new StringContent(new JavaScriptSerializer().Serialize(inp));
            content.Headers.ContentType = new System.Net.Http.Headers.MediaTypeHeaderValue("application/json");
            HttpResponseMessage responseMsg = client.PostAsync("https://api.rosette.com/rest/v1/morphology/complete?output=rosette", content).Result;

            System.Diagnostics.Debug.WriteLine(responseMsg.StatusCode);
            System.Diagnostics.Debug.WriteLine(responseMsg.Content.ReadAsStringAsync().Result);
            byte[] raw = responseMsg.Content.ReadAsByteArrayAsync().Result;
            MemoryStream stream = new MemoryStream(Decompress(raw));
            StreamReader reader = new StreamReader(stream);
            //System.Diagnostics.Debug.WriteLine(reader.ReadToEnd());

        }
        /// <summary>Decompress
        /// <para>Method to decompress GZIP files
        /// </para>
        /// </summary>
        /// <param name="gzip">(byte[]): Data in byte form to decompress</param>
        /// <returns>(byte[]) Decompressed data</returns>
        private static byte[] Decompress(byte[] gzip)
        {
            // Create a GZIP stream with decompression mode.
            // ... Then create a buffer and write into while reading from the GZIP stream.
            using (GZipStream stream = new GZipStream(new MemoryStream(gzip), CompressionMode.Decompress))
            {
                const int size = 4096;
                byte[] buffer = new byte[size];
                using (MemoryStream memory = new MemoryStream())
                {
                    int count = 0;
                    do
                    {
                        count = stream.Read(buffer, 0, size);
                        if (count > 0)
                        {
                            memory.Write(buffer, 0, count);
                        }
                    }
                    while (count > 0);
                    return memory.ToArray();
                }
            }
        }
    }
}

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace CBindingUnitTests
{
    [TestClass]
    public class CBindingUnitTests
    {
        [TestMethod]
        public void TestMethod1()
        {
            CMockData c = new CMockData();
            List<string> cRequests = new List<string>();
            List<string> cResponses = new List<string>();
            cRequests = c.getAllRequests();
            cResponses = c.getAllResponses();
            foreach (string s in cRequests)
            {
                if (s is string)
                {
                    string req = c.getFileData(s);

                    Assert.IsTrue(req != null);
                    Console.WriteLine(req);
                }
            }

            foreach (string s in cResponses)
            {
                if (s is string)
                {
                    string req = c.getFileData(s);

                    Assert.IsTrue(req != null);
                    Console.WriteLine(req);
                }
            }

        }
    }
}

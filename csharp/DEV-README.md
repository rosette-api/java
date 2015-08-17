Meaning and use of this repository (C# binding)
=============================

Without loading the Git repo into your file system, you can't start.  If you haven't
done that already, do so, so that this file appears in the top-level directory,
named 'csharp'.

Acquire prerequisites.
=================
- Windows Machine (currently using Windows 7)
- Visual Studios (currently using VS2013)
  - Install VS2013 or higher
  - Set up your environment so that you are using .NET 4.5 or higher
  - Has NuGet Package Manager installed already
- Doxygen is installed and doxygen is available on your `$PATH`
- nuget is available on your `$PATH`
- Set up msbuild and mstest with VCVARSALL.bat
  - Locate your VCVARSALL.bat file (ex: `C:\Program Files (x86)\Microsoft Visual Studio 12.0\VC`)
  - Run the bat file (should set up all path variables)

Run mvn
==============
No matter what your goal, you must run mvn in this directory, with no arguments.

- Maven additional flags
  - clean: removes extra files and folders
  - site: creates the documentation

- Maven variables (denoted by -Dvariablename)
  - (bool) Dpublish: whether or not to publish to NuGet (ex. -Dpublish=true)
  - (str) Dvcvars: filepath to the vcvarsall.bat file (ex. `-Dvcvars=c:\vs2013\VC`)
  - (str) Doldversion, Dnewversion: old and new versions of the api package. (ex. -Doldversion=0.5.1.3 -Dnewversion=0.5.1.4)
  
Sample full maven call with publish: `clean install site -Dpublish=true -Dvcvars=c:\vs2013\VC -Doldversion=0.5.1.3 -Dnewversion=0.5.1.4`

Doxygen documentation will be produced to the target/html subdirectory of this
directory.

To use the code where it now lives
=============
Main API code and UnitTests:

Open up csharp/rosette_api.sln using Visual Studios. Everything should be set up for you already

Examples:

Open up csharp/rosette_apiExamples/rosette_apiExamples.sln using Visual Studios. Everything should be set up for you.

Default main in Examples is categories.cs

To clean up
============
Run `mvn clean` in this directory.  Temporary output including the epydoc will
be expunged.  The virtual environments used to test will be expunged.  

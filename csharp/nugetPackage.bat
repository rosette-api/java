::Build script for CBinding
::Calls vcvarsall.bat to set the environment prior to issuing the call. The path to vcvarsall.bat must be provided
::as a command line argument.
::Should be run from the same directory as the solution
::Returns 0 if successful, 1 if no path supplied, 2 if vcvarsall.bat not found
@echo off

set APIkey=%~1
set AssemblyPath=%~2
set AssemblyName=%~3
echo API Key: %APIkey%
echo Assembly Path: %AssemblyPath%
echo Assembly Name: %AssemblyName%
if "%APIkey%"=="" goto usage1
if "%AssemblyPath%"=="" goto usage2
if "%AssemblyName%"=="" goto usage3

if not exist "%AssemblyPath%\%AssemblyName%.dll" goto noexist

nuget spec "%AssemblyPath%\%AssemblyName%.dll"
nuget pack "%AssemblyPath%\%AssemblyName%.dll.nuspec"
nuget setApiKey %APIkey%
nuget push "%AssemblyPath%\%AssemblyName%.dll.nupkg"
exit /b 0

:usage1
echo usage: %0 missing API Key
exit /b 1

:usage2
echo usage: %0 missing Assembly Path
exit /b 2

:usage3
echo usage: %0 missing Assembly Name
exit /b 3

:noexist
echo %AssemblyPath%\%AssemblyName%% is not a dll
exit /b 4

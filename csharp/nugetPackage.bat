::Build script for CBinding
::Calls vcvarsall.bat to set the environment prior to issuing the call. The path to vcvarsall.bat must be provided
::as a command line argument.
::Should be run from the same directory as the solution
::Returns 0 if successful, 1 if no path supplied, 2 if vcvarsall.bat not found
@echo off

set APIkey=%~1
set ProjectPath=%~2
set ProjectName=%~3
set version=%~4
echo API Key: %APIkey%
echo Project Path: %ProjectPath%
echo Project Name: %ProjectName%
echo Version: %version%
if "%APIkey%"=="" goto usage1
if "%ProjectPath%"=="" goto usage2
if "%ProjectName%"=="" goto usage3
if "%version%"=="" goto usage4

if not exist "%ProjectPath%\%ProjectName%.csproj" goto noexist

nuget pack "%ProjectPath%\%ProjectName%.csproj"
nuget setApiKey %APIkey%
nuget push "%ProjectPath%\%ProjectName%.%version%.nupkg"
exit /b 0

:usage1
echo usage: %0 missing API Key
exit /b 1

:usage2
echo usage: %0 missing Project Path
exit /b 2

:usage3
echo usage: %0 missing Project Name (e.g. CBinding)
exit /b 3

:usage4
echo usage: %0 missing Version number
exit /b 4

:noexist
echo "%ProjectPath%\%ProjectName%.csproj" is missing. Check to see if you have the right path and project name.
exit /b 5

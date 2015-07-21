::Build script for CBinding
::Calls vcvarsall.bat to set the environment prior to issuing the call. The path to vcvarsall.bat must be provided
::as a command line argument.
::Should be run from the same directory as the solution
::Returns 0 if successful, 1 if no path supplied, 2 if vcvarsall.bat not found
@echo off

set filepath=%~1
echo path: %filepath%
if "%filepath%"=="" goto usage

if not exist "%filepath%\vcvarsall.bat" goto noexist

call "%filepath%\vcvarsall.bat"
msbuild cbinding.sln /t:Clean /p:Configuration=Debug
exit /b 0

:usage
echo usage: %0 path_to_vcvarsall
exit /b 1

:noexist
echo vcvarsall.bat not found at %filepath%
exit /b 2

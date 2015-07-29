::Build script for CBinding
::Calls vcvarsall.bat to set the environment prior to issuing the call. The path to vcvarsall.bat must be provided
::as a command line argument.
::Should be run from the same directory as the solution
::Returns 0 if successful, 1 if no path supplied, 2 if no config mode supplied, 3 if no solnname, 4 if no testname, 5 if vcvarsall.bat not found
@echo off

set filepath=%~1
set config=%~2
set solnname=%~3
set testname=%~4
echo path: %filepath%
echo config: %config%
echo solnname: %solnname%
echo testname: %testname%
if "%filepath%"=="" goto usage1
if "%config%"=="" goto usage2
if "%solnname%"=="" goto usage3
if "%testname%"=="" goto usage4

if not exist "%filepath%\vcvarsall.bat" goto noexist

call "%filepath%\vcvarsall.bat"
msbuild %solnname%.sln /t:Rebuild /p:Configuration=%config%
mstest /testcontainer:.\%testname%\bin\%config%\%testname%.dll
exit /b 0

:usage1
echo usage: %0 path_to_vcvarsall
exit /b 1

:usage2
echo usage: %0 missing config mode
exit /b 2

:usage3
echo usage: %0 missing solnname
exit /b 3

:usage4
echo usage: %0 missing testname
exit /b 4

:noexist
echo vcvarsall.bat not found at %filepath%
exit /b 5

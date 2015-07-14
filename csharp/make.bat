:: Makefile.bat
:: Arguments are all, clean, rebuild, package.  No argument == all
@echo off
set ORIGINAL_PATH=%PATH%

:: Verify some pre-requisites
:: Validation section copied from mvn.bat
if not "%JAVA_HOME%" == "" goto OkJHome

echo.
echo ERROR: JAVA_HOME not found in your environment.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation
echo.
goto error

:OkJHome
if exist "%JAVA_HOME%\bin\java.exe" goto chkMHome

echo.
echo ERROR: JAVA_HOME is set to an invalid directory.
echo JAVA_HOME = "%JAVA_HOME%"
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation
echo.
goto error

:chkMHome
echo Validated: JAVA_HOME = "%JAVA_HOME%"
if not "%M2_HOME%"=="" goto chkMBat

echo.
echo ERROR: M2_HOME not found in your environment.
echo Please set the M2_HOME variable in your environment to match the
echo location of the Maven installation
echo.
goto error

:chkMBat
if exist "%M2_HOME%\bin\mvn.bat" goto chkAHome

echo.
echo ERROR: M2_HOME is set to an invalid directory.
echo M2_HOME = "%M2_HOME%"
echo Please set the M2_HOME variable in your environment to match the
echo location of the Maven installation
echo Ref:  http://maven.apache.org/download.cgi#Windows
echo.
goto error

:chkAHome
echo Validated: M2_HOME = "%M2_HOME%"
if not "%ANT_HOME%"=="" goto chkABat

echo.
echo ERROR: ANT_HOME not found in your environment.
echo Please set the ANT_HOME variable in your environment to match the
echo location of the Ant installation
echo.
goto error

:chkABat
if exist "%ANT_HOME%\bin\ant.bat" goto init

echo.
echo ERROR: ANT_HOME is set to an invalid directory.
echo ANT_HOME = "%ANT_HOME%"
echo Please set the ANT_HOME variable in your environment to match the
echo location of the Ant installation
echo.
goto error
:: ==== END VALIDATION ====

:init
echo Validated: ANT_HOME = "%ANT_HOME%"

set VCVARS=C:\vs2013\VC\vcvarsall.bat

:PYTHON
:: Set PYTHON
set PYTHON=C:\Python27\python

:NEXT
:: Lets get the CWD so we can use absolute paths.
set CWD=%CD%

:: Set INSTALLER to wherever the AdvancedInstaller is located
:AI
set INSTALLER=C:\Program^ Files^ (x86)\Caphyon\Advanced^ Installer^ 11.0\bin\x86\

:: Set the output directory for the Install package
:PACKAGE_PATH
set PACKAGEPATH=%CWD%\artifacts\

:RUN
:: Set MSVC environment vars
call "%VCVARS%"

IF [%1]==[] GOTO all
:: Statement passes to the label that matches the command line arg
GOTO:%~1 2>NUL

:all
msbuild TransliterationAssistant.sln /p:Configuration=Release
goto end

:clean
rmdir /s /q artifacts
call "%ANT_HOME%\bin\ant" -f "%CWD%\Doc\build.xml" -DSTAGING_DIR="%CWD%\Doc\build" -Dbt.arch=amd64-w64-msvc1000 clean
call "%ANT_HOME%\bin\ant" -f "%CWD%\doc\build.xml" -DSTAGING_DIR="%CWD%\doc\build" -Dbt.arch=amd64-w64-msvc1000 purge.doctools
rmdir /s /q Installer-cache
msbuild TransliterationAssistant.sln /p:Configuration=Release /t:Clean
rmdir /s /q "%CWD%BasisTechnologyXA\bin"
rmdir /s /q "%CWD%\BasisTechnologyXA\obj"
rmdir /s /q "%CWD%\XAforExcel\bin"
rmdir /s /q "%CWD%\XAforExcel\obj"
rmdir /s /q "%CWD%\XAforWord\bin"
rmdir /s /q "%CWD%\XAforWord\obj"
goto gazetteers-clean

:: Something screwy going on here with Ant and paths with spaces..  Avoid spaces if you want to build doc.
:doc
call "%ANT_HOME%\bin\ant" -f "%CWD%\Doc\build.xml" -DSTAGING_DIR="%CWD%\Doc\build" hlt.help -Dbt.arch=amd64-w64-msvc100
pushd %CWD%\Doc\docbook-xsl-1.78.1\webhelp
call "%ANT_HOME%\bin\ant" -f "build.xml" webhelp  -DSTAGING_DIR="%CWD%\Doc\build"
popd
call "%ANT_HOME%\bin\ant" -f "%CWD%\Doc\build.xml" -DSTAGING_DIR="%CWD%\Doc\build" doc -Dbt.arch=amd64-w64-msvc100
goto end

:gazetteers
call "%M2_HOME%\bin\mvn.bat" -B -Pxa-gazetteers
goto end

:gazetteers-clean
call "%M2_HOME%\bin\mvn.bat" -B -Pclean-gazetteers clean
goto prerequisites-clean

:prerequisites
call "%M2_HOME%\bin\mvn.bat" -B -Pxa-prerequisites
:: TODO - BR-550 - Do we still need any of this???
xcopy /i /s ..\..\xa-artifacts\java .\prerequisites\java
goto end

:prerequisites-clean
call "%M2_HOME%\bin\mvn.bat" -B -Pclean-prerequisites clean
rmdir /s /q "%CWD%\Prerequisites\32bit"
rmdir /s /q "%CWD%\Prerequisites\64bit"
goto end

:webhelp
call "%M2_HOME%\bin\mvn.bat" -B -Pdocbook-webhelp
goto end

:clean-webhelp
call "%M2_HOME%\bin\mvn.bat" -B -Pclean-docbook-webhelp clean
goto prerequisites-clean

:rebuild
::msbuild TransliterationAssistant.sln /p:Configuration=Release /t:Rebuild
goto end

:package

set PATH=%INSTALLER%;%PATH%
AdvancedInstaller /rebuild Installer-Client-32bit.aip
AdvancedInstaller /rebuild Installer-Client-32bit-Com.aip
AdvancedInstaller /rebuild Installer-Highlight-Resources-32bit.aip
AdvancedInstaller /rebuild Installer-Client-64bit.aip
AdvancedInstaller /rebuild Installer-Client-64bit-Com.aip
AdvancedInstaller /rebuild Installer-Highlight-Resources-64bit.aip
AdvancedInstaller /rebuild Highlight-Client-Prerequisites-Installer.aip
xcopy /i Scripts\*.bat artifacts
::AdvancedInstaller /rebuild Installer-Server.aip
signtool sign -f BasisCertificate.pfx -p mana4nlades -t http://timestamp.verisign.com/scripts/timstamp.dll .\artifacts\Highlight-32bit-Server-rws-7.12.1.msi
signtool sign -f BasisCertificate.pfx -p mana4nlades -t http://timestamp.verisign.com/scripts/timstamp.dll .\artifacts\Highlight-64bit-Server-rws-7.12.1.msi
goto end

:error
set ERROR_CODE=1

:end
set PATH=%ORIGINAL_PATH%
popd
cmd /C exit /B %ERROR_CODE%

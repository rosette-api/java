#!/bin/bash

#Gets called when the user doesn't provide any args
function HELP {
    echo -e "\nusage: java -cp rosette-api-examples.jar:lib/rosette-api-manifest.jar -Drosette.api.key=<API_KEY> -Drosette.api.altUrl=<ALT_URL> "
    echo "  API_KEY       - Rosette API key (required)"
    echo "  FILENAME      - Java source file (optional)"
    echo "  ALT_URL       - Alternate service URL (optional)"
    echo "Compiles and runs the source file(s) using the local development source."
    exit 1
}

#Gets API_KEY, FILENAME, ALT_URL, GIT_USERNAME and VERSION if present
while getopts ":API_KEY:FILENAME:ALT_URL" arg; do
    case "${arg}" in
        API_KEY)
            API_KEY=${OPTARG}
            usage
            ;;
        ALT_URL)
            ALT_URL=${OPTARG}
            usage
            ;;
        FILENAME)
            FILENAME=${OPTARG}
            usage
            ;;
    esac
done
ping_url="https://api.rosette.com/rest/v1"

# strip the trailing slash off of the alt_url if necessary
if [ ! -z "${ALT_URL}" ]; then
    case ${ALT_URL} in
        */) ALT_URL=${ALT_URL::-1}
            echo "Slash detected"
            ;;
    esac
    ping_url=${ALT_URL}
fi

#Checks for valid url
match=$(curl "${ping_url}/ping" -H "X-RosetteAPI-Key: ${API_KEY}" |  grep -o "Rosette API")
if [ "${match}" = "" ]; then
    echo -e "\n${ping_url} server not responding\n"
    exit 1
fi  

#Checks if Rosette API key is valid
function checkAPI {
    match=$(curl "https://api.rosette.com/rest/v1/ping" -H "X-RosetteAPI-Key: ${API_KEY}" |  grep -o "forbidden")
    if [ ! -z $match ]; then
        echo -e "\nInvalid Rosette API Key"
        exit 1
    fi  
}

function runExample() {
    echo -e "\n---------- ${1} start -------------\n"
    if [ ! -z ${ALT_URL} ]; then
        result="$(mvn exec:java -Dexec.mainClass=com.basistech.rosette.examples.${1} -Drosette.api.key=${API_KEY} -Drosette.api.altUrl=${ALT_URL} 2>&1 )"
    else
        result="$(mvn exec:java -Dexec.mainClass=com.basistech.rosette.examples.${1} -Drosette.api.key=${API_KEY} 2&>1 )"
    fi
    if [[ $result == *"Exception"* ]]; then
        retcode=1
    fi
    echo ${result}
    echo -e "\n---------- ${1} end -------------\n"
}

#Copy the mounted content in /source to current WORKDIR
cp -r -u /source/* .

#Set the global return code.  If any of the examples fails, this will be set to 1 for the exit
retcode=0
result=""

#Run the examples

if [ ! -z ${API_KEY} ]; then
    checkAPI
    mvn install -DskipTests=true -Dmaven.javadoc.skip=true -B -V
    cd /java/examples
    if [ ! -z ${FILENAME} ]; then
        runExample ${FILENAME}
    else
        for file in /java/examples/src/main/java/com/basistech/rosette/examples/*.java; do
            filename=$(basename "$file")
            filename="${filename%.*}"
            if [ "${filename}" = "ExampleBase" ]; then
                continue
            fi
            runExample ${filename}
        done
    fi
else 
    HELP
fi

exit ${retcode}

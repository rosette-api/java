#!/bin/bash

retcode=0
ping_url="https://api.rosette.com/rest/v1"
errors=( "Exception" "processingFailure" "badRequest" "AttributeError" "ImportError" )

#------------------ Functions ----------------------------------------------------

function usage() { echo "Usage: $0 API_KEY VERSION [ALT_URL] [FILENAME]" 1>&2; exit 1; }

#Checks if Rosette API key is valid
function checkAPI {
    match=$(curl "${ping_url}/ping" -H "X-RosetteAPI-Key: ${API_KEY}" |  grep -o "forbidden")
    if [ ! -z $match ]; then
        echo -e "\nInvalid Rosette API Key"
        exit 1
    fi
}

function cleanURL() {
    # strip the trailing slash off of the alt_url if necessary
    if [ ! -z "${ALT_URL}" ]; then
        case ${ALT_URL} in
            */) ALT_URL=${ALT_URL::-1}
                echo "Slash detected"
                ;;
        esac
        ping_url=${ALT_URL}
    fi
}

function validateURL() {
    match=$(curl "${ping_url}/ping" -H "X-RosetteAPI-Key: ${API_KEY}" |  grep -o "Rosette API")
    if [ "${match}" = "" ]; then
        echo -e "\n${ping_url} server not responding\n"
        exit 1
    fi
}

function runExample() {
    echo -e "\n---------- ${1} start -------------\n"
    if [ ! -z ${ALT_URL} ]; then
        result="$(mvn exec:java -B -Dexec.mainClass=com.basistech.rosette.examples.${1} -Drosette.api.key=${API_KEY} -Drosette.api.altUrl=${ALT_URL} 2>&1 )"
    else
        result="$(mvn exec:java -B -Dexec.mainClass=com.basistech.rosette.examples.${1} -Drosette.api.key=${API_KEY} 2&>1 )"
    fi
    if [[ $result == *"Exception"* ]]; then
        retcode=1
    fi
    echo "${result}"
    echo -e "\n---------- ${1} end -------------\n"
    for err in "${errors[@]}"; do
        if [[ ${result} == *"${err}"* ]]; then
            retcode=1
        fi
    done
}

# Updates the given pom files to use the publish version
function assignVersion() {
    sed -i "s|\(<version>\).*SNAPSHOT\(</version>\)|\1${VERSION}\2|" ${1}
}
#------------------ Functions End ------------------------------------------------


#Gets API_KEY, FILENAME, ALT_URL, GIT_USERNAME and VERSION if present
while getopts ":API_KEY:FILENAME:ALT_URL:VERSION" arg; do
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
        VERSION)
            VERSION=${OPTARG}
            usage
            ;;
    esac
done

cleanURL

validateURL

#Copy the mounted content in /source to current WORKDIR
cp -r -n /source/examples .
cp /source/examples/docker/main/pom.xml .

assignVersion pom.xml
assignVersion examples.pom.xml

#Run the examples

if [ ! -z ${API_KEY} ]; then
    checkAPI
    mvn install -DskipTests=true -Dmaven.javadoc.skip=true -B -V -pl examples -pl examples
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
    usage
fi

exit ${retcode}

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

#Checks if Rosette API key is valid
function checkAPI {
    match=$(curl "https://api.rosette.com/rest/v1/ping" -H "user_key: ${API_KEY}" |  grep -o "forbidden")
    if [ ! -z $match ]; then
        echo -e "\nInvalid Rosette API Key"
        exit 1
    fi  
}

#Copy the mounted content in /source to current WORKDIR
cp -r -n /source/* .

#Run the examples
if [ ! -z ${API_KEY} ]; then
    checkAPI
    mvn install -DskipTests=true -Dmaven.javadoc.skip=true -B -V
    cd /java/examples
    if [ ! -z ${FILENAME} ]; then
        if [ ! -z ${ALT_URL} ]; then
            echo -e "\n---------- ${FILENAME} start -------------"
            mvn exec:java -Dexec.mainClass="com.basistech.rosette.examples.${FILENAME}" -Drosette.api.key=${API_KEY} -Drosette.api.altUrl=${ALT_URL}
            echo "---------- ${FILENAME} end -------------"
        else
            echo -e "\n---------- ${FILENAME} start -------------"
            mvn exec:java -Dexec.mainClass="com.basistech.rosette.examples.${FILENAME}" -Drosette.api.key=${API_KEY}
            echo "---------- ${FILENAME} end -------------"
        fi
    elif [ ! -z ${ALT_URL} ]; then
        for file in /java/examples/src/main/java/com/basistech/rosette/examples/*.java; do
            filename=$(basename "$file")
            filename="${filename%.*}"
            echo -e "\n---------- ${filename} start -------------"
            mvn exec:java -Dexec.mainClass="com.basistech.rosette.examples.${filename}" -Drosette.api.key=${API_KEY} -Drosette.api.altUrl=${ALT_URL} 
            echo "---------- ${filename} end -------------"
        done
    else
        for file in /java/examples/src/main/java/com/basistech/rosette/examples/*.java; do
            filename=$(basename "$file")
            filename="${filename%.*}"
            echo -e "\n---------- ${filename} start -------------"
            mvn exec:java -Dexec.mainClass="com.basistech.rosette.examples.${filename}" -Drosette.api.key=${API_KEY}                
            echo "---------- ${filename} end -------------"
        done
    fi
else 
    HELP
fi

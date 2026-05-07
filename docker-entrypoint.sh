#!/usr/bin/env bash

retcode=0
failed_examples=()
service_url="https://analytics.babelstreet.com/rest/v1"
errors=( "Exception" "processingFailure" "badRequest" "ParseError" "ValueError" "SyntaxError" "AttributeError" "ImportError" "Timeout")

usage() {
    echo -e "\nusage: $0 --key API_KEY [--url ALT_URL]"
    echo "  API_KEY       - Babel Street API key (required)"
    echo "  FILENAME      - source file (optional)"
    echo "  ALT_URL       - Alternate service URL (optional)"
    echo "Compiles and runs the source file(s) using the local development source."
    exit 1
}

if [ -n "${ALT_URL}" ]; then
    service_url="${ALT_URL}"
fi

cleanURL() {
    # strip the trailing slash off of the alt_url if necessary
    if [ ! -z "${ALT_URL}" ]; then
        case ${ALT_URL} in
            */) ALT_URL=${ALT_URL::-1}
                echo "Slash detected"
                ;;
        esac
        service_url=${ALT_URL}
    fi
}

checkAPIKey() {
  output_file=check_key_out.log
  http_status_code=$(curl -s -o "${output_file}" -w "%{http_code}" -H "X-BabelStreetAPI-Key: ${API_KEY}" "${service_url}/ping")
  if [ "${http_status_code}" = "403" ]; then
      echo -e "\nInvalid Babel Street Analytics API key.  Output is:\n"
      cat "${output_file}"
      exit 1
  fi
}

validateURL() {
    output_file=validate_url_out.log
    http_status_code=$(curl -s -o "${output_file}" -w "%{http_code}" -H "X-BabelStreetAPI-Key: ${API_KEY}" "${service_url}/ping")
    if [ "${http_status_code}" != "200" ]; then
        echo -e "\n${service_url} server not responding.  Output is:\n"
        cat "${output_file}"
        exit 1
    fi
}

runExample() {
    echo -e "\n---------- ${1} start -------------"
    result=""
    if [ -z "${ALT_URL}" ]; then
        filename=$(basename "${1}")
        filename="${filename%.*}"
        if [ "${filename}" != "ExampleBase" ]; then
            pushd /java/examples
            result="$(mvn -ntp exec:java -Dexec.mainClass=com.basistech.rosette.examples.${filename} -Danalytics.api.key=${API_KEY} 2>&1 )"
            popd
        fi
    else
        filename=$(basename "${1}")
        filename="${filename%.*}"
        if [ "${filename}" != "ExampleBase" ]; then
            pushd /java/examples
            result="$(mvn -ntp exec:java -Dexec.mainClass=com.basistech.rosette.examples.${filename} -Danalytics.api.key=${API_KEY} -Danalytics.api.altUrl=${ALT_URL} 2>&1 )"
            popd
        fi
    fi
    echo "${result}"
    echo -e "\n---------- ${1} end -------------"

    local has_failed=false
    for err in "${errors[@]}"; do
        if [[ ${result} == *"${err}"* ]]; then
            if [ "${filename}" = "ConcurrencyExample" ]; then
                echo "Note: ${1} failed (as expected)."
            else
                retcode=1
                has_failed=true
            fi
            break
        fi
    done

    if [ "${has_failed}" = true ]; then
        failed_examples+=("${1}")
    fi
}

while [[ "$#" -gt 0 ]]; do
    case "$1" in
        --key)
            API_KEY="$2"
            shift 2
            ;;
        --url)
            ALT_URL="$2"
            shift 2
            ;;
        --filename)
            FILENAME="$2"
            shift 2
            ;;
        *)
            usage
            ;;
    esac
done

cp -r -n /source/. .

cleanURL

validateURL

mvn -ntp install -DskipTests=true -Dmaven.javadoc.skip=true -Dcheckstyle.skip=true -Dpmd.skip=true -B -V

#Run the examples
if [ -n "${API_KEY}" ]; then
    checkAPIKey
    if [ -n "${FILENAME}" ]; then
        echo -e "\nRunning example against: ${service_url}\n"
        runExample "${FILENAME}"
    else
        echo -e "\nRunning examples against: ${service_url}\n"
        pushd "/java/examples/src/main/java/com/basistech/rosette/examples" > /dev/null
        for file in *.java; do
            runExample "${file}"
        done
        popd > /dev/null
    fi
else
    usage
fi

if [[ ${#failed_examples[@]} -gt 0 ]]; then
    echo -e "\n=============================================="
    echo "Failed Examples Summary:"
    for failed in "${failed_examples[@]}"; do
        echo "  - ${failed}"
    done
    echo "=============================================="
else
    if [ "${retcode}" -eq 0 ] && [ -n "${API_KEY}" ]; then
        echo -e "\n=============================================="
        echo "All examples completed successfully."
        echo "=============================================="
    fi
fi

exit "${retcode}"

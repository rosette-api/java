if [ $# -eq 0 ]; then
   echo "Must have API key to run example"
   exit 1
fi

curl "https://api.rosette.com/rest/v1/categories" \
  -H 'user_key: "88afd6b4b18a11d1248639ecf399903c"' \
  -H 'Content-Type:application/json' \
  -H 'Accept:application/json' \
  -d '{"contentUri": "${categories_data}"}'
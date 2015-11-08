apikey = $1

if [ -z "$apikey" ]; then
   echo "Must have API key to run example"
   exit 1
fi

curl "https://api.rosette.com/rest/v1/categories" \
  -H 'user_key: [your_api-key]' \
  -H 'Content-Type:application/json' \
  -H 'Accept:application/json' \
  -d '{"contentUri": "${categories_data}"}'
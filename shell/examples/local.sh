if [ $# -eq 0 ]; then
   echo "Must have API key to run example"
   exit 1
fi

curl "https://api.rosette.com/rest/v1/sentences" \
  -H 'user_key: $1' \
  -H 'ContentType:multipart/form-data' \
  -H 'Accept:application/json' \
  -F content=@$2
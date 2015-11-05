curl "https://api.rosette.com/rest/v1/sentences" \
  -H'user_key: [your_api-key]' \
  -H 'ContentType:multipart/form-data' \
  -H 'Accept:application/json' \
  -F content=@/path/to/data/fox.txt
curl "https://api.rosette.com/rest/v1/translated-name" \
  -H'user_key: [your_api-key]' \
  -H'Content-Type:application/json' \
  -H'Accept:application/json' \
  -d'{"name": "${translated_name_data}" "targetLanguage": "eng"}'
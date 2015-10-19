curl "https://api.rosette.com/rest/v1/sentences" \
  -H 'user_key: [your_api-key]' \
  -H 'Content-Type:application/json' \
  -H 'Accept:application/json' \
  -d '{"content": "${sentences_data}"}'
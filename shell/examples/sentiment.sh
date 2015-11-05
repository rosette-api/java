curl "https://api.rosette.com/rest/v1/sentiment" \
  -H 'user_key: [your_api-key]' \
  -H 'Content-Type:application/json' \
  -H 'Accept:application/json' \
  -d '{"content": "${sentiment_data}"}'
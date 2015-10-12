curl "https://api.rosette.com/rest/v1/morphology/han-readings" \
  -H'user_key: [your_api-key]' \
  -H'Content-Type:application/json' \
  -H'Accept:application/json' \
  -d'{"content": "${morphology_han_readings_data}"}'
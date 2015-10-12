curl "https://api.rosette.com/rest/v1/categories" \
  -H'user_key: [your_api-key]' \
  -H'Content-Type:application/json' \
  -H'Accept:application/json' \
  -d'{"contentUri": "${categories_data}"}'
curl "https://api.rosette.com/rest/v1/matched-name"
  -H'user_key: [your_api-key]' \
  -H'Content-Type:application/json' \
  -H'Accept:application/json' \
  -d'{"name1": {"text": "${matched_name_data1}"}, "name2": {"text": "${matched_name_data2}"} }'
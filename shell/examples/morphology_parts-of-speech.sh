curl "https://api.rosette.com/rest/v1/morphology/parts-of-speech" \
  -H'user_key: [your_api-key]' \
  -H'Content-Type:application/json' \
  -H'Accept:application/json' \
  -d'{"content": "${morphology_parts_of_speech_data}"}'
curl "https://api.rosette.com/rest/v1/morphology/compound-components" \
  -H'user_key: [your_api-key]' \
  -H'Content-Type:application/json' \
  -H'Accept:application/json' \
  -d'{"content": "${morphology_compound_components_data}"}'
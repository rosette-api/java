#!/usr/bin/env ruby

require "net/http"
require "net/https"
require "json"

uri = URI.parse("https://api.rosette.com/rest/v1/info")
http = Net::HTTP.new(uri.host, uri.port)
http.use_ssl = true if uri.scheme == 'https'

request = Net::HTTP::Get.new(uri.request_uri)
request["user_key"] = "[your_api-key]"

response = http.request(request)

puts JSON.pretty_generate(JSON.parse(response.body))
# -*- coding: utf-8 -*-

"""
Example code to call Rosette API to get sentences in a piece of text.
"""

import argparse
import json

from rosette.api import API, DocumentParameters

parser = argparse.ArgumentParser(description="Get sentences from a piece of text")
parser.add_argument("--key", required=True, help="Rosette API key")
parser.add_argument("--service_url", nargs="?", help="Optional user service URL")
args = parser.parse_args()

# Create an API instance
if args.service_url:
    api = API(service_url=args.service_url, user_key=args.key)
else:
    api = API(user_key=args.key)

params = DocumentParameters()
params["content"] = u"${sentences_data}"

result = api.sentences(params)

print(json.dumps(result, indent=2, ensure_ascii=False))

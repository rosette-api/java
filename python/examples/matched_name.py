# -*- coding: utf-8 -*-

'''
Example code to call Rosette API to get match score (similarity) of two names.
'''

import argparse
import pprint
import sys

# enable imports from rosette.api
sys.path += '../../'

from rosette.api import API, RniParameters

parser = argparse.ArgumentParser(description='Accept Rosette API key')
parser.add_argument('--key', required=True, help='Rosette API key')
parser.add_argument('--service_url', nargs='?', help='Optional user service URL')
args = parser.parse_args()

# Create an API instance
if args.service_url:
    api = API(service_url=args.service_url, user_key=args.key)
else:
    api = API(user_key=args.key)

params = RniParameters()
params["name1"] = { "text":"Michael Jackson", "language": "eng", "entityType":"PERSON" }
params["name2"] = { "text":"迈克尔·杰克逊", "entityType":"PERSON" }
#params["name1"] = { "text":"Elizabeth Doe", "language": "eng", "entityType":"PERSON" }
#params["name2"] = { "text":"Liz Doe", "language": "eng", "entityType":"PERSON" }
op = api.matched_name()
result = op.operate(params)

pprint.pprint(result)

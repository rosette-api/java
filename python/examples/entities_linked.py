# -*- coding: utf-8 -*-

'''
Example code to call Rosette API to get linked (against Wikipedia) entities from a piece of text.
'''

import argparse
import pprint
import sys

# enable imports from rosette.api
sys.path += '../../'

from rosette.api import API, RosetteParameters

parser = argparse.ArgumentParser(description='Accept Rosette API key')
parser.add_argument('--key', required=True, help='Rosette API key')
parser.add_argument('--service_url', nargs='?', help='Optional user service URL')
args = parser.parse_args()

# Create an API instance
if args.service_url:
    api = API(service_url=args.service_url, user_key=args.key)
else:
    api = API(user_key=args.key)

params = RosetteParameters()
params["content"] = u"President Obama urges the Congress and Speaker Boehner to pass the $50 billion spending bill"
params["content"] += u"based on Christian faith by July 1st or Washington will become totally dysfunctional,"
params["content"] += u"a terrible outcome for American people."
op = api.entities(True) # entity linking is turned on
result = op.operate(params)

pprint.pprint(result)

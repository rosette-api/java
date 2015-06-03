# -*- coding: utf-8 -*-

'''
Example code to call Rosette API to get a document's (located at a given URL) entities
'''

import sys
import pprint
import argparse
# enable imports from rosette.api
sys.path += '../../'

from rosette.api import API, RosetteParameters

# Get the user's access key
parser = argparse.ArgumentParser(description='Accept Rosette API key')
parser.add_argument('--key')
# Optional user input URL for data
parser.add_argument('--url', nargs='?', default='http://www.basistech.com/about/')
# Optional user service URL
parser.add_argument('--service_url', nargs='?')
args = parser.parse_args()

# Create an API
if args.service_url:
    api = API(service_url=args.service_url,user_key=args.key)
else:
    api = API(user_key=args.key)

params = RosetteParameters()

# Use a URL to input data instead of a string
params["contentUri"] = args.url
op = api.entities(False) # entity linking is turned off
result = op.operate(params)

pprint.pprint(result)

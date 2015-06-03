# -*- coding: utf-8 -*-

'''
Example code to call Rosette API to determine the language of a piece of text.
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

# Optional user service URL
parser.add_argument('--service_url', nargs='?')
args = parser.parse_args()

# Create an API
if args.service_url:
    api = API(service_url=args.service_url,user_key=args.key)
else:
    api = API(user_key=args.key)

params = RosetteParameters()

# Use an HTML file to load data instead of a string
params["content"] = u"This sample text will demonstrate the ability to detect language."
op = api.language()
result = op.operate(params)

pprint.pprint(result)
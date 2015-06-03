# -*- coding: utf-8 -*-

'''
Example code to call Rosette API to get entities from a piece of text.
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

# Use a URL to input data instead of a string
#params["contentUri"] = u"http://www.basistech.com/about/"
params["content"] = u"The first men to reach the moon – Mr. Armstrong and his co-pilot, Col. Edwin E. Aldrin, Jr. of the Air Force – brought their ship to rest on a level, rock-strewn plain near the southwestern shore of the arid Sea of Tranquility."
op = api.entities(False) # entity linking is turned off
result = op.operate(params)

pprint.pprint(result)

# -*- coding: utf-8 -*-

'''
Example code to call Rosette API to get the category of a piece of text.
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
params["content"] = u"The National Hockey League presents a number of trophies each year. The most prestigious team award is the Stanley Cup, which is awarded to the league champion at the end of the Stanley Cup playoffs. The team that has the most points in the regular season is awarded the Presidents' Trophy."
op = api.categories()
result = op.operate(params)

pprint.pprint(result)
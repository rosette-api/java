# -*- coding: utf-8 -*-

'''
Example code to call Rosette API to get linked (against Wikipedia) entities from a piece of text.
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
params["content"] = u"A total of twelve people have landed on the Moon. This was accomplished with two US pilot-astronauts flying a Lunar Module on each of six NASA missions across a 41-month time span starting on 20 July 1969, with Neil Armstrong and Buzz Aldrin on Apollo 11, and ending on 14 December 1972 with Gene Cernan and Jack Schmitt on Apollo 17. Cernan was the last to step off the lunar surface."
op = api.entities(True) # entity linking is turned on
result = op.operate(params)

pprint.pprint(result)
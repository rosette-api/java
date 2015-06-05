# -*- coding: utf-8 -*-

'''
Example code to call Rosette API to get Chinese readings fo words in a piece of text.
'''

import argparse
import pprint
import sys

# enable imports from rosette.api
sys.path += '../../'

from rosette.api import API, RosetteParameters, MorphologyOutput

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
params["content"] = u"The fact is that the geese just went back to get a rest and I'm not banking on their return soon"
op = api.morphology(MorphologyOutput.LEMMAS)
result = op.operate(params)

pprint.pprint(result)
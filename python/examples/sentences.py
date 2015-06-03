# -*- coding: utf-8 -*-

'''
Example code to call Rosette API to get sentences in a piece of text.
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
params["content"] = u"The Olympic Games are an important international event featuring summer and winter sports. Olympic Games are held every two years, with Summer and Winter Olympic Games taking turns. Each seasonal games happens every four years. Originally, the ancient Olympic Games were held in Olympia, Greece, from the 8th century BC to the 5th century AD."
op = api.sentences()
result = op.operate(params)

pprint.pprint(result)
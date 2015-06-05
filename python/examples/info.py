# -*- coding: utf-8 -*-

'''
Example code to call Rosette API to get information such as version and build
'''

import argparse
import pprint
import sys

# enable imports from rosette.api
sys.path += '../../'

from rosette.api import API, Operator

parser = argparse.ArgumentParser(description='Accept Rosette API key')
parser.add_argument('--key', required=True, help='Rosette API key')
parser.add_argument('--service_url', nargs='?', help='Optional user service URL')
args = parser.parse_args()

# Create an API instance
if args.service_url:
    api = API(service_url=args.service_url, user_key=args.key)
else:
    api = API(user_key=args.key)

op = Operator(api, None)
result = op.info()

pprint.pprint(result)
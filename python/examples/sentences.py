# -*- coding: utf-8 -*-

'''
Example code to call Rosette API to get sentences in a piece of text.
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
params["content"] = u"This land is your land This land is my land\n"
params["content"] += u"From California to the New York island;\n"
params["content"] += u"From the red wood forest to the Gulf Stream waters\n\n"
params["content"] += u"This land was made for you and Me.\n\n"
params["content"] += u"As I was walking that ribbon of highway,\n"
params["content"] += u"I saw above me that endless skyway:\n"
params["content"] += u"I saw below me that golden valley:\n"
params["content"] += u"This land was made for you and me.\n"

op = api.sentences()
result = op.operate(params)

pprint.pprint(result)

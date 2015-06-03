# -*- coding: utf-8 -*-

'''
Example code to call Rosette API to get Chinese readings fo words in a piece of text.
'''

import sys
import pprint
import argparse
# enable imports from rosette.api
sys.path += '../../'

from rosette.api import API, RosetteParameters, MorphologyOutput

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
params["content"] = u"新华网联合国１月２２日电（记者 白洁　王湘江）第６４届联合国大会２２日一致通过决议，呼吁１９２个成员国尽快响应联合国发起的海地救援紧急募捐呼吁，强调各国应对联合国主导的救灾工作予以支持。"
op = api.morphology(MorphologyOutput.HAN_READINGS)
result = op.operate(params)

pprint.pprint(result)

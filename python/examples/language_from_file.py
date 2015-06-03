# -*- coding: utf-8 -*-

'''
Example code to call Rosette API to determine the language of a given file.
'''

import sys
import pprint
import argparse
import os
# enable imports from rosette.api
sys.path += '../../'

from rosette.api import API, RosetteParameters

# Get the user's access key
parser = argparse.ArgumentParser(description='Accept Rosette API key')
parser.add_argument('--key')

# Optional user file
parser.add_argument('--file', nargs='?', default='simple.html')

# Optional user service URL
parser.add_argument('--service_url', nargs='?')
args = parser.parse_args()

# Create a file to read from
f = open('simple.html','w+b')
message = """<!DOCTYPE html>
<html>
    <body>

        <h1>Sample File</h1>

        <p>This is a simple sample HTML file to demonstrate the option of loading data from a file.</p>

    </body>
</html>"""
f.write(message)
f.close()

# Create an API
if args.service_url:
    api = API(service_url=args.service_url,user_key=args.key)
else:
    api = API(user_key=args.key)

params = RosetteParameters()

# Use an HTML file to load data instead of a string
params.load_document_file(args.file)
op = api.language()
result = op.operate(params)

# Clean up the file
os.remove('simple.html')

pprint.pprint(result)
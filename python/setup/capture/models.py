import logging
import json
import sys
import pprint
import glob
import codecs

db = {}

def readem():
    pp = pprint.PrettyPrinter(indent=4)

    for f in glob.glob("*.json"):
        data = json.load(codecs.open(f, "r", "utf-8"))
        dialogue = data[0]
        httpRequest = dialogue['httpRequest']
        httpResponse = dialogue['httpResponse']
        print "REQUEST:", httpRequest['path']
        print "RESPONSE BODY:", httpResponse['body']


readem()

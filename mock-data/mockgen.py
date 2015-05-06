#!/usr/bin/env python
#
# takes source test data and creates mocked RaaS request and response json files
#

import argparse
import csv
import feedparser
import glob
import io
import json
import os
import requests
import sys

DOC_ENDPOINTS = ["language", "morphology/complete", "entities", "entities/linked", "categories", "sentiment"]
RNT_ENDPOINTS = ["translated-name"]
RNI_ENDPOINTS = ["matched-name"]
GNEWS_LANGS = {"eng": "ned=us&hl=en-US",
               "zho": "ned=cn&hl=zh-CN",
               "ara": "ned=ar_me&hl=ar",
               "spa": "ned=es_mx&hl=es",
               "fra": "ned=fr&hl=fr"}

def capture(req, endpoints, filename_prefix):
    for endpoint in endpoints:
        resp = requests.post(args.endpoint + "/" + endpoint + "?debug=true",
                             headers=headers,
                             data=json.dumps(req))
        if resp.status_code >= 500:
            print "error posting to raas, response (%s): %s - abort" % (resp.status_code, resp.content)
            sys.exit(1)
        json_filename = filename_prefix + "-" + endpoint.replace("/", "_") + ".json"
        status_filename = filename_prefix + "-" + endpoint.replace("/", "_") + ".status"
        with io.open("request/" + json_filename, "w", encoding="utf8") as req_file,\
                io.open("response/" + json_filename, "w", encoding="utf8") as resp_file,\
                io.open("response/" + status_filename, "w", encoding="utf8") as resp_status_file:
            req_file.write(json.dumps(req, ensure_ascii=False, indent=2, sort_keys=True, encoding="utf8"))
            resp_file.write(json.dumps(resp.json(), ensure_ascii=False, indent=2, sort_keys=True, encoding="utf8"))
            resp_status_file.write(unicode(resp.status_code))
            req_file.close()
            resp_file.close()
        print "created mock req/resp files for %s" % (filename_prefix + "-" + endpoint)

parser = argparse.ArgumentParser(formatter_class=argparse.ArgumentDefaultsHelpFormatter,
                                 description="create RosApi mocked test data")
parser.add_argument("--endpoint", default="http://jugmaster.basistech.net/rest/v1", help="RosApi endpoint")
parser.add_argument("--user-key", help="RosApi user_ley")
args = parser.parse_args()

headers = {"Content-Type": "application/json"}
if args.user_key:
    headers['user_key'] = args.user_key

# prep & clean up
for folder in ["request", "response"]:
    if not os.path.exists(folder):
        os.makedirs(folder)
files = glob.glob("request/*.json") + glob.glob("response/*.json") + glob.glob("response/*.status")
for f in files:
    os.remove(f)

# store some info
resp = requests.get(args.endpoint + "/info", headers=headers)
if resp.status_code != 200:
    print "info call failed - abord"
    sys.exit(1)
else:
    with io.open("response/info.json", "w", encoding="utf8") as resp_file:
        resp_file.write(json.dumps(resp.json(), ensure_ascii=False, indent=2, sort_keys=True, encoding="utf8"))
        resp_file.close()

# deal with local docs
with open("source/doc-strings.tsv", "r") as tsvfile:
    reader = csv.DictReader(tsvfile, delimiter="\t", quoting=csv.QUOTE_MINIMAL)
    for row in reader:
        req = {}
        filename_prefix = ""
        if row["language"]:
            req["language"] = row["language"]
            filename_prefix = row["language"]
        else:
            filename_prefix = "xxx"
        if row["unit"]:
            req["unit"] = row["unit"]
            filename_prefix += "-" + row["unit"]
        else:
            filename_prefix += "-null"
        if row["content"]:
            req["content"] = row["content"]
        else:
            continue
        capture(req, DOC_ENDPOINTS, filename_prefix)
    tsvfile.close()

# deal with URLs
for key in GNEWS_LANGS.keys():
    rss_url = "https://news.google.com/news?cf=all&output=rss&" + GNEWS_LANGS[key]
    feed = feedparser.parse(rss_url)
    article_url = feed["items"][0]["link"]
    req = {"contentUri": article_url}
    filename_prefix = key + "-url"
    capture(req, DOC_ENDPOINTS, filename_prefix)

# deal with RNT
with open("source/rnt.tsv", "r") as tsvfile:
    reader = csv.DictReader(tsvfile, delimiter="\t", quoting=csv.QUOTE_MINIMAL)
    i = 0
    for row in reader:
        i += 1
        filename_prefix = "rnt-" + str(i)
        capture(row, RNT_ENDPOINTS, filename_prefix)
    tsvfile.close()

# deal with RNI
with open("source/rni.tsv", "r") as tsvfile:
    reader = csv.DictReader(tsvfile, delimiter="\t", quoting=csv.QUOTE_MINIMAL)
    i = 0
    for row in reader:
        i += 1
        filename_prefix = "rni-" + str(i)
        name1 = {}
        name2 = {}
        for column_name in row.keys():
            key = column_name.split("|")[0]
            if column_name.endswith("|1"):
                name1[key] = row[column_name]
            elif column_name.endswith("|2"):
                name2[key] = row[column_name]
            else:
                print "bad column heading %s - abort" % column_name
                sys.exit(1)
        capture({"name1": name1, "name2": name2}, RNI_ENDPOINTS, filename_prefix)
    tsvfile.close()


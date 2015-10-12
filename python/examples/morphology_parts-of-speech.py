# -*- coding: utf-8 -*-

"""
Example code to call Rosette API to get part-of-speech tags for words in a piece of text.
"""

import json

from rosette.api import API, DocumentParameters, MorphologyOutput

def run(key):
    # Create an API instance
    api = API(user_key = key)

    params = DocumentParameters()
    params["content"] = u"${morphology_parts_of_speech_data}"
    result = api.morphology(params, MorphologyOutput.PARTS_OF_SPEECH)

    print(json.dumps(result, indent=2, ensure_ascii=False).encode("utf8"))
    return json.dumps(result, indent=2, ensure_ascii=False).encode("utf8")

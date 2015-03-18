# -*- coding: utf-8 -*-

import sys
# enable imports from rosette.api
sys.path += '../../'

#_ping
from rosette.api import API, RosetteParameters

# Create an API with the "service_url" parameter providing 
# the base URL for the Rosette services.

api = API(service_url="http://jugmaster.basistech.net/rest/v1", user_key="1234567890")
op = api.ping()
result = op.ping()
print("/ping: ", result)
##_


#_entities
from rosette.api import API, RosetteParameters

api = API(service_url="http://jugmaster.basistech.net/rest/v1", user_key="1234567890")
params = RosetteParameters()
params["content"] = u"The first men to reach the moon -- Mr. Armstrong and his co-pilot, Col. Edwin E. Aldrin, Jr. of the Air Force -- brought their ship to rest on a level, rock-strewn plain near the southwestern shore of the arid Sea of Tranquility."
op = api.entities(False) # entity linking is turned off
result = op.operate(params)
print("/entities: ", result)
##_


#_entities_linked
from rosette.api import API, RosetteParameters

api = API(service_url="http://jugmaster.basistech.net/rest/v1", user_key="1234567890")
params = RosetteParameters()
params["content"] = u"The first men to reach the moon -- Mr. Armstrong and his co-pilot, Col. Edwin E. Aldrin, Jr. of the Air Force -- brought their ship to rest on a level, rock-strewn plain near the southwestern shore of the arid Sea of Tranquility."
op = api.entities(True) # entity linking is turned on
result = op.operate(params)
print("/entities/linked: ", result)
##_


#_categories
from rosette.api import API, RosetteParameters

api = API(service_url="http://jugmaster.basistech.net/rest/v1", user_key="1234567890")
params = RosetteParameters()
params["content"] = u"We need to spend several weeks fixing up our family tennis court."
op = api.categories()
result = op.operate(params)
print("/categories: ", result)
##_


#_sentiment
from rosette.api import API, RosetteParameters

api = API(service_url="http://jugmaster.basistech.net/rest/v1", user_key="1234567890")
params = RosetteParameters()
params["content"] = u"We are looking forward to the upcoming release."
op = api.sentiment()
result = op.operate(params)
print("/sentiment: ", result)
##_


#_language
from rosette.api import API, RosetteParameters

api = API(service_url="http://jugmaster.basistech.net/rest/v1", user_key="1234567890")
params = RosetteParameters()
params["content"] = u"The quick brown fox jumped over the lazy dog. Yes he did."
op = api.language()
result = op.operate(params)
print("/language: ", result)
##_


#_morpho_complete
from rosette.api import API, RosetteParameters
api = API(service_url="http://jugmaster.basistech.net/rest/v1", user_key="1234567890")
params = RosetteParameters()
params["content"] = u"The quick brown fox jumped over the lazy dog. Yes he did."
op = api.morphology()
result = op.operate(params)
print("/morphology/complete: ", result)
##_


#_morpho_han_readings
from rosette.api import API, RosetteParameters, MorphologyOutput
api = API(service_url="http://jugmaster.basistech.net/rest/v1", user_key="1234567890")
params = RosetteParameters()
params["content"] = u"新华网联合国１月２２日电（记者 白洁　王湘江）第６４届联合国大会２２日一致通过决议，呼吁１９２个成员国尽快响应联合国发起的海地救援紧急募捐呼吁，强调各国应对联合国主导的救灾工作予以支持。"
op = api.morphology(MorphologyOutput.HAN_READINGS)
result = op.operate(params)
print("/morphology/han_readings: ", result)
##_


#_tokens
from rosette.api import API, RosetteParameters

api = API(service_url="http://jugmaster.basistech.net/rest/v1", user_key="1234567890")
params = RosetteParameters()
params["content"] = u"The brown fox's mother jumped over 3 lazy dogs. Yes she did."
op = api.tokens()
result = op.operate(params)
print("/tokens: ", result)
##_


#_sentences
from rosette.api import API, RosetteParameters

api = API(service_url="http://jugmaster.basistech.net/rest/v1", user_key="1234567890")
params = RosetteParameters()
params["content"] = u"The brown fox's mother jumped over 3 lazy dogs. Yes she did."
op = api.sentences()
result = op.operate(params)
print("/sentences: ", result)
##_


#_translated_name
from rosette.api import API, RntParameters
 
api = API(service_url="http://jugmaster.basistech.net/rest/v1", user_key="1234567890")
params = RntParameters()
params["name"] = u"كريم عبد الجبار"
params["entityType"] = "PERSON"
params["targetLanguage"] = "eng"
op = api.translated_name()
result = op.operate(params)
print("/translated_name\n", result)
##_


#_multipart_form
from rosette.api import API, RosetteParameters, DataFormat

api = API(service_url="http://jugmaster.basistech.net/rest/v1", user_key="1234567890")
params = RosetteParameters()
params.load_document_file("data/fox.txt")
op = api.sentences()
result = op.operate(params)
print("/sentences (multipart_form): ", result)
##_


#_overview
#2
from rosette.api import API, RosetteParameters, MorphologyOutput

#3
api = API(service_url="http://jugmaster.basistech.net/rest/v1", user_key="1234567890")

#4
params = RosetteParameters()

#5
params["content"] = u"Was ist so böse an der Europäischen Zentralbank?"

#6
op = api.morphology(MorphologyOutput.LEMMAS)

#7
result = op.operate(params)
print("overview: ", result)
##_


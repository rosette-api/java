# -*- coding: utf-8 -*-
import json
def printJson(endpoint, result):
    print endpoint
    print json.dumps(result,indent=4, separators=(',', ': '))

#_ping
from rosette.api import API, RosetteParameters

api = API(service_url="http://jugmaster.basistech.net/rest/v1", user_key="1234567890")
op = api.ping()
result = op.ping()
##_
printJson('/ping',result)

#_entities
from rosette.api import API, RosetteParameters

api = API(service_url="http://jugmaster.basistech.net/rest/v1", user_key="1234567890")
params = RosetteParameters()
params["content"] = u"The first men to reach the moon -- Mr. Armstrong and his co-pilot, Col. Edwin E. Aldrin, Jr. of the Air Force -- brought their ship to rest on a level, rock-strewn plain near the southwestern shore of the arid Sea of Tranquility."
op = api.entities(False) # entity linking is turned off
result = op.operate(params)
##_
printJson('/entities',result)


#_entities_linked
from rosette.api import API, RosetteParameters

api = API(service_url="http://jugmaster.basistech.net/rest/v1", user_key="1234567890")
params = RosetteParameters()
params["content"] = u"The first men to reach the moon -- Mr. Armstrong and his co-pilot, Col. Edwin E. Aldrin, Jr. of the Air Force -- brought their ship to rest on a level, rock-strewn plain near the southwestern shore of the arid Sea of Tranquility."
op = api.entities(True) # entity linking is turned on
result = op.operate(params)
##_
printJson('entities/linked',result)


#_categories
from rosette.api import API, RosetteParameters

api = API(service_url="http://jugmaster.basistech.net/rest/v1", user_key="1234567890")
params = RosetteParameters()
params["content"] = u"We need to spend several weeks fixing up our family tennis court."
op = api.categories()
result = op.operate(params)
##_
printJson('/categores', result)


#_sentiment
from rosette.api import API, RosetteParameters

api = API(service_url="http://jugmaster.basistech.net/rest/v1", user_key="1234567890")
params = RosetteParameters()
params["content"] = u"We are looking forward to the upcoming release."
op = api.sentiment()
result = op.operate(params)
##_
printJson('/sentiment',result)


#_language
from rosette.api import API, RosetteParameters

api = API(service_url="http://jugmaster.basistech.net/rest/v1", user_key="1234567890")
params = RosetteParameters()
params["content"] = u"The quick brown fox jumped over the lazy dog. Yes he did."
op = api.language()
result = op.operate(params)
##_
printJson('/language',result)


#_morpho_complete
from rosette.api import API, RosetteParameters
api = API(service_url="http://jugmaster.basistech.net/rest/v1", user_key="1234567890")
params = RosetteParameters()
params["content"] = u"The quick brown fox jumped over the lazy dog. Yes he did."
op = api.morphology()
result = op.operate(params)
##_
printJson('/morphology/complete',result)


#_morpho_han_readings
from rosette.api import API, RosetteParameters, MorphologyOutput
api = API(service_url="http://jugmaster.basistech.net/rest/v1", user_key="1234567890")
params = RosetteParameters()
params["content"] = u"Was ist so böse an der Europäischen Zentralbank?"
op = api.morphology(MorphologyOutput.LEMMAS)
result = op.operate(params)
##_
import codecs
out = codecs.open('deu.txt', 'w', 'utf-8')
out.write(str(result))
out.close()
print result


#_tokens
from rosette.api import API, RosetteParameters

api = API(service_url="http://jugmaster.basistech.net/rest/v1", user_key="1234567890")
params = RosetteParameters()
params["content"] = u"The brown fox's mother jumped over 3 lazy dogs. Yes she did."
op = api.tokens()
result = op.operate(params)
##_
printJson('/tokens',result)


#_sentences
from rosette.api import API, RosetteParameters

api = API(service_url="http://jugmaster.basistech.net/rest/v1", user_key="1234567890")
params = RosetteParameters()
params["content"] = u"The brown fox's mother jumped over 3 lazy dogs. Yes she did."
op = api.sentences()
result = op.operate(params)
##_
printJson('/sentences',result)


#_translated_name
from rosette.api import API, RntParameters
 
api = API(service_url="http://jugmaster.basistech.net/rest/v1", user_key="1234567890")
params = RntParameters()
params["name"] = u"كريم عبد الجبار"
params["entityType"] = "PERSON"
params["targetLanguage"] = "eng"
op = api.translated_name()
result = op.operate(params)
##_
printJson('/translated_name',result)

#_multipart_form
from rosette.api import API, RosetteParameters, DataFormat

api = API(service_url="http://jugmaster.basistech.net/rest/v1", user_key="1234567890")
params = RosetteParameters()
params.load_document_file("samples/fox.txt")
op = api.sentences()
result = op.operate(params)
##_
printJson("/sentences (multipart_form)",result)

#_overview
from rosette.api import API, RosetteParameters, MorphologyOutput

api = API(service_url="http://jugmaster.basistech.net/rest/v1", user_key="1234567890")
params = RosetteParameters()
params["content"] = u"Was ist so böse an der Europäischen Zentralbank?"
op = api.morphology(MorphologyOutput.LEMMAS)
result = op.operate(params)
##_
print 'overview\n', result, '\n'


#!/usr/bin/env bash

if [ $# -eq 0 ]; then
    echo "Usage: $0 API_KEY" 1>&2
    exit 1
fi

key=$1

read -d '' examples <<EOF
com.basistech.rosette.examples.CategoriesExample
com.basistech.rosette.examples.EntitiesExample
com.basistech.rosette.examples.EntitiesLinkedExample
com.basistech.rosette.examples.InfoExample
com.basistech.rosette.examples.LanguageExample
com.basistech.rosette.examples.MorphologyCompleteExample
com.basistech.rosette.examples.MorphologyCompoundComponentsExample
com.basistech.rosette.examples.MorphologyHanReadingsExample
com.basistech.rosette.examples.MorphologyLemmasExample
com.basistech.rosette.examples.MorphologyPartsOfSpeechExample
com.basistech.rosette.examples.NameDeduplicationExample
com.basistech.rosette.examples.NameSimilarityExample
com.basistech.rosette.examples.NameTranslationExample
com.basistech.rosette.examples.PingExample
com.basistech.rosette.examples.RelationshipsExample
com.basistech.rosette.examples.SentencesExample
com.basistech.rosette.examples.SentimentExample
com.basistech.rosette.examples.TokensExample
EOF

for example in $examples; do
    java -Drosette.api.key=$key -cp rosette-api-examples.jar:lib/rosette-api-manifest.jar $example
done


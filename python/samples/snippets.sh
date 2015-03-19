# run collection of doc samples, then extract the individual snippets
python src/docsamples.py 
rm -rf target
python extractSnippets.py src target
zip -j target/snippets.zip target/*.py

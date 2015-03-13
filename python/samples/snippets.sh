# run collection of doc samples, then extract the individual snippets
cp src/docsamples.py ../
python ../docsamples.py
rm ../docsamples.py
python extractSnippets.py ./src ./target

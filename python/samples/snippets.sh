# run collection of doc samples, then extract the individual snippets
cp src/docsamples.py ..
cd ..
python docsamples.py 
rm docsamples.py
cd samples
python extractSnippets.py ./src ./target

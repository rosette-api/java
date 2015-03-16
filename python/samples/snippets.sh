# run collection of doc samples, then extract the individual snippets
bash ../src/scripts/run_in_venv.sh ../virtualenv/target/vroot ../virtualenv/target/vroot/bin/python src/docsamples.py
python extractSnippets.py ./src ./target

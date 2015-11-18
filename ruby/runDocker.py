import os
import subprocess
examples = ["categories", "entities", "entities_linked", "info", "language", "matched-name", "morphology_complete", "morphology_compound-components", "morphology_han-readings", "morphology_lemmas", "morphology_parts-of-speech", "ping", "sentences", "sentiment", "tokens", "translated-name"]
ext = ".rb"
process = subprocess.Popen(["mvn", "clean install -Dtarget=true"],shell=True)
stdoutdata, stderrdata = process.communicate()
subprocess.call(["docker", "build", "-t", "examples", "."])
for i in examples:
    j = i + ext
    cmd = subprocess.Popen(["docker", "run", "-e", "file=" + j, "-e", "key=88afd6b4b18a11d1248639ecf399903c", "-v", os.getcwd() + ":/examples", "examples"], stdout=subprocess.PIPE, stderr=subprocess.STDOUT)
    cmd_out, cmd_err = cmd.communicate()
    print cmd_out


import subprocess
import os

if not os.path.exists('examples/fox.txt'):
    f = open('examples/fox.txt', 'w')
    f.write("The quick brown fox jumped over the lazy dog. Yes he did.")
    f.close()

examples = ["categories", "entities", "entities_linked", "info", "language", "local", "matched-name", "morphology_complete", "morphology_compound-components", "morphology_han-readings", "morphology_lemmas", "morphology_parts-of-speech", "ping", "sentences", "sentiment", "tokens", "translated-name"]
ext = ".sh"
process = subprocess.Popen(["mvn", "clean", "install", "-Dtarget=true"],shell=True)
a, b= process.communicate()
for i in examples:
    j = "target/github-publish/examples/" + i + ext
    p = subprocess.Popen(["dos2unix", j], shell=True)
    a, b = p.communicate()
    
stdoutdata, stderrdata = process.communicate()
subprocess.call(["docker", "build", "-t", "examples", "."])
for i in examples:
    j = i + ext
    fp = "none"
    if i == "local":
        fp = "target/github-publish/examples/fox.txt"
    cmd = subprocess.Popen(["docker", "run", "-e", "file=" + j, "-e", "key=88afd6b4b18a11d1248639ecf399903c", "-e", "path=target/github-publish/examples", "-e", "fullpath=" + fp,"examples"], stdout=subprocess.PIPE, stderr=subprocess.STDOUT)
    cmd_out, cmd_err = cmd.communicate()
    print cmd_out
    

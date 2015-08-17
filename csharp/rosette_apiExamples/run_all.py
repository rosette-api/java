from os import listdir
import os
import subprocess
import shutil
import time
import sys

# helper function to cleanup folder
def cleanup():
    try:
        shutil.rmtree('test')
    except:
        sys.exit('Failed to clean up test folder')
        
# clone from git and get examples 
# try:
#     subprocess.call(["git", "clone", "git@github.com:basis-technology-corp/rosette-api.git", "gitclone"])
# except:
#    sys.exit('Failed to clone examples from github: git@github.com:basis-technology-corp/rosette-api.git')

success = True
error = ""
# move examples to test folder
try:
#     gitsrc = os.path.join(os.path.realpath('.'), 'gitclone/csharp/rosette_apiExamples')
    gitsrc = os.path.join(os.path.realpath('..'), 'target/github-publish/rosette_apiExamples')
    gitdest = os.path.join(os.path.realpath('.'), 'test')
    print gitsrc
    print gitdest
    shutil.copytree(gitsrc, gitdest)
except:
    cleanup()
    print 'Failed to copy examples from github folder to test folder'
    sys.exit('Failed to copy examples from github folder to test folder')
    
# move rosette_api.dll to current folder
try:
    src = os.path.join(os.path.realpath('.'), 'bin/debug/rosette_api.dll')
    print src
    dest = os.path.join(os.path.realpath('.'), 'test/rosette_api.dll')
    print dest
    shutil.copyfile(src, dest)
except:
    cleanup()
    print 'Failed to copy over rosette_api.dll to test folder'
    sys.exit('Failed to copy over rosette_api.dll to test folder')


# compile and run each example
print listdir(os.path.join(os.path.realpath('.'), 'test'))
failures = []
for f in listdir(os.path.join(os.path.realpath('.'), 'test')):
    if f.endswith(".cs"):
        print f
        try:
            subprocess.call(["csc", "/out:" + os.path.join(os.path.realpath('.'), 'test/' + os.path.splitext(f)[0] + ".exe"), os.path.join(os.path.realpath('.'), f), "/r:System.Net.Http.dll", "/r:System.IO.dll", "/r:System.Web.Extensions.dll", "/r:test/rosette_api.dll"])
            cmd = subprocess.Popen([os.path.join(os.path.realpath('.'), 'test/' + os.path.splitext(f)[0] + ".exe"), "88afd6b4b18a11d1248639ecf399903c"], stdout=subprocess.PIPE, stderr=subprocess.STDOUT)
            cmd_out, cmd_err = cmd.communicate()
            nice = threading.Timer(1, cmd.terminate)
            nice.start()
            mean = threading.Timer(1.1, cmd.kill)
            mean.start()
            cmd.wait()
            nice.cancel()
            mean.cancel()
            print cmd_out
            print cmd_err
            if "Exception" in cmd_out:
                failures = failures + [f]
        except:
            failures = failures + [f]

if len(failures) != 0:
    cleanup()
    print 'Failed to pass these examples: ' + ', '.join(failures)
    sys.exit('Failed to pass these examples: ' + ', '.join(failures))

# at the end clean up the folder
cleanup()

sys.exit(0)



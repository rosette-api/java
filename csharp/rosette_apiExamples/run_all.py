from os import listdir
import os
import subprocess
import shutil
import time
import sys

# helper function to remove folders
# http://stackoverflow.com/questions/2656322/python-shutil-rmtree-fails-on-windows-with-access-is-denied
def onerror(func, path, exc_info):
    """
    Error handler for ``shutil.rmtree``.

    If the error is due to an access error (read only file)
    it attempts to add write permission and then retries.

    If the error is for another reason it re-raises the error.

    Usage : ``shutil.rmtree(path, onerror=onerror)``
    """
    import stat
    if not os.access(path, os.W_OK):
        # Is the error an access error ?
        os.chmod(path, stat.S_IWUSR)
        func(path)
    else:
        raise


# helper function to cleanup folder
def cleanup():
    try:
        shutil.rmtree('test', onerror=onerror)
    except:
        print "Test folder failed to be removed"
    try:
        shutil.rmtree('rosettePackage', onerror=onerror)
    except:
        print "rosettePackage folder failed to be removed"
    try:
        shutil.rmtree('gitclone', onerror=onerror)
    except:
        print "Gitclone folder failed to be removed"

# Start by cleaning up
cleanup()

# clone from git and get examples
try:
    subprocess.call(["git", "clone", "-b", "0.5.2", "git@github.com:rosette-api/csharp.git", "gitclone"])
except:
    sys.exit('Failed to clone examples from github: git@github.com:basis-technology-corp/rosette-api.git')

# move examples to test folder
try:
    gitsrc = os.path.join(os.path.realpath('.'), 'gitclone/rosette_apiExamples')
    gitdest = os.path.join(os.path.realpath('.'), 'test')
    shutil.copytree(gitsrc, gitdest)
except:
    cleanup()
    print 'Failed to copy examples from github folder to test folder'
    sys.exit('Failed to copy examples from github folder to test folder')

# Try to install rosette_api from nuget
try:
    subprocess.call(["nuget", "install", "rosette_api", "-o", "rosettePackage"])
except:
    print 'Failed to install newest rosette_api package from nuget'
    sys.exit('Failed to install newest rosette_api package from nuget')

# move rosette_api.dll to current folder
try:
    version = listdir(os.path.join(os.path.realpath('.'), 'rosettePackage'))[0]
    src = os.path.join(os.path.realpath('.'), 'rosettePackage/' + version + '/lib/net45/rosette_api.dll')
    dest = os.path.join(os.path.realpath('.'), 'test/rosette_api.dll')
    shutil.copyfile(src, dest)
except:
    cleanup()
    print 'Failed to copy over rosette_api.dll to test folder'
    sys.exit('Failed to copy over rosette_api.dll to test folder')

# Try to move into the test folder
try:
    os.chdir(os.path.realpath('test'))
except:
    print 'Failed to move into test'
    sys.exit('Failed to move into test')

# compile and run each example
failures = []
for f in listdir(os.path.join(os.path.realpath('.'))):
    if f.endswith(".cs"):
        print f
        try:
            subprocess.call(["csc", f, "/r:System.Net.Http.dll", "/r:System.IO.dll", "/r:System.Web.Extensions.dll", "/r:rosette_api.dll"])
            cmd = subprocess.Popen([os.path.splitext(f)[0] + ".exe", "88afd6b4b18a11d1248639ecf399903c"], stdout=subprocess.PIPE, stderr=subprocess.STDOUT)
            cmd_out, cmd_err = cmd.communicate()
            print cmd_out
            if "Exception" in cmd_out or "{" not in cmd_out:
                failures = failures + [f]
        except:
            print f + " was unable to be compiled and run"
            failures = failures + [f]

# Exit test folder
try:
    os.chdir(os.path.realpath('..'))
except:
    print 'Failed to move back into rosette_apiExamples'
    sys.exit('Failed to move back into rosette_apiExamples')
if len(failures) != 0:
    cleanup()
    print 'Failed to pass these examples: ' + ', '.join(failures)
    sys.exit('Failed to pass these examples: ' + ', '.join(failures))

# at the end clean up the folder
cleanup()

print 'All tests passed successfully'
sys.exit(0)



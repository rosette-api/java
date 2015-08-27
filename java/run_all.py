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
        shutil.rmtree('gitclone', onerror=onerror)
    except:
        print "Gitclone folder failed to be removed"

# Try to move into the cloned examples folder
try:
    os.chdir(os.path.realpath('gitclone/examples'))
    print "Moved into gitclone/examples"
except:
    print 'Failed to move into gitclone/examples'
    cleanup()
    sys.exit('Failed to move into gitclone/examples')

# Perform a mvn call
try:
    cmd = subprocess.call(["mvn"])
except:
    cleanup()    
    sys.exit('Failed to perform mvn')

print os.path.realpath('.')
# compile and run each example
failures = []
for f in listdir(os.path.realpath('src/main/java/com/basistech/rosette/examples')):
    if f.endswith(".java"):
        print f
        try:
            if not "ExampleBase" in f:
                cmd = subprocess.Popen(["mvn", "exec:java", "-Dexec.mainClass=" + '"com.basistech.rosette.examples.' + os.path.splitext(f)[0] + '"', "-Drosette.api.key=" + '"88afd6b4b18a11d1248639ecf399903c"'], stdout=subprocess.PIPE, stderr=subprocess.STDOUT)
                cmd_out, cmd_err = cmd.communicate()
                print cmd_out
                if "Exception" in cmd_out or "{" not in cmd_out:
                    failures = failures + [f]
        except:
            print f + " was unable to be compiled and run"
            failures = failures + [f]

# Exit test folder
try:
    os.chdir(os.path.realpath('../..'))
except:
    print 'Failed to move back into examples'
    sys.exit('Failed to move back into examples')

if len(failures) != 0:
    cleanup()
    print 'Failed to pass these examples: ' + ', '.join(failures)
    sys.exit('Failed to pass these examples: ' + ', '.join(failures))

# at the end clean up the folder
cleanup()

print 'All tests passed successfully'
sys.exit(0)



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

# Start by cleaning up
cleanup()

# clone from git and get examples
try:
    subprocess.call(["git", "clone", "-b", "master", "https://github.com/rosette-api/java.git", "gitclone"])
except:
    sys.exit('Failed to clone examples from github: https://github.com/rosette-api/java.git')
    

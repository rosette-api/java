from os import listdir
import os
import subprocess
import shutil
import time
import sys
import platform
import pip
pip.main(["install", "--upgrade", "pip"])
pip.main(["install", "--upgrade", "gitpython"])
from git import Repo
join = os.path.join

currOS = platform.system()

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
        print "gitclone folder found and removed"
    except:
        pass

def runpython():
    failed = True
    runs = 0
    while failed and runs < 3:
        try:
            # Start by cleaning up and setting up
            cleanup()
            try:
                pip.main(["install", "--upgrade", "argparse"])
            except:
                pass
            Repo.clone_from("https://github.com/rosette-api/python.git", "gitclone")
            # install rosette_api python package
            pip.main(["install", "--upgrade", "rosette_api"])

            # Try to move into the cloned examples folder
            try:
                os.chdir(os.path.realpath('gitclone/examples'))
            except:
                print 'Failed to move into gitclone/examples'

            # compile and run each example
            failures = []
            retry = 10
            for f in listdir(os.path.join(os.path.realpath('.'))):
                if f.endswith(".py") and "init" not in f:
                    print f
                    success = False
                    try:
                        for i in range(retry):
                            cmd = subprocess.Popen(["python", f, "--key", "88afd6b4b18a11d1248639ecf399903c"], stdout=subprocess.PIPE, stderr=subprocess.STDOUT)
                            cmd_out, cmd_err = cmd.communicate()
                            if "Exception" not in cmd_out and "{" in cmd_out:
                                success = True
                                break
                            time.sleep(2)
                        print cmd_out
                        if not success:
                            failures = failures + [f]
                    except:
                        print f + " was unable to be compiled and run"
                        failures = failures + [f]

            # Exit test folder
            try:
                os.chdir(os.path.realpath('../..'))
            except:
                print 'Failed to move back into examples'

            if len(failures) != 0:
                cleanup()
                print 'Failed to pass these examples: ' + ', '.join(failures)

            # at the end clean up the folder
            cleanup()
            failed = False
        except:
            runs = runs + 1
            print 'Attempt ' + str(runs) + ' failed. Retrying'

    if failed:
        print 'Failed after 3 attempts'
        return False
    else:
        print 'All tests passed successfully'
        return True

def runcsharp():
    failed = True
    runs = 0
    while failed and runs < 3:
        try:
            # Start by cleaning up
            cleanup()

            # clone from git and get examples
            print "cloning from github"
            Repo.clone_from("https://github.com/rosette-api/csharp.git", "gitclone")
            
            # Try to move into the cloned examples folder
            try:
                os.chdir(os.path.realpath('gitclone/rosette_apiExamples'))
            except:
                print 'Failed to move into gitclone/rosette_apiExamples'
            
            # Try to install rosette_api from nuget
            print "installing api from nuget"
            subprocess.call(["nuget", "install", "rosette_api", "-o", "rosettePackage"])

            try:
                version = listdir(os.path.join(os.path.realpath('.'), 'rosettePackage'))[0]
                src = os.path.join(os.path.realpath('.'), 'rosettePackage/' + version + '/lib/net45/rosette_api.dll')
                dest = os.path.join(os.path.realpath('.'), 'rosette_api.dll')
                shutil.copyfile(src, dest)
            except:
                cleanup()
                print 'Failed to copy over rosette_api.dll to test folder'

            # compile and run each example
            failures = []
            retry = 10
            for f in listdir(os.path.join(os.path.realpath('.'))):
                if f.endswith(".cs"):
                    print f
                    success = False
                    try:
                        for i in range(retry):
                            subprocess.call(["csc", f, "/r:System.Net.Http.dll", "/r:System.IO.dll", "/r:System.Web.Extensions.dll", "/r:rosette_api.dll"])
                            cmd = subprocess.Popen([os.path.splitext(f)[0] + ".exe", "88afd6b4b18a11d1248639ecf399903c"], stdout=subprocess.PIPE, stderr=subprocess.STDOUT)
                            cmd_out, cmd_err = cmd.communicate()
                            if "Exception" not in cmd_out and "{" in cmd_out:
                                success = True
                                break
                            time.sleep(2)
                        print cmd_out
                        if not success:
                            failures = failures + [f]
                    except:
                        print f + " was unable to be compiled and run"
                        failures = failures + [f]

            if len(failures) != 0:
                print 'Failed to pass these examples: ' + ', '.join(failures)
            else:
                failed = False

            # at the end clean up the folder
            cleanup()
        except:
            runs = runs + 1
            print 'Attempt ' + str(runs) + ' failed. Retrying'

    if failed:
        print 'Failed after 3 attempts'
        return False
    else:
        print 'All tests passed successfully'
        return True
    
if "Windows" in currOS:
    #Run Python first
    pythonsuccess = runpython()
    #Run C# next
    csharpsuccess = runcsharp()
    os.chdir(os.path.realpath('../..'))
    cleanup()
    if pythonsuccess and csharpsuccess:
        sys.exit(0)
    else:
        sys.exit('Python and C# failed')
else:
    pass    

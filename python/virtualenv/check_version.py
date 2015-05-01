import sys

if not sys.version.startswith(sys.argv[1]):
    sys.stderr.write("python not version " + sys.argv[1] + " as expected.\n")
    sys.exit(4)

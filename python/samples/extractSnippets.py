# -*- coding: utf-8 -*-

import os, sys, codecs

def parse(pyfile,docdir):
    start = '#_'
    end = '##_'
    snip = False
    for line in pyfile:
        if line.lstrip().startswith(start):
            f = line.lstrip().lstrip(start).rstrip() + '.py'
            snippet = os.path.join(docdir, f)
            out = codecs.open(snippet, 'w', 'utf8')
            snip = True
        elif snip:
            if line.lstrip().startswith(end):
                out.close()
                print 'See', snippet
                snip = False
            else:
                # put in the correct url for end users and the documentation.
                line = line.replace('http://jugmaster.basistech.net', 'https://api.rosette.com', 1)
                out.write(line)

def main():
    if (len(sys.argv) == 3):
        (samplesdir, docdir) = sys.argv[1:]
    else:
        print >>sys.stderr, 'Usage: %s samplesdir docdir' % sys.argv[0]
        sys.exit(1)    
    files = os.listdir(samplesdir)
    for f in files:
        if f.endswith('.py'):
            jf = os.path.join(samplesdir, f)
            pyfile = codecs.open(jf, 'r', 'utf8')
            print 'Parsing',pyfile
            parse(pyfile, docdir)
            pyfile.close()

main()

Python Samples
==================

To start with, samples contains python samples that are also embedded in the markdown 
end-user documentation for the Rosette API.

The samples are in samples/docsamples.py.

Assuming you have installed requests and enum, you can copy docsamples.py to the python 
directory and run it. Later, we will add a procedure to do this and to verify the results.

From samples, run snippets.sh, which extracts each sample snippet from docsamples.py and 
writes it to samples/target. Later we can put these samples in a maven artifact, and set]
up a procedure in the raas/docs repo to extract and place the snippets in the markdown
(index.md).

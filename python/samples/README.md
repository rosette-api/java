Python Samples
==================

To start with, samples contains python samples that are also embedded in the markdown 
end-user documentation for the Rosette API.

The samples are in python/samples/src/docsamples.py.

Assuming you have installed requests and enum34, run snippets.sh from python/samples.

This script runs the samples, printing json output to the console, then extracts
each sample and writes it to samples/target.

Later we can and set up procedures to publish the samples and to extract and place the 
samples in the markdown (index.md).

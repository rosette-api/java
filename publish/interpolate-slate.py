#!/usr/bin/env python
import os
import re
import sys


def usage():
    print sys.argv[0] + " <path_to_slate_index_md_file>"
    sys.exit(1)


def get_example_file_map():
    bindings_base_dir = os.path.dirname(os.path.realpath(__file__)) + "/../"
    example_file_map = {}
    for endpoint in ["info", "ping",
                     "language",
                     "tokens", "sentences", "morphology_complete", "morphology_lemmas",
                     "morphology_compound-components", "morphology_han-readings", "morphology_parts-of-speech",
                     "entities", "entities_linked",
                     "categories", "sentiment",
                     "translated-name", "matched-name"]:
        for language in ["java", "ruby", "php", "python", "nodejs", "go", "csharp"]:
            language_dir = bindings_base_dir + language
            if language == "python":
                example_file = "%s/examples/%s.py" % (language_dir, endpoint)
            elif language == "php":
                example_file = "%s/examples/%s.php" % (language_dir, endpoint)
            elif language == "java":
                camel_case_endpoint = re.sub(r"[-_]", " ", endpoint).title().replace(" ", "")
                example_file = "%s/examples/src/main/java/com/basistech/rosette/examples/%sExample.java" % \
                               (language_dir, camel_case_endpoint)
            elif language == "nodejs":
                example_file = "%s/examples/%s.js" % (language_dir, endpoint)
            else:
                # TODO: expand this as new bindings are added
                example_file = None
            example_file_map[language + ":" + endpoint] = example_file
    print "built example file map"
    return example_file_map


def get_example_regex_map():
    example_regex_map = {}
    for language in ["java", "ruby", "php", "python", "nodejs", "go", "csharp"]:
        if language == "python":
            # all content starting at first import
            pattern = re.compile("(import.*)()", re.DOTALL)
        elif language == "php":
            # first <php?, then skip the header comment, then the rest
            pattern = re.compile("(<\?php.*)\n/\*\*.*\*\*/(.*)", re.DOTALL)
        elif language == "java":
            # skip the class javadoc section
            pattern = re.compile("(.*)\n/\*.*\*/(.*)", re.DOTALL)
        elif language == "nodejs":
            pattern = re.compile("(\"use strict\".*)()", re.DOTALL)
        else:
            # TODO: expand this as new bindings are added
            pattern = None
        example_regex_map[language] = pattern
    print "built example regex map"
    return example_regex_map


# Given the slate md file (to replace parts of), and the language and endpoint, finds the correct example code
# and updates the file to reflect any changes
def replace_file_content(slate_file):
    print "interpolating " + slate_file + " ..."
    (place_holder, inline_example, regex_problems) = ([], [], [])
    index = num_examples = num_replaced = num_untouched = 0
    in_process = False
    current_tag = ""
    with open(slate_file, "r") as f:
        for line in f:
            index += 1
            if line.startswith("[//]:"):
                match = re.match("^\[//]:\s+([A-Za-z0-9]+:[A-Za-z0-9_-]+)_begin_tag", line)
                if match:
                    if in_process:
                        print "line #%d contains a begin_tag without a previous end_tag, please fix the file" % index
                        sys.exit(1)
                    in_process = True
                    current_tag = match.group(1)
                    place_holder.append(line)
                    print "== begin %s=======================================================" % current_tag
                    continue

                match = re.match("^\[//]:\s+([A-Za-z0-9]+:[A-Za-z0-9_-]+)_end_tag", line)
                if match:
                    if not in_process:
                        print "line #%d end_tag without a previous begin_tag, please fix the file" % index
                        sys.exit(1)
                    if match.group(1) != current_tag:
                        print "line #%d end_tag mismatches the previous begin_tag, please fix the file" % index
                        sys.exit(1)
                    if current_tag in example_file_map and example_file_map[current_tag]:
                        try:
                            example_content = get_example_content(current_tag).lstrip()
                            if not example_content.endswith("\n"):
                                example_content += "\n"
                        except ValueError as e:
                            example_content = inline_example
                            regex_problems.append(current_tag)
                    else:
                        example_content = inline_example
                    if example_content != inline_example:
                        num_replaced += 1
                        print example_content
                        print "== end ==%s=======================================================\n\n" % current_tag
                    else:
                        num_untouched += 1
                        print "   ... no example found, leave inline content"
                        print "== end ==%s=======================================================\n\n" % current_tag
                    place_holder.append("".join(example_content))
                    place_holder.append(line)
                    inline_example = []
                    current_tag = ""
                    in_process = False
                    num_examples += 1
                    continue

            if in_process:
                inline_example.append(line)
            else:
                place_holder.append(line)
    with open(slate_file, "w") as f:
        f.write("".join(place_holder))
    if len(regex_problems) > 0:
        print "\nWarning: example files are not used for those with regex problems:  " + ",".join(regex_problems)
    print "\nDONE: %s updated, total %d example blocks, %d replaced, %d left untouched" % (slate_file, num_examples, num_replaced, num_untouched)


# Given the language and endpoint, finds the correct example code file and returns the necessary parts as a string
def get_example_content(tag):
    (language, endpoint) = tag.split(":")
    example_file = example_file_map[tag]
    if not os.path.isfile(example_file):
        print "%s:%s\tno such file [%s]" % (language, endpoint, example_file)
        return ""

    with open(example_file, "r") as f:
        file_content = f.read()

    example_pattern = example_regex_map[language]
    if example_pattern:
        match = example_regex_map[language].search(file_content)
        if match:
            return match.group(1) + match.group(2)
        else:
            raise ValueError("language-specific regex failed to extract content")
    else:
        return file_content


if len(sys.argv) != 2:
    usage()

slate_file = sys.argv[1]
if not os.path.isfile(slate_file):
    print "slate file [%s] does not exist" % slate_file
    sys.exit(1)
if not os.access(slate_file, os.W_OK):
    print "slate file [%s] is not writable" % slate_file
    sys.exit(1)

example_file_map = get_example_file_map()
example_regex_map = get_example_regex_map()
replace_file_content(slate_file)

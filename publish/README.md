How to publish binding artifacts
================================

Prerequisite
------------
If you don't have a github.com account and have it added to the basis team,
you need to do that now. IT or Seth can help you join the basis team.

Both your internal git and github.com accounts **must be** configured
with ssh access.


Step 0: prepare the source
--------------------------
Before you publish a new binding or a new version of it, you should have
already gone through internal dev/test with proper jira verification and
PR review/merge in the internal git repo.

When you are ready to publish, you should be on the master branch that
contains the newly merged pull request, if not checkout that hasg/tag.
This ensures the right files getting published to external github and
installation repos.

You **must** do a `mvn clean install; (cd java; mvn site)` in the top level directory.


Step 1: publish the code
------------------------

```
./publish-api.sh [java|js|php|python|ruby|...] [NEW_BRANCH_NAME, eg 0.5.1]
```

We are only going to publish one binding at a time, so you will need
to supply the name of the binding as the first argument to the script.
You will also need to specify the new remote github.com branch name.
Please follow the naming convention using semver, e.g. `0.5.1`.
We want to keep the major and minor numbers in the
semver same as the backend, leaving the patch number for binding
updates themselves.

The result of the step will create a new remote branch with newly
released code on it. Note that the source folder is expected to be
at <binding>/target/github-publish. If that's not there, update
the binding's pom to have things copied over there.


Step 2: pull request, merge, tag
--------------------------------

```
./github-pr-tag.sh [java|js|php|python|ruby|...] [BRANCH_NAME] [github.com_USERNAME]
```

`BRANCH_NAME` should be the same as what you specified in Step 1.

You will need your github.com username and password here, so get
them before running the script. You also need to ensure that your
ssh key is authorized to use with git@github.com:rosette-api.

This step automates the creation of a pull request from the new branch
to master, merges it, and tags with a name same as branch name. 


Step 3: publish api doc to gh-pages
-----------------------------------

```
./publish-doc.sh [js|php|python|ruby|...] [BRANCH_NAME]
```

`BRANCH_NAME` should be the same as what you specified in Step 1.

Again, you will need to specify the binding. The script will go into
the binding's `target/html` folder and grab files from there to push
to `gh-pages` branch in the github.com repo.

Note: Java binding uses maven so release plugin needs to run first (see 
Step 4 below) so that there's no SNAPSHOT in version string. After that:

```
./publish-java-doc.sh [git_tag/release_version, eg 0.5.0]
```

Resulting doc site will be externally visible at http://rosette-api.github.io/<binding>.


Step 4: publish installable to language-specific repos
------------------------------------------------------
Unless you have credentials to the repository where you want to
publish your page, you should ask for them first.

- Python/[PyPI](https://pypi.python.org)

  `(cd python; python setup.py sdist upload)`

- Java/[Maven Central](http://search.maven.org)

  `git clone git@github.com:rosette-api/java.git`
  `cd java; mvn release:prepare; mvn release:perform`

- PHP/[Packagist](https://packagist.org)

  Nothing needs to be done here, packagist will poll github changes automatically.

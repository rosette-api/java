How to publish binding artifacts
================================

Prerequisite
------------
If you don't have a github.com account and have it added to the basis team,
you need to do that now. IT or Seth can help you join the basis team.

Both your internal git and github.com accounts **must be** configured
with ssh access.

You will also need to install [jq](http://stedolan.github.io/jq/).

Step 0: prepare the source
--------------------------
Before you publish a new binding or a new version of it, you should have
already gone through internal dev/test with proper jira verification and
PR review/merge in the internal git repo.

For any bindings where the package manager requires a unique version, 
update the version in the relevant place before continuing.

When you are ready to publish, you should be on the master branch that
contains the newly merged pull request, if not checkout that hasg/tag.
This ensures the right files getting published to external github and
installation repos.

You **must** do a `mvn clean install; (cd java; mvn site)` in the top level directory.


Step 1: publish the code
------------------------

```
./publish/publish-api.sh [java|js|php|python|ruby|...] [NEW_BRANCH_NAME, eg 0.5.1]
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
./publish/github-pr-tag.sh [java|js|php|python|ruby|...] [BRANCH_NAME] [github.com_USERNAME]
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
./publish/publish-doc.sh [js|php|python|ruby|...] [BRANCH_NAME]
```

`BRANCH_NAME` should be the same as what you specified in Step 1.

Again, you will need to specify the binding. The script will go into
the binding's `target/html` folder and grab files from there to push
to `gh-pages` branch in the github.com repo.

Note: Java binding uses maven so release plugin needs to run first (see
Step 4 below) so that there's no SNAPSHOT in version string. After that:

```
./publish/publish-java-doc.sh [git_tag/release_version, eg 0.5.0]
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

- .Net (C#)/Nuget (https://www.nuget.org/)

  To publish, adjust the mvn call in the Bamboo job:
  Change from:
  `mvn clean install site -Dpublish=false` 
  to
  `mvn clean install site -Dpublish=true -Doldversion=X -Dnewversion=Y`  
  where X is the old version number and Y is the new version number
  The maven call automatically packages and delivers to Nuget.
  
- Node.js/[npm](https://www.npmjs.com/)

  To publish, run `mvn clean install` then navigate to the target/github-publish directory.
  If you have never published to npm before, run `npm adduser` and put in the credentials when prompted.
  Then run `npm publish` which will publish to npm. 
  Then run `npm pack` which will create a `.tgz` file in the target/github-publish directory.
  In the browser, go to the github site and click "releases" then "Draft a new release". Tag it with the version and any
  notes, then at the bottom click "add binaries" and add the `.tgz` file then "Publish Release".

Step 5: update features and functions slate source with new example code
------------------------------------------------------------------------

- `git clone git@git.basistech.net:raas/docs.git raasdocs`, if not previously cloned
- `(cd raasdocs; git checkout -b [BRANCH_NAME])`
- run `./publish/interpolate-slate.py <raasdocs>/slate/source/index.md`
- `(cd raasdocs/slate; bundle exec middleman build)` # you may need to install some tools, see README.md there
- test by loading `raasdocs/slate/build/index.html` in a browser
- `(cd raasdocs; git commit -a)`
- go to internal git site and create a pull request for James to merge
- James should review/try/merge and publish final file to 3scale portal

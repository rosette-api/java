#!/bin/bash -e
# This merges a pushed branch via a pull req and tags it

if [ "$#" -ne 3 ]; then
    echo "Usage: $0 [java|js|php|python|ruby|...] [BRANCH_NAME, eg 0.5.1] [github.com_USERNAME]"
    exit 1
fi

binding=$1
branch=$2
username=$3
repo=$binding
owner=rosette-api

echo -n $username@github.com password: 
read -s password
echo

exec 5>&1

# create a pull request
pr_resp=$(curl -s -u $username:$password https://api.github.com/repos/$owner/$repo/pulls -d"{\"title\": \"Pull request for $branch\", \"base\": \"master\", \"head\": \"$owner:$branch\"}" | tee /dev/fd/5)
pr_num=$(echo "$pr_resp" | jq .number | sed 's/"//g')
[ -z "${pr_num##*[!0-9]*}" ] && echo "failed to create pull request" && exit 1


# merge the PR
merge_resp=$(curl -s -X PUT -u $username:$password https://api.github.com/repos/$owner/$repo/pulls/$pr_num/merge -d"{\"commit_message\": \"Merge $branch to master\"}" | tee /dev/fd/5)
sha=$(echo "$merge_resp" | jq .sha | sed 's/"//g')
[[ ${#sha} != 40 ]] && echo "failed to merge pull request" && exit 1


# create a ref tag
curl -u $username:$password https://api.github.com/repos/$owner/$repo/git/refs -d"{\"ref\": \"refs/tags/$branch\",\"sha\": \"$sha\"}"

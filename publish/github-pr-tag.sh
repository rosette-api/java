#!/bin/bash -e
# This merges a pushed branch via a pull req and tags it

if [ "$#" -ne 2 ]; then
    echo "Usage: $0 [BRANCH_NAME] [github.com_USERNAME]"
    exit 1
fi

branch=$1
username=$2
repo=rosette-api
owner=basis-technology-corp

echo -n $username@github.com password: 
read -s password
echo

exec 5>&1

# create a pull request
pr_resp=$(curl -s -u $username:$password https://api.github.com/repos/$owner/$repo/pulls -d"{\"title\": \"Pull request for $branch\", \"base\": \"master\", \"head\": \"$username:$branch\"}" | tee /dev/fd/5)
pr_num=$(echo "$pr_resp" | jq .number | sed 's/"//g')
[ -z "${pr_num##*[!0-9]*}" ] && echo "failed to create pull request" && exit 1


# merge the PR
merge_resp=$(curl -s -X PUT -u $username:$password https://api.github.com/repos/$owner/$repo/pulls/$pr_num/merge -d"{\"commit_message\": \"Merge $branch to master\"}" | tee /dev/fd/5)
sha=$(echo "$merge_resp" | jq .sha | sed 's/"//g')
[[ ${#sha} != 40 ]] && echo "failed to merge pull request" && exit 1


# create a ref tag
curl -u $username:$password https://api.github.com/repos/$owner/$repo/git/refs -d"{\"ref\": \"refs/tags/$branch\",\"sha\": \"$sha\"}"


FROM ruby:latest
COPY . /main
ENV file categories.rb
ENV key 88afd6b4b18a11d1248639ecf399903c
ENV path examples
VOLUME ["/examples"]
WORKDIR "/examples/target/github-publish/examples"
CMD ruby $file $key

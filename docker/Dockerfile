FROM maven:3.3.3-jdk-8
MAINTAINER Fiona Hasanaj
ENV TZ=America/New_York

# install necessary software
RUN apt-get -y update && apt-get install -y vim  && apt-get install -y git

# copy over the necessary files
COPY run_java.sh /java/run_java.sh 
RUN chmod 755 /java/run_java.sh

# set working directory
WORKDIR java

# specify docker volume
VOLUME ["/source"]

# allow interactive bash inside docker container
CMD ./run_java.sh $API_KEY $FILENAME $ALT_URL $GIT_USERNAME $VERSION
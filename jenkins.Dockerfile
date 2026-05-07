FROM maven:3-amazoncorretto-25-al2023
LABEL source="https://github.com/rosette-api/java/blob/master/jenkins.Dockerfile"

RUN dnf install -y git && dnf clean all

COPY docker-entrypoint.sh /java/docker-entrypoint.sh
RUN chmod 755 /java/docker-entrypoint.sh

WORKDIR /java

VOLUME ["/source"]

ENTRYPOINT ["./docker-entrypoint.sh"]

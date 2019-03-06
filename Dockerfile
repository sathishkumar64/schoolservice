FROM openjdk:alpine
MAINTAINER sathish vasu 
VOLUME /tmp
EXPOSE 9098
ADD ./target/schoolservice-0.0.1-SNAPSHOT.jar schoolservice-0.0.1-SNAPSHOT.jar
RUN sh -c 'touch /schoolservice-0.0.1-SNAPSHOT.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","schoolservice-0.0.1-SNAPSHOT.jar"]

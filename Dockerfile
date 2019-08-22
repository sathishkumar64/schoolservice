FROM openjdk:8-jre-alpine
MAINTAINER sathish vasu 
VOLUME /tmp
EXPOSE 9098 9099

ADD ./target/schoolservice-0.0.1-SNAPSHOT.jar schoolservice-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","schoolservice-0.0.1-SNAPSHOT.jar"]



#ARG DEPENDENCY=target/dependency
#COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
#COPY ${DEPENDENCY}/META-INF /app/META-INF
#COPY ${DEPENDENCY}/BOOT-INF/classes /app
#ENTRYPOINT ["java","-cp","app:app/lib/*","com.schoolservice.SchoolServiceApplication"]

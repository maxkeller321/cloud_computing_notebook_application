FROM ubuntu:20.04

#FROM openjdk:8-jdk-alpine

RUN apt-get update && \
    apt-get install -y openjdk-8-jdk && \
    apt-get install -y ant && \
    apt-get clean;

RUN apt-get update && \
    apt-get install ca-certificates-java && \
    apt-get clean && \
    update-ca-certificates -f;

# Setup JAVA_HOME -- useful for docker commandline
ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64/
RUN export JAVA_HOME

RUN apt-get install -y iputils-ping

RUN apt -y install maven
RUN mvn -version

RUN apt-get update && apt-get install -y git
RUN git clone https://github.com/CC-Exercises/notebookapp.git

WORKDIR "/notebookapp"

RUN git checkout e02bcf92554394246d871d74117c3b2107bac937

ADD notebookapp-0.3.0/config.yml /notebookapp/config.yml

RUN mvn clean install

CMD java -jar target/notebookapp-0.3.0.jar server config.yml
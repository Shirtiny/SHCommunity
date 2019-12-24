FROM java:8

LABEL author="Shirtiny"

ADD community-0.0.1-SNAPSHOT.jar /community.jar

EXPOSE 8888

ENTRYPOINT ["java","-jar","/community.jar"]
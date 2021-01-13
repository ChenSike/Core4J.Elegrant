FROM openjdk
VOLUME /tmp
ADD Basic.jar /Basic.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/Basic.jar"]
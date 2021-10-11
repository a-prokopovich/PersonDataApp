FROM openjdk:11
COPY webapp/target/*.jar webapp-1.0.jar
VOLUME /tmp
ENTRYPOINT ["java","-jar","/webapp-1.0.jar"]

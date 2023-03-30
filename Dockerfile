FROM docker.io/maven:3.9.0-amazoncorretto-17 AS source
COPY src /usr/src/app/src  
COPY pom.xml /usr/src/app  

FROM source as build
RUN mvn -f /usr/src/app/pom.xml test-compile compile

FROM build as test
RUN mvn -f /usr/src/app/pom.xml test

FROM source as package
RUN mvn -f /usr/src/app/pom.xml clean package

FROM amazoncorretto:latest as deploy
COPY --from=package /usr/src/app/target/nixauth-0.0.1-SNAPSHOT.jar /usr/app/webapp.jar  
EXPOSE 8080  
ENTRYPOINT ["java","-jar","/usr/app/webapp.jar"]  

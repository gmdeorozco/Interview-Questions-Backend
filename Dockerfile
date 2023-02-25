FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM openjdk:latest

#COPY /target/java-interview-questions-0.0.1-SNAPSHOT.jar java-interview-questions-0.0.1-SNAPSHOT.jar
COPY --from=build /home/app/target/java-interview-questions-0.0.1-SNAPSHOT.jar /usr/local/lib/java-interview-questions-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/java-interview-questions-0.0.1-SNAPSHOT.jar"]

# mvn install -DskipTests 
# docker image build -t questions .target/java-interview-questions-0.0.1-SNAPSHOT.jar
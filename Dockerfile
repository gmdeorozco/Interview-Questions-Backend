FROM openjdk:latest
COPY /target/java-interview-questions-0.0.1-SNAPSHOT.jar java-interview-questions-0.0.1-SNAPSHOT.jar
CMD ["java","-jar","java-interview-questions-0.0.1-SNAPSHOT.jar"]

# mvn install -DskipTests 
# docker image build -t questions .target/java-interview-questions-0.0.1-SNAPSHOT.jar
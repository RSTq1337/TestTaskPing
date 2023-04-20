FROM mcr.microsoft.com/java/jre:17-zulu-ubuntu
WORKDIR /app
COPY target/TestTaskPing-0.0.1-SNAPSHOT.jar .
CMD ["java", "-jar", "TestTaskPing-0.0.1-SNAPSHOT.jar"]

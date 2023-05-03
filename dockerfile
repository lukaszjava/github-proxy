FROM eclipse-temurin:17-jdk-alpine
COPY target/github-proxy-0.0.1-SNAPSHOT.jar github-proxy-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/github-proxy-0.0.1-SNAPSHOT.jar"]
FROM eclipse-temurin:20-jdk

WORKDIR /app

COPY target/OpenSky-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]

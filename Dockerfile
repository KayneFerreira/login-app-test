FROM openjdk:21
WORKDIR /app
COPY ./target/logx-0.0.1-SNAPSHOT.jar /app
EXPOSE 8080
CMD ["java", "-jar", "logx-0.0.1-SNAPSHOT.jar"]

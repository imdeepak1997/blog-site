

FROM openjdk:17
COPY . /usr/src/myapp
WORKDIR /usr/src/myapp
RUN ./mvnw clean package
CMD ["java", "-jar", "target/users-0.0.1-SNAPSHOT.jar"]
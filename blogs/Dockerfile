

FROM openjdk:17
COPY . /usr/src/myapp
WORKDIR /usr/src/myapp
RUN ./mvnw clean package
EXPOSE 8081
CMD ["java", "-jar", "target/blogs-0.0.1-SNAPSHOT.jar"]
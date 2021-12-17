FROM maven:3.6.0-jdk-11-slim AS build

COPY ./ ./

RUN mvn clean install

CMD ["java", "-jar", "target/product-hexagonal-0.0.1-SNAPSHOT.jar"]
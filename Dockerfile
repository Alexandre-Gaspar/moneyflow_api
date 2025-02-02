# Estágio de build
FROM openjdk:21-jdk-slim AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn package -DskipTests

# Estágio de runtime
FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=build /app/target/api-moneyflow-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
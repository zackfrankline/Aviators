# ===============================
# Stage 1: Build the application
# ===============================
FROM eclipse-temurin:21-jdk-alpine AS builder

WORKDIR /app

COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

RUN ./mvnw dependency:go-offline

COPY src src

RUN ./mvnw clean package -DskipTests


# ===============================
# Stage 2: Run the application
# ===============================
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

COPY --from=builder /app/target/subscription-security-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 4002

ENTRYPOINT ["java", "-jar", "app.jar"]
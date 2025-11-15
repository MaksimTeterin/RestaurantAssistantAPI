# ================================
# 1. Build stage
# ================================
FROM eclipse-temurin:17-jdk AS builder

WORKDIR /app

# Copy Maven wrapper & config
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .

# ✅ Make mvnw executable (fix for Linux)
RUN chmod +x mvnw

# Download dependencies
RUN ./mvnw dependency:go-offline -B

# Copy the rest of the source
COPY src src

# Build the app
RUN ./mvnw clean package -DskipTests

# ================================
# 2. Runtime stage
# ================================
FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8000

ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=${PORT:-8000}"]

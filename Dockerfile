# Etapa 1: Build
FROM maven:3.9.9-eclipse-temurin-23 AS builder

WORKDIR /app

# Copiamos el pom y descargamos dependencias primero (cachea mejor)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Ahora copiamos el código fuente
COPY src ./src

# Build del proyecto
RUN mvn clean package -DskipTests

# Etapa 2: Runtime
FROM openjdk:23-jdk AS runner

WORKDIR /app

# Copiamos el JAR construido desde el builder
COPY --from=builder /app/target/Game-0.0.1-SNAPSHOT.jar app.jar


# Exponemos el puerto en el que corre Spring Boot
EXPOSE 8080

# Comando de arranque
ENTRYPOINT ["java", "-jar", "app.jar"]

# Usar una imagen base con Maven para construir el proyecto
FROM maven:3.6.3-jdk-11 as build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Usar OpenJDK para ejecutar la aplicación
FROM openjdk:11
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]

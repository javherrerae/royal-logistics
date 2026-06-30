# Usar la imagen oficial y ligera de Java 21
FROM eclipse-temurin:21-jdk-alpine

# Definir la zona horaria
ENV TZ=UTC

# Copiar el archivo ejecutable compilado al contenedor
COPY target/*.jar app.jar

# Puerto
EXPOSE 8087

# Ejecutar el microservicio
ENTRYPOINT ["java", "-jar", "/app.jar"]
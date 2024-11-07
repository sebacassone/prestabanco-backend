# Partimos de una imagen de Java 17 con Alpine (más ligera)
FROM openjdk:17-jdk-alpine
# Establecemos el directorio de trabajo dentro del contenedor
WORKDIR /app
# Copiamos el JAR generado en el contenedor
COPY build/libs/api.jar api.jar

# Exponemos el puerto 8000 (el que usa Spring Boot por defecto)
EXPOSE 8000
# Comando para ejecutar la aplicación cuando el contenedor arranque
ENTRYPOINT ["java", "-jar", "api.jar"]
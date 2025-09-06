# Etapa de construcción
FROM gradle:7.6.2-jdk17 AS build
WORKDIR /app

# Copiar archivos de configuración de Gradle
COPY build.gradle .
COPY settings.gradle .

# Descargar dependencias para almacenar en caché si no han cambiado
RUN gradle build -x test --no-daemon

# Copiar el código fuente del proyecto
COPY . .

# Compilar la aplicación
RUN gradle clean build -x test --no-daemon

# Etapa de ejecución
FROM openjdk:17-jdk-alpine
WORKDIR /app

# Copiar el archivo JAR generado desde la etapa de construcción
COPY --from=build /app/build/libs/*.jar ./client-app.jar

# Ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "client-app.jar"]

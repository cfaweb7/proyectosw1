# 1) Build stage: compila tu jar con Maven Wrapper
FROM eclipse-temurin:17-jdk AS build

WORKDIR /app

# Copiamos solo lo estrictamente necesario para cachear deps de Maven
COPY mvnw .
COPY mvnw.cmd .
COPY .mvn/ .mvn
COPY pom.xml .

# Damos permiso de ejecución al wrapper
RUN chmod +x mvnw

# Pre-descarga las dependencias para aprovechar cache
RUN ./mvnw dependency:go-offline -B

# Copiamos el código fuente y empaquetamos
COPY src/ src/
RUN ./mvnw clean package -DskipTests -B

# 2) Runtime stage: imagen mínima con JRE
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copiamos el jar generado
ARG JAR_FILE=target/*.jar
COPY --from=build /app/${JAR_FILE} app.jar

# Render inyecta la variable $PORT; la exponemos para documentación
EXPOSE 8080

# Arrancamos usando la variable PORT si está definida
ENTRYPOINT ["sh","-c","java -Dserver.port=${PORT:-8080} -jar /app/app.jar"]

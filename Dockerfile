# Estágio de build
FROM gradle:8.5-jdk21 AS build
WORKDIR /app

# Copia arquivos de build e wrapper
COPY gradlew gradlew
COPY gradle gradle
COPY build.gradle settings.gradle ./
COPY src ./src

# Permite executar o wrapper no container
RUN chmod +x gradlew




# Estágio de execução
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Copia o JAR empacotado
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
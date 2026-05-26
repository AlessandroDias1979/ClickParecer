
 # Estágio de build
 FROM eclipse-temurin:17-jdk-jammy AS build
 WORKDIR /app

 # Copia os arquivos de configuração do Gradle para o estágio de build
 COPY gradle gradle
 COPY build.gradle settings.gradle gradlew gradlew.bat ./

 # Now this will work because the file actually exists!
 RUN chmod +x gradlew

 # Baixa as dependências
 RUN ./gradlew dependencies --no-daemon

 # Estágio de execução
 COPY src ./src
 RUN ./gradlew build -x test --no-daemon

 # Estágio de execução
 FROM eclipse-temurin:17-jre-jammy
 WORKDIR /app

 # Copia o JAR gerado para o estágio final
 COPY --from=build /app/build/libs/*.jar app.jar

 EXPOSE 8080
 ENTRYPOINT ["java", "-jar", "app.jar"]
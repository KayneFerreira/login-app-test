# Etapa 1: Imagem base do Java
FROM openjdk:17-jdk as build

# Diretório de trabalho dentro do container
WORKDIR /app

# Copia o arquivo JAR gerado no seu sistema local para o container
COPY target/demo.jar demo.jar

# Etapa 2: Imagem para rodar a aplicação
FROM openjdk:17-jre

# Diretório de trabalho no container
WORKDIR /app

# Copiar o arquivo JAR da etapa anterior
COPY --from=build /app/demo.jar /app/demo.jar

# Expor a porta na qual a aplicação vai rodar (caso seja Spring Boot, por padrão, é a 8080)
EXPOSE 8080

# Comando para rodar a aplicação ao iniciar o container
ENTRYPOINT ["java", "-jar", "demo.jar"]


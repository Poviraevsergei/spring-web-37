FROM openjdk:21-ea-oracle
ARG jar_path=target/spring-web-37.jar
RUN mkdir /group37
WORKDIR /group37
COPY ${jar_path} /group37
ENTRYPOINT ["java", "-jar","spring-web-37.jar"]
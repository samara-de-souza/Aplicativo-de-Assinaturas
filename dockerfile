FROM eclipse-temurin:24-jdk AS build
WORKDIR /app

COPY .mvn .mvn
COPY mvnw mvnw
COPY pom.xml pom.xml

RUN sed -i 's/\r$//' mvnw && chmod +x mvnw
RUN ./mvnw dependency:go-offline

COPY src src
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:24-jre
WORKDIR /app

RUN groupadd --system app && useradd --system --gid app app

COPY --from=build /app/target/app-assinatura-0.0.1-SNAPSHOT.jar app.jar

RUN chown -R app:app /app
USER app

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
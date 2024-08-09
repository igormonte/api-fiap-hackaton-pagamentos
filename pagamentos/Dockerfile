FROM maven:3.8.5-openjdk-17 as build

COPY . /opt/project

WORKDIR /opt/project

RUN mvn -B package -DskipTests

RUN cp ./target/*.jar ./app.jar

FROM build
LABEL authors="igormonte"



ENTRYPOINT ["java", "-jar", "app.jar"]
CMD ["--spring.profiles.active=prod"]
FROM eclipse-temurin:26
WORKDIR /opt/app
COPY target/*.jar probaFeladat.jar
CMD ["java", "-jar", "probaFeladat.jar"]
FROM openjdk:24-oraclelinux9
COPY build/libs/*.jar hr-system.jar
ENTRYPOINT ["java", "-jar", "/hr-system.jar"]

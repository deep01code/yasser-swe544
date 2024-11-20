FROM openjdk:8-jre-alpine
#RUN pwd
ADD build/libs/*.jar app.jar
EXPOSE 80
ENTRYPOINT [ "java", "-Dspring.profiles.active=prod", "-jar", "app.jar" ]

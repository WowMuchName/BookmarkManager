FROM adoptopenjdk:11-jre
# Create a dedicated account for the service
RUN useradd --create-home --shell /bin/bash --user-group service
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
USER service
ENTRYPOINT ["java","-jar","/app.jar"]

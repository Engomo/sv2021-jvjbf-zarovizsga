FROM adoptopenjdk:16-jre-hotspot
RUN mkdir /opt/app
COPY target/final-exam-0.0.1-SNAPSHOT.jar-0.0.1-SNAPSHOT.jar /opt/app/final-exam.jar
CMD ["java", "-jar", "/opt/app/final-exam.jar"]
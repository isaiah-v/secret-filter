FROM adoptopenjdk:11-jre-hotspot

RUN mkdir /opt/app
COPY build/libs/secret-filter.jar /opt/app

CMD ["java", "-jar", "/opt/app/secret-filter.jar"]
FROM openjdk:17

ENV PORT=8000
ENV APIKEY=

COPY ./vintageforlife.jar /vintageforlife.jar
ENTRYPOINT ["java","-jar","/vintageforlife.jar"]
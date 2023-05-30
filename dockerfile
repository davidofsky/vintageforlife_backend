FROM openjdk:17

ENV PORT=8000
ENV ORS=https://api.openrouteservice.org
ENV ORS_PUBLIC=https://api.openrouteservice.org
ENV API_KEY=5b3ce3597851110001cf62489742ff7ee3cb432b8e60eec5bab84852

COPY ./vintageforlife.jar /vintageforlife.jar
ENTRYPOINT ["java","-jar","/vintageforlife.jar"]
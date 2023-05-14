package com.vintageforlife.routeplanner.helpers;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

public class OpenRouteService {
    static private String baseUrl = "https://api.openrouteservice.org";
    static private String apiKeyParam = "api_key=5b3ce3597851110001cf62489742ff7ee3cb432b8e60eec5bab84852";

    public static List<String> getCoordinates(String address) throws Exception {
        String geoCodeUrl = baseUrl + "/geocode/search";
        String textParam = "text=" + URLEncoder.encode(address, StandardCharsets.UTF_8);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(geoCodeUrl + "?" + apiKeyParam + "&" + textParam))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        String jsonpathCoordinatesPath = "features[0].geometry.coordinates";
        DocumentContext jsonContext = JsonPath.parse(response.body());
        return jsonContext.read(jsonpathCoordinatesPath);
    }

    public static Double getDistance(List<String> coordsStart, List<String> coordsEnd) throws Exception {
        String coordsStartString = coordsStart.toString();
        String coordsEndString = coordsEnd.toString();

        String directionsUrl = baseUrl + "/v2/directions/driving-car";
        String startParam = "start=" + coordsStartString.substring(1, coordsStartString.length() - 2);
        String endParam = "end=" + coordsEndString.substring(1, coordsEndString.length() - 2);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(directionsUrl + "?" + apiKeyParam + "&" + startParam + "&" + endParam))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        String jsonpathCoordinatesPath = "$.features[0].properties.summary.duration";
        DocumentContext jsonContext = JsonPath.parse(response.body());
        return jsonContext.read(jsonpathCoordinatesPath);
    }
}

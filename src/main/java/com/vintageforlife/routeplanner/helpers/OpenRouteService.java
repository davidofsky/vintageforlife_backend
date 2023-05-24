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
    static private String apiKeyParam = "api_key=" + System.getenv("APIKEY");

    public static List<String> getCoordinates(String address) throws Exception {
        String geoCodeUrl = baseUrl + "/geocode/search";
        String textParam = "text=" + URLEncoder.encode(address, StandardCharsets.UTF_8);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(geoCodeUrl + "?" + apiKeyParam + "&" + textParam))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("ORS responded with status: " + response.statusCode());
        String jsonpathCoordinatesPath = "features[0].geometry.coordinates";
        DocumentContext jsonContext = JsonPath.parse(response.body());
        return jsonContext.read(jsonpathCoordinatesPath);
    }

    // returns response from ORS as DocumentContext
    public static DocumentContext getDistance(List<String> coordsStart, List<String> coordsEnd) throws Exception {
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
        System.out.println("ORS responded with status: " + response.statusCode());
        return JsonPath.parse(response.body());
    }
}

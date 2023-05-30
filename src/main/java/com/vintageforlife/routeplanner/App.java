package com.vintageforlife.routeplanner;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;

import com.sun.net.httpserver.HttpServer;
import com.vintageforlife.routeplanner.helpers.ExchangeHelper;

class App {
    public static void main(String[] args) throws IOException {
        // check env variables
        String env_port = System.getenv("PORT");
        if (env_port == null || env_port.trim() == "") throw new Error("Missing env var 'PORT'");
        String env_apiKey = System.getenv("API_KEY");
        if (env_apiKey == null || env_apiKey.trim() == "") throw new Error("Missing env var 'API_KEY'");
        String env_ors = System.getenv("ORS");
        if (env_ors == null || env_ors.trim() == "") throw new Error("Missing env var 'ORS'");
        String env_orsPublic = System.getenv("ORS_PUBLIC");
        if (env_orsPublic == null || env_orsPublic.trim() == "") throw new Error("Missing env var 'ORS_PUBLIC'");

        // set port and init server
        int port = Integer.parseInt(env_port);
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 1);

        // routing
        server.createContext("/api", (exchange -> {
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");

            if (exchange.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
                exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, OPTIONS");
                exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type,Authorization");
                exchange.sendResponseHeaders(204, -1);
                return;
            }

            if ("POST".equals(exchange.getRequestMethod())) {
                try { 
                    String body = ExchangeHelper.getBody(exchange);
                    List<Address> addressList = Address.parseJson(body);
                    // create route of addresses
                    Route route = new Route(addressList); 
                    // add application/json header to response and return the route
                    exchange.getResponseHeaders().add("content-type", "application/json");
                    ExchangeHelper.respond(exchange, 200, route.toString()); 
                    exchange.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    // send internal server error
                    exchange.sendResponseHeaders(500, 0);
                    exchange.close();
                }
            } else {
                exchange.sendResponseHeaders(400, 0); // return 400 if not a POST request
                exchange.close();
            }
        }));

        // default executor
        server.setExecutor(null);

        System.out.println("Server is listening on port: " + port);
        server.start();
    }
}
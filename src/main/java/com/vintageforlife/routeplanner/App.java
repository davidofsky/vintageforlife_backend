package com.vintageforlife.routeplanner;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;

import com.sun.net.httpserver.HttpServer;
import com.vintageforlife.routeplanner.helpers.ExchangeHelper;

class App {
    public static void main(String[] args) throws IOException {
        int port = 8000;
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
                    Route route = new Route(addressList); 

                    exchange.getResponseHeaders().add("content-type", "application/json");
                    ExchangeHelper.respond(exchange, 200, route.toString()); 
                } catch (Exception e) {
                    e.printStackTrace();
                }


                exchange.sendResponseHeaders(200, 0);
                exchange.close();
            } else {
                exchange.sendResponseHeaders(400, 0); // return 400 if not get request
                exchange.close();
            }
        }));

        // default executor
        server.setExecutor(null);

        System.out.println("Server is listening on port: " + port);
        server.start();
    }
}
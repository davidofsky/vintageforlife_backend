package com.vintageforlife.routeplanner;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;

import com.sun.net.httpserver.HttpServer;
import com.vintageforlife.routeplanner.helpers.ExchangeHelper;
import com.vintageforlife.routeplanner.helpers.OpenRouteService;

class App {
    public static void main(String[] args) throws IOException {
        int port = 8000;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 1);

        // routing
        server.createContext("/api", (exchange -> {
            if ("POST".equals(exchange.getRequestMethod())) {
                String body = ExchangeHelper.getBody(exchange);
                List<Address> addressList = Address.parseJson(body);
                Address first = addressList.get(0);
                Address second = addressList.get(1);

                try {
                    System.out.println(first.coordinates);
                    System.out.println(second.coordinates);

                    Double distance = OpenRouteService.getDistance(first.coordinates, second.coordinates);
                    System.out.println(distance);

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
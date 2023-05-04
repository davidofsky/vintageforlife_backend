package vintageforlife;
import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

import vintageforlife.Helpers.ExchangeHelper;

class App {
    public static void main(String[] args) throws IOException {
        int port = 8000;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        // routing
        server.createContext("/api/calc", (exchange -> {
            if ("GET".equals(exchange.getRequestMethod())) {
                String address = ExchangeHelper.getHeaderValue(exchange, "addresses");
                String apiKey = ExchangeHelper.getHeaderValue(exchange, "key");

                if (apiKey.trim().length() <= 0) {
                    ExchangeHelper.respond(exchange, 400, "Missing header 'key'");
                    return;
                }
                if (address.trim().length() <= 0) {
                    ExchangeHelper.respond(exchange, 400, "Missing header 'addresses'");
                    return;
                }

                System.out.println(address);
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
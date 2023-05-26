package com.vintageforlife.routeplanner.helpers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

// contains static functions that help simplify code for HttpExchange
public class ExchangeHelper {
    public static void respond(HttpExchange httpExchange, int code, String message ) {
        try {
            byte[] bs = message.getBytes("UTF-8");
            httpExchange.sendResponseHeaders(code, bs.length);
            OutputStream os = httpExchange.getResponseBody();
            os.write(bs);
            os.close();
            httpExchange.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void respond(HttpExchange httpExchange, int code ) {
        respond(httpExchange, code, "");
    }

    public static String getHeaderValue(HttpExchange httpExchange, String key) {
        Headers headers = httpExchange.getRequestHeaders();
        if (!headers.containsKey(key)) return "";
        return headers.getFirst(key);
    }


    public static String getBody(HttpExchange httpExchange) {
        String text = new BufferedReader(
            new InputStreamReader(httpExchange.getRequestBody(), StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));
        return text;
    }
}
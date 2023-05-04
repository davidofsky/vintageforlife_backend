package vintageforlife.Helpers;
import java.io.OutputStream;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

// contains static functions that help simplify code for HttpExchange
public class ExchangeHelper {
    public static void respond(HttpExchange httpExchange, int code, String message ) {
        try {
            httpExchange.sendResponseHeaders(code, message.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(message.getBytes());
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

}

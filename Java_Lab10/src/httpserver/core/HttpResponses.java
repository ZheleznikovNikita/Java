package httpserver.core;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class HttpResponses {
    private HttpResponses() {
    }

    public static int sendText(HttpExchange exchange, int statusCode, String responseBody) throws IOException {
        return sendBytes(
                exchange,
                statusCode,
                "text/plain; charset=UTF-8",
                responseBody.getBytes(StandardCharsets.UTF_8)
        );
    }

    public static int sendJson(HttpExchange exchange, int statusCode, String responseBody) throws IOException {
        return sendBytes(
                exchange,
                statusCode,
                "application/json; charset=UTF-8",
                responseBody.getBytes(StandardCharsets.UTF_8)
        );
    }

    public static int sendBytes(
            HttpExchange exchange,
            int statusCode,
            String contentType,
            byte[] responseBody
    ) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", contentType);
        exchange.sendResponseHeaders(statusCode, responseBody.length);
        try (OutputStream outputStream = exchange.getResponseBody()) {
            outputStream.write(responseBody);
        }
        return statusCode;
    }

    public static int sendNoContent(HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(204, -1);
        return 204;
    }
}

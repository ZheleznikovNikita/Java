package httpserver.handlers;

import com.sun.net.httpserver.HttpExchange;
import httpserver.SimpleHttpServer;
import httpserver.core.BaseHandler;
import httpserver.core.HttpResponses;
import httpserver.core.QueryParser;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class DataHandler extends BaseHandler {
    private static final String PATH_PREFIX = "/data";

    @Override
    protected int handleRequest(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        if ("PUT".equals(method)) {
            return handlePut(exchange);
        }
        if ("DELETE".equals(method)) {
            return handleDelete(exchange);
        }
        return methodNotAllowed(exchange, "PUT, DELETE");
    }

    private int handlePut(HttpExchange exchange) throws IOException {
        String key = extractKey(exchange);
        byte[] requestBody;
        try (InputStream inputStream = exchange.getRequestBody()) {
            requestBody = inputStream.readAllBytes();
        }

        if (requestBody.length == 0) {
            return HttpResponses.sendText(exchange, 400, "Empty request body");
        }

        SimpleHttpServer.saveData(key, new String(requestBody, StandardCharsets.UTF_8));
        return HttpResponses.sendText(exchange, 201, "Saved key: " + key);
    }

    private int handleDelete(HttpExchange exchange) throws IOException {
        String key = extractKey(exchange);
        if (SimpleHttpServer.deleteData(key)) {
            return HttpResponses.sendNoContent(exchange);
        }

        return HttpResponses.sendText(exchange, 404, "Data not found for key: " + key);
    }

    private String extractKey(HttpExchange exchange) {
        String key = null;
        String rawPath = exchange.getRequestURI().getRawPath();
        if (rawPath.length() > PATH_PREFIX.length()) {
            if (!rawPath.startsWith(PATH_PREFIX + "/")) {
                throw new httpserver.core.BadRequestException("Invalid data path");
            }
            key = QueryParser.decode(rawPath.substring((PATH_PREFIX + "/").length()));
        }

        if (key == null || key.isEmpty()) {
            Map<String, String> params = QueryParser.parse(exchange.getRequestURI().getRawQuery());
            key = params.get("key");
        }

        if (key == null || key.isEmpty()) {
            throw new httpserver.core.BadRequestException("Missing or empty key parameter");
        }

        return key;
    }
}

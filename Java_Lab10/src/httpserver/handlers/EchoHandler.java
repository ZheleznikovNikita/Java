package httpserver.handlers;

import com.sun.net.httpserver.HttpExchange;
import httpserver.core.BaseHandler;
import httpserver.core.HttpResponses;

import java.io.IOException;
import java.io.InputStream;

public class EchoHandler extends BaseHandler {
    @Override
    protected int handleRequest(HttpExchange exchange) throws IOException {
        if (!"POST".equals(exchange.getRequestMethod())) {
            return methodNotAllowed(exchange, "POST");
        }

        byte[] requestBody;
        try (InputStream inputStream = exchange.getRequestBody()) {
            requestBody = inputStream.readAllBytes();
        }

        if (requestBody.length == 0) {
            return HttpResponses.sendText(exchange, 400, "Empty request body");
        }

        return HttpResponses.sendBytes(exchange, 200, "text/plain; charset=UTF-8", requestBody);
    }
}

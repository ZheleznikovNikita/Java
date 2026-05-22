package httpserver.handlers;

import com.sun.net.httpserver.HttpExchange;
import httpserver.core.BaseHandler;
import httpserver.core.HttpResponses;
import httpserver.core.QueryParser;

import java.io.IOException;
import java.util.Map;

public class HelloHandler extends BaseHandler {
    @Override
    protected int handleRequest(HttpExchange exchange) throws IOException {
        if (!"GET".equals(exchange.getRequestMethod())) {
            return methodNotAllowed(exchange, "GET");
        }

        Map<String, String> params = QueryParser.parse(exchange.getRequestURI().getRawQuery());
        if (!params.containsKey("name")) {
            return HttpResponses.sendText(exchange, 200, "Hello, World!");
        }

        String name = params.get("name");
        if (name == null || name.isEmpty()) {
            return HttpResponses.sendText(exchange, 400, "Missing or empty name parameter");
        }

        return HttpResponses.sendText(exchange, 200, "Hello, " + name + "!");
    }
}

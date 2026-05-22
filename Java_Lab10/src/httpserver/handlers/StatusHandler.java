package httpserver.handlers;

import com.sun.net.httpserver.HttpExchange;
import httpserver.SimpleHttpServer;
import httpserver.core.BaseHandler;
import httpserver.core.HttpResponses;

import java.io.IOException;

public class StatusHandler extends BaseHandler {
    @Override
    protected int handleRequest(HttpExchange exchange) throws IOException {
        if (!"GET".equals(exchange.getRequestMethod())) {
            return methodNotAllowed(exchange, "GET");
        }

        String json = String.format(
                "{\"status\":\"ok\",\"requests\":%d,\"uptime\":%d}",
                SimpleHttpServer.getRequestCount(),
                SimpleHttpServer.getUptimeSeconds()
        );
        return HttpResponses.sendJson(exchange, 200, json);
    }
}

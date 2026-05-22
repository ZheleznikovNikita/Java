package httpserver.handlers;

import com.sun.net.httpserver.HttpExchange;
import httpserver.core.BaseHandler;
import httpserver.core.HttpResponses;

import java.io.IOException;

public class NotFoundHandler extends BaseHandler {
    @Override
    protected int handleRequest(HttpExchange exchange) throws IOException {
        return HttpResponses.sendText(exchange, 404, "Not Found");
    }
}

package httpserver.core;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import httpserver.SimpleHttpServer;

import java.io.IOException;

public abstract class BaseHandler implements HttpHandler {
    @Override
    public final void handle(HttpExchange exchange) {
        long startedAt = System.nanoTime();
        int statusCode = 500;

        SimpleHttpServer.incrementRequestCount();
        try {
            statusCode = handleRequest(exchange);
        } catch (BadRequestException e) {
            statusCode = sendSafely(exchange, 400, e.getMessage());
        } catch (Exception e) {
            SimpleHttpServer.logError(e);
            statusCode = sendSafely(exchange, 500, "Internal Server Error");
        } finally {
            long processingTimeMillis = TimeUnitUtils.toMillis(System.nanoTime() - startedAt);
            SimpleHttpServer.logRequest(
                    exchange.getRequestMethod(),
                    exchange.getRequestURI().toString(),
                    statusCode,
                    processingTimeMillis
            );
            exchange.close();
        }
    }

    protected abstract int handleRequest(HttpExchange exchange) throws IOException;

    protected int methodNotAllowed(HttpExchange exchange, String allowedMethods) throws IOException {
        exchange.getResponseHeaders().set("Allow", allowedMethods);
        return HttpResponses.sendText(exchange, 405, "Method Not Allowed. Use " + allowedMethods + ".");
    }

    private int sendSafely(HttpExchange exchange, int statusCode, String message) {
        try {
            return HttpResponses.sendText(exchange, statusCode, message);
        } catch (IOException e) {
            SimpleHttpServer.logError(e);
            return statusCode;
        }
    }
}

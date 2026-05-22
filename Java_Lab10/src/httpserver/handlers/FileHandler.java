package httpserver.handlers;

import com.sun.net.httpserver.HttpExchange;
import httpserver.core.BaseHandler;
import httpserver.core.HttpResponses;
import httpserver.core.QueryParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Map;

public class FileHandler extends BaseHandler {
    private static final String PATH_PREFIX = "/files";
    private static final Map<String, String> CONTENT_TYPES = Map.ofEntries(
            Map.entry(".html", "text/html; charset=UTF-8"),
            Map.entry(".htm", "text/html; charset=UTF-8"),
            Map.entry(".css", "text/css; charset=UTF-8"),
            Map.entry(".js", "application/javascript; charset=UTF-8"),
            Map.entry(".json", "application/json; charset=UTF-8"),
            Map.entry(".txt", "text/plain; charset=UTF-8"),
            Map.entry(".jpg", "image/jpeg"),
            Map.entry(".jpeg", "image/jpeg"),
            Map.entry(".png", "image/png"),
            Map.entry(".gif", "image/gif"),
            Map.entry(".svg", "image/svg+xml; charset=UTF-8"),
            Map.entry(".ico", "image/x-icon")
    );

    private final Path publicDirectory;

    public FileHandler(Path publicDirectory) {
        this.publicDirectory = publicDirectory.toAbsolutePath().normalize();
    }

    @Override
    protected int handleRequest(HttpExchange exchange) throws IOException {
        if (!"GET".equals(exchange.getRequestMethod())) {
            return methodNotAllowed(exchange, "GET");
        }

        String rawPath = exchange.getRequestURI().getRawPath();
        if (rawPath.length() <= PATH_PREFIX.length() || !rawPath.startsWith(PATH_PREFIX + "/")) {
            return HttpResponses.sendText(exchange, 400, "Missing file name");
        }

        String requestedName = QueryParser.decode(rawPath.substring((PATH_PREFIX + "/").length()));
        Path target = publicDirectory.resolve(requestedName).normalize();

        if (!target.startsWith(publicDirectory)) {
            return HttpResponses.sendText(exchange, 403, "Access denied");
        }
        if (!Files.exists(target)) {
            return HttpResponses.sendText(exchange, 404, "File not found");
        }
        if (Files.isDirectory(target)) {
            return HttpResponses.sendText(exchange, 403, "Directory access is forbidden");
        }

        byte[] body = Files.readAllBytes(target);
        return HttpResponses.sendBytes(exchange, 200, detectContentType(target), body);
    }

    private String detectContentType(Path target) {
        String fileName = target.getFileName().toString().toLowerCase(Locale.ROOT);
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex < 0) {
            return "application/octet-stream";
        }

        return CONTENT_TYPES.getOrDefault(fileName.substring(dotIndex), "application/octet-stream");
    }
}

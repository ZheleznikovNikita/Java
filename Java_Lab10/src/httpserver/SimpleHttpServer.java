package httpserver;

import com.sun.net.httpserver.HttpServer;
import httpserver.core.AccessLogger;
import httpserver.handlers.DataHandler;
import httpserver.handlers.EchoHandler;
import httpserver.handlers.FileHandler;
import httpserver.handlers.HelloHandler;
import httpserver.handlers.NotFoundHandler;
import httpserver.handlers.StatusHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class SimpleHttpServer {
    private static final int DEFAULT_PORT = 8080;
    private static final Path PUBLIC_DIRECTORY = Path.of("public");
    private static final Path ACCESS_LOG_FILE = Path.of("access.log");

    private static final AtomicLong REQUEST_COUNTER = new AtomicLong(0);
    private static final long START_TIME_MILLIS = System.currentTimeMillis();
    private static final Map<String, String> DATA_STORAGE = new ConcurrentHashMap<>();
    private static final AccessLogger ACCESS_LOGGER = new AccessLogger(ACCESS_LOG_FILE);
    private static final ExecutorService SERVER_EXECUTOR = Executors.newCachedThreadPool();

    public static void main(String[] args) throws IOException {
        int port = readPort(args);
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.setExecutor(SERVER_EXECUTOR);

        server.createContext("/hello", new HelloHandler());
        server.createContext("/status", new StatusHandler());
        server.createContext("/echo", new EchoHandler());
        server.createContext("/files", new FileHandler(PUBLIC_DIRECTORY));
        server.createContext("/data", new DataHandler());
        server.createContext("/", new NotFoundHandler());

        Runtime.getRuntime().addShutdownHook(new Thread(() -> stopServer(server), "server-shutdown"));
        server.start();

        System.out.println("HTTP server started on port " + port);
        System.out.println("Available endpoints: /hello, /status, /echo, /files/{filename}, /data");
        System.out.println("Access log: " + ACCESS_LOG_FILE.toAbsolutePath());
    }

    public static long incrementRequestCount() {
        return REQUEST_COUNTER.incrementAndGet();
    }

    public static long getRequestCount() {
        return REQUEST_COUNTER.get();
    }

    public static long getUptimeSeconds() {
        return (System.currentTimeMillis() - START_TIME_MILLIS) / 1000;
    }

    public static void logRequest(String method, String url, int statusCode, long processingTimeMillis) {
        ACCESS_LOGGER.logRequest(method, url, statusCode, processingTimeMillis);
    }

    public static void logError(Throwable throwable) {
        ACCESS_LOGGER.logError(throwable);
    }

    public static void saveData(String key, String value) {
        DATA_STORAGE.put(key, value);
    }

    public static boolean deleteData(String key) {
        return DATA_STORAGE.remove(key) != null;
    }

    private static int readPort(String[] args) {
        if (args.length == 0) {
            return DEFAULT_PORT;
        }

        try {
            int port = Integer.parseInt(args[0]);
            if (port < 1 || port > 65535) {
                throw new NumberFormatException("Port is out of range");
            }
            return port;
        } catch (NumberFormatException e) {
            System.out.println("Invalid port, default port " + DEFAULT_PORT + " will be used");
            return DEFAULT_PORT;
        }
    }

    private static void stopServer(HttpServer server) {
        server.stop(0);
        SERVER_EXECUTOR.shutdownNow();
        ACCESS_LOGGER.close();

        try {
            SERVER_EXECUTOR.awaitTermination(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

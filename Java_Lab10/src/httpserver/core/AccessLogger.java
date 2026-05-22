package httpserver.core;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

public class AccessLogger implements AutoCloseable {
    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final Path logFile;
    private final ExecutorService logExecutor = Executors.newSingleThreadExecutor(runnable -> {
        Thread thread = new Thread(runnable, "access-logger");
        thread.setDaemon(false);
        return thread;
    });

    public AccessLogger(Path logFile) {
        this.logFile = logFile;
    }

    public void logRequest(String method, String url, int statusCode, long processingTimeMillis) {
        String line = String.format(
                "%s | %s | %s | %d | %d ms%n",
                DATE_TIME_FORMAT.format(LocalDateTime.now()),
                method,
                url,
                statusCode,
                processingTimeMillis
        );
        writeAsync(line);
    }

    public void logError(Throwable throwable) {
        StringWriter buffer = new StringWriter();
        throwable.printStackTrace(new PrintWriter(buffer));
        String line = DATE_TIME_FORMAT.format(LocalDateTime.now())
                + " | ERROR | "
                + buffer
                + System.lineSeparator();
        writeAsync(line);
    }

    private void writeAsync(String line) {
        try {
            logExecutor.execute(() -> writeLine(line));
        } catch (RejectedExecutionException ignored) {
        }
    }

    private void writeLine(String line) {
        try {
            Files.writeString(
                    logFile,
                    line,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );
        } catch (Exception e) {
            System.out.println("Failed to write access log: " + e.getMessage());
        }
    }

    @Override
    public void close() {
        logExecutor.shutdown();
        try {
            if (!logExecutor.awaitTermination(3, TimeUnit.SECONDS)) {
                logExecutor.shutdownNow();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logExecutor.shutdownNow();
        }
    }
}

package chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class ChatServer {
    private static final int DEFAULT_PORT = 8080;
    private static final long PING_INTERVAL_MS = 30_000;
    private static final long PING_TIMEOUT_MS = 10_000;
    private static final Path LOG_FILE = Path.of("chat.log");
    private static final DateTimeFormatter LOG_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final List<ClientHandler> clients = Collections.synchronizedList(new ArrayList<>());
    private static final ExecutorService clientPool = Executors.newCachedThreadPool();
    private static final ExecutorService logPool = Executors.newSingleThreadExecutor();
    private static final ScheduledExecutorService pingPool = Executors.newSingleThreadScheduledExecutor();

    public static void main(String[] args) {
        int port = readPort(args);
        startPingMonitor();

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Чат-сервер запущен на порту " + port);
            System.out.println("Лог сообщений: " + LOG_FILE.toAbsolutePath());

            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                System.out.println("Новое подключение: " + socket.getInetAddress().getHostAddress() + ":" + socket.getPort());
                clientPool.execute(new ClientHandler(socket));
            }
        } catch (SocketException e) {
            System.out.println("Сервер остановлен: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Ошибка сервера: " + e.getMessage());
        } finally {
            shutdown();
        }
    }

    public static boolean registerClient(ClientHandler client) {
        synchronized (clients) {
            if (isNicknameTaken(client.getNickname())) {
                return false;
            }
            clients.add(client);
        }

        String message = "Пользователь " + client.getNickname() + " подключился";
        System.out.println(message + " (" + client.getClientAddress() + ":" + client.getClientPort() + ")");
        logAsync("CONNECT " + client.getNickname() + " from " + client.getClientAddress() + ":" + client.getClientPort());
        broadcastSystem(message, client);
        return true;
    }

    public static void broadcastMessage(String message, ClientHandler sender) {
        String formattedMessage = sender.getNickname() + ": " + message;
        synchronized (clients) {
            for (ClientHandler client : clients) {
                if (client != sender) {
                    client.sendMessage(formattedMessage);
                }
            }
        }
        System.out.println(formattedMessage);
        logAsync("PUBLIC " + formattedMessage);
    }

    public static void sendPrivateMessage(String targetNickname, String message, ClientHandler sender) {
        Optional<ClientHandler> target = findByNickname(targetNickname);
        if (target.isEmpty()) {
            sender.sendMessage("[Сервер] Пользователь " + targetNickname + " не найден");
            return;
        }

        ClientHandler targetClient = target.get();
        String incomingMessage = "[Лично] " + sender.getNickname() + ": " + message;
        String outgoingMessage = "[Лично -> " + targetClient.getNickname() + "] " + message;

        targetClient.sendMessage(incomingMessage);
        sender.sendMessage(outgoingMessage);
        System.out.println("[Лично] " + sender.getNickname() + " -> " + targetClient.getNickname() + ": " + message);
        logAsync("PRIVATE " + sender.getNickname() + " -> " + targetClient.getNickname() + ": " + message);
    }

    public static String getUsersList() {
        synchronized (clients) {
            if (clients.isEmpty()) {
                return "[Сервер] Сейчас в чате никого нет";
            }

            String nicknames = clients.stream()
                    .map(ClientHandler::getNickname)
                    .sorted(String.CASE_INSENSITIVE_ORDER)
                    .collect(Collectors.joining(", "));
            return "[Сервер] Пользователи онлайн: " + nicknames;
        }
    }

    public static void removeClient(ClientHandler client) {
        boolean removed;
        synchronized (clients) {
            removed = clients.remove(client);
        }

        if (!removed || client.getNickname() == null) {
            return;
        }

        String message = "Пользователь " + client.getNickname() + " отключился";
        System.out.println(message + ". Активных клиентов: " + getClientCount());
        logAsync("DISCONNECT " + client.getNickname());
        broadcastSystem(message, client);
    }

    public static void logAsync(String message) {
        logPool.execute(() -> {
            String line = LOG_TIME_FORMAT.format(LocalDateTime.now()) + " | " + message + System.lineSeparator();
            try {
                Files.writeString(
                        LOG_FILE,
                        line,
                        StandardCharsets.UTF_8,
                        StandardOpenOption.CREATE,
                        StandardOpenOption.APPEND
                );
            } catch (IOException e) {
                System.out.println("Не удалось записать лог: " + e.getMessage());
            }
        });
    }

    static long getPingIntervalMs() {
        return PING_INTERVAL_MS;
    }

    static long getPingTimeoutMs() {
        return PING_TIMEOUT_MS;
    }

    private static Optional<ClientHandler> findByNickname(String nickname) {
        synchronized (clients) {
            return clients.stream()
                    .filter(client -> client.getNickname().equalsIgnoreCase(nickname))
                    .findFirst();
        }
    }

    private static boolean isNicknameTaken(String nickname) {
        return clients.stream().anyMatch(client -> client.getNickname().equalsIgnoreCase(nickname));
    }

    private static void broadcastSystem(String message, ClientHandler excludedClient) {
        synchronized (clients) {
            for (ClientHandler client : clients) {
                if (client != excludedClient) {
                    client.sendMessage("[Сервер] " + message);
                }
            }
        }
    }

    private static int getClientCount() {
        synchronized (clients) {
            return clients.size();
        }
    }

    private static void startPingMonitor() {
        pingPool.scheduleWithFixedDelay(() -> {
            List<ClientHandler> snapshot;
            synchronized (clients) {
                snapshot = clients.stream()
                        .sorted(Comparator.comparing(ClientHandler::getNickname, String.CASE_INSENSITIVE_ORDER))
                        .toList();
            }

            long now = System.currentTimeMillis();
            for (ClientHandler client : snapshot) {
                if (client.isPingTimedOut(now)) {
                    System.out.println("Пользователь " + client.getNickname() + " не ответил на ping");
                    client.disconnect("[Сервер] Соединение закрыто из-за отсутствия ответа на ping");
                } else if (client.shouldSendPing(now)) {
                    client.sendPing(now);
                }
            }
        }, 5, 5, TimeUnit.SECONDS);
    }

    private static int readPort(String[] args) {
        if (args.length == 0) {
            return DEFAULT_PORT;
        }

        try {
            return Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("Некорректный порт, используется порт по умолчанию " + DEFAULT_PORT);
            return DEFAULT_PORT;
        }
    }

    private static void shutdown() {
        synchronized (clients) {
            for (ClientHandler client : clients) {
                client.disconnect("[Сервер] Сервер завершает работу");
            }
            clients.clear();
        }

        pingPool.shutdownNow();
        clientPool.shutdownNow();
        logPool.shutdown();
        try {
            if (!logPool.awaitTermination(3, TimeUnit.SECONDS)) {
                logPool.shutdownNow();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logPool.shutdownNow();
        }
    }
}

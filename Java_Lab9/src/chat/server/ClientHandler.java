package chat.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicBoolean;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final String clientAddress;
    private final int clientPort;
    private final AtomicBoolean closed;

    private BufferedReader in;
    private PrintWriter out;
    private String nickname;
    private volatile long lastPongAt;
    private volatile long lastPingSentAt;
    private volatile boolean waitingForPong;

    public ClientHandler(Socket socket) {
        this.socket = socket;
        this.clientAddress = socket.getInetAddress().getHostAddress();
        this.clientPort = socket.getPort();
        this.closed = new AtomicBoolean(false);
        this.lastPongAt = System.currentTimeMillis();
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);

            if (!readAndRegisterNickname()) {
                return;
            }

            String message;
            while ((message = in.readLine()) != null) {
                if (handleServiceMessage(message)) {
                    continue;
                }

                if ("/exit".equalsIgnoreCase(message.trim())) {
                    sendMessage("[Сервер] До свидания!");
                    break;
                }

                if (message.isBlank()) {
                    continue;
                }

                handleUserMessage(message);
            }
        } catch (SocketException e) {
            if (!closed.get()) {
                System.out.println("Соединение с " + getClientInfo() + " разорвано: " + e.getMessage());
            }
        } catch (IOException e) {
            System.out.println("Ошибка обмена с " + getClientInfo() + ": " + e.getMessage());
        } finally {
            close();
            ChatServer.removeClient(this);
        }
    }

    public String getNickname() {
        return nickname;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public int getClientPort() {
        return clientPort;
    }

    public synchronized void sendMessage(String message) {
        if (out != null && !closed.get()) {
            out.println(message);
        }
    }

    public boolean shouldSendPing(long now) {
        return !closed.get() && !waitingForPong && now - lastPongAt >= ChatServer.getPingIntervalMs();
    }

    public boolean isPingTimedOut(long now) {
        return !closed.get() && waitingForPong && now - lastPingSentAt >= ChatServer.getPingTimeoutMs();
    }

    public void sendPing(long now) {
        waitingForPong = true;
        lastPingSentAt = now;
        sendMessage("/ping");
    }

    public void disconnect(String reason) {
        sendMessage(reason);
        close();
    }

    private boolean readAndRegisterNickname() throws IOException {
        sendMessage("[Сервер] Введите никнейм:");
        String requestedNickname = in.readLine();

        if (requestedNickname == null || requestedNickname.isBlank()) {
            sendMessage("[Сервер] Никнейм не может быть пустым");
            return false;
        }

        nickname = requestedNickname.trim();
        if (!nickname.matches("[\\p{L}\\p{N}_-]{2,20}")) {
            sendMessage("[Сервер] Никнейм должен содержать 2-20 букв, цифр, символов '_' или '-'");
            return false;
        }

        if (!ChatServer.registerClient(this)) {
            sendMessage("[Сервер] Никнейм уже занят");
            return false;
        }

        sendMessage("[Сервер] Добро пожаловать, " + nickname + "!");
        sendMessage("[Сервер] Команды: /users, /msg ник текст, /exit");
        return true;
    }

    private boolean handleServiceMessage(String message) {
        if ("/pong".equalsIgnoreCase(message.trim())) {
            waitingForPong = false;
            lastPongAt = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    private void handleUserMessage(String message) {
        String trimmedMessage = message.trim();

        if ("/users".equalsIgnoreCase(trimmedMessage)) {
            sendMessage(ChatServer.getUsersList());
            ChatServer.logAsync("COMMAND /users from " + nickname);
            return;
        }

        if (trimmedMessage.startsWith("/msg ")) {
            handlePrivateMessage(trimmedMessage);
            return;
        }

        ChatServer.broadcastMessage(message, this);
    }

    private void handlePrivateMessage(String message) {
        String[] parts = message.split("\\s+", 3);
        if (parts.length < 3 || parts[2].isBlank()) {
            sendMessage("[Сервер] Формат приватного сообщения: /msg ник текст");
            return;
        }

        ChatServer.sendPrivateMessage(parts[1], parts[2], this);
    }

    private String getClientInfo() {
        if (nickname == null) {
            return clientAddress + ":" + clientPort;
        }
        return nickname + " (" + clientAddress + ":" + clientPort + ")";
    }

    private void close() {
        if (!closed.compareAndSet(false, true)) {
            return;
        }

        try {
            if (in != null) {
                in.close();
            }
        } catch (IOException ignored) {
        }

        if (out != null) {
            out.close();
        }

        try {
            socket.close();
        } catch (IOException ignored) {
        }
    }

}

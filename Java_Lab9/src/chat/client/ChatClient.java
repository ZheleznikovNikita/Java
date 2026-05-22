package chat.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicBoolean;

public class ChatClient {
    private static final String DEFAULT_HOST = "localhost";
    private static final int DEFAULT_PORT = 8080;

    public static void main(String[] args) {
        String host = args.length > 0 ? args[0] : DEFAULT_HOST;
        int port = readPort(args);
        AtomicBoolean exitRequested = new AtomicBoolean(false);

        try (Socket socket = new Socket(host, port);
             BufferedReader serverIn = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
             PrintWriter serverOut = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
             BufferedReader console = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8))) {

            System.out.println("Подключено к " + host + ":" + port);
            String nicknamePrompt = serverIn.readLine();
            if (nicknamePrompt == null) {
                System.out.println("Сервер закрыл соединение до авторизации");
                return;
            }

            System.out.print(nicknamePrompt + " ");
            String nickname = console.readLine();
            if (nickname == null || nickname.isBlank()) {
                System.out.println("Никнейм не может быть пустым");
                return;
            }
            serverOut.println(nickname.trim());

            Thread readerThread = new Thread(() -> readServerMessages(socket, serverIn, serverOut, exitRequested), "server-reader");
            readerThread.start();

            String userInput;
            while ((userInput = console.readLine()) != null) {
                serverOut.println(userInput);
                if ("/exit".equalsIgnoreCase(userInput.trim())) {
                    exitRequested.set(true);
                    socket.close();
                    break;
                }
            }

            readerThread.join(1_000);
        } catch (UnknownHostException e) {
            System.out.println("Неизвестный хост: " + host);
        } catch (SocketException e) {
            if (!exitRequested.get()) {
                System.out.println("Соединение с сервером потеряно: " + e.getMessage());
            }
        } catch (IOException e) {
            System.out.println("Ошибка клиента: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Работа клиента прервана");
        }
    }

    private static void readServerMessages(
            Socket socket,
            BufferedReader serverIn,
            PrintWriter serverOut,
            AtomicBoolean exitRequested
    ) {
        try {
            String serverMessage;
            while ((serverMessage = serverIn.readLine()) != null) {
                if ("/ping".equalsIgnoreCase(serverMessage.trim())) {
                    serverOut.println("/pong");
                    continue;
                }
                System.out.println(serverMessage);
            }

            if (!exitRequested.get()) {
                System.out.println("Сервер закрыл соединение");
                closeSocket(socket);
                System.exit(0);
            }
        } catch (SocketException e) {
            if (!exitRequested.get()) {
                System.out.println("Соединение с сервером разорвано: " + e.getMessage());
                System.exit(0);
            }
        } catch (IOException e) {
            if (!exitRequested.get()) {
                System.out.println("Ошибка чтения от сервера: " + e.getMessage());
                System.exit(0);
            }
        }
    }

    private static int readPort(String[] args) {
        if (args.length <= 1) {
            return DEFAULT_PORT;
        }

        try {
            return Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("Некорректный порт, используется порт по умолчанию " + DEFAULT_PORT);
            return DEFAULT_PORT;
        }
    }

    private static void closeSocket(Socket socket) {
        try {
            socket.close();
        } catch (IOException ignored) {
        }
    }
}

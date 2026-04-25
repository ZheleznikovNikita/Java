package phonebook.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ActionLogger {
    private static final String LOG_FILE = "log.txt";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static void log(String action) {
        String entry = String.format("[%s] %s%n", LocalDateTime.now().format(FORMATTER), action);
        try (FileWriter fw = new FileWriter(LOG_FILE, true)){
            fw.write(entry);
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void showHistory() {
        File file = new File(LOG_FILE);
        if (!file.exists()) {
            System.out.println("Лог-файл не найден");
            return;
        }
        try {
            List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
            int counter = 0;
            for (var elem : lines) {
                System.out.printf("%d: %s\n", counter + 1, lines.get(counter));
                ++counter;
                if (counter == 19)
                    break;
            }
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}

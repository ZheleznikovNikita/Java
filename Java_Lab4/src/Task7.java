import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Task7 {
    // Исполняющая функция
    public static void main(String[] args) {
        AnalyzeLogs();
    }
    /// Анализатор логов
    private static void AnalyzeLogs() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите путь к папке с логами: ");
        String input = scanner.nextLine();
        System.out.println("Анализирую...");
        try {
            Path rootPath = Paths.get(input);
            List<Path> logFiles;
            try (Stream<Path> walk = Files.walk(rootPath)) {
                logFiles = walk
                        .filter(Files::isRegularFile)
                        .filter(p -> p.toString().toLowerCase().endsWith(".log"))
                        .toList();
            }
            if (logFiles.isEmpty()) {
                System.out.println("В указанной папке нет файлов с расширением .log.");
                return;
            }
            Map<String, Long> dailyStats = new TreeMap<>();
            for (Path logFile : logFiles) {
                try (Stream<String> lines = Files.lines(logFile, StandardCharsets.UTF_8)) {
                    lines.forEach(line -> {
                        if (line.length() >= 10) {
                            String datePart = line.substring(0, 10);
                            if (datePart.matches("\\d{4}-\\d{2}-\\d{2}")) {
                                dailyStats.merge(datePart, 1L, Long::sum);
                            }
                        }
                    });
                } catch (IOException e) {
                    System.err.println("Ошибка при чтении файла " + logFile.getFileName() + ": " + e.getMessage());
                }
            }
            if (dailyStats.isEmpty()) {
                System.out.println("Валидных записей в логах не обнаружено.");
                return;
            }
            PrintFinalReport(rootPath, logFiles.size(), dailyStats);
        } catch (InvalidPathException e) {
            System.err.println("Ошибка: некорректный путь: " + input);
        } catch (NoSuchFileException e) {
            System.err.println("Ошибка: файл или папка не найдены: " + e.getFile());
        } catch (AccessDeniedException e) {
            System.err.println("Ошибка: нет прав доступа: " + e.getFile());
        } catch (IOException e) {
            System.err.println("Ошибка ввода/вывода: " + e.getMessage());
        }
    }
    /// Вывод статистики
    private static void PrintFinalReport(Path root, int fileCount, Map<String, Long> dailyStats) {
        long totalRecords = dailyStats.values().stream().mapToLong(Long::longValue).sum();
        Map.Entry<String, Long> maxEntry = Collections.max(dailyStats.entrySet(), Map.Entry.comparingByValue());
        Map.Entry<String, Long> minEntry = Collections.min(dailyStats.entrySet(), Map.Entry.comparingByValue());
        Map<String, Long> monthlyStats = dailyStats.entrySet().stream()
                .collect(Collectors.groupingBy(
                        e -> e.getKey().substring(0, 7),
                        TreeMap::new,
                        Collectors.summingLong(Map.Entry::getValue)
                ));
        System.out.println("\nАнализ логов в папке: " + root.toAbsolutePath());
        System.out.println("Найдено .log файлов: " + fileCount);
        System.out.println("========================================");
        System.out.println("Статистика по дням (все дни, сортировка по дате):");
        dailyStats.forEach((date, count) -> System.out.printf("%s: %d записей%n", date, count));
        System.out.println("\nМаксимальная активность: " + maxEntry.getKey() + " (" + maxEntry.getValue() + " записей)");
        System.out.println("Минимальная активность: " + minEntry.getKey() + " (" + minEntry.getValue() + " записей)");
        System.out.println("\nСтатистика по месяцам:");
        monthlyStats.forEach((month, count) -> System.out.printf("%s: %d записей%n", month, count));
        System.out.println("========================================");
        System.out.println("Всего записей обработано: " + totalRecords);
    }
}

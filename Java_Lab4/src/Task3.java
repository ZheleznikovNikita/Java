import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Task3 {
    /// Поле для формата даты
    private static final DateTimeFormatter _dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    // Исполняющая функция
    public static void main(String[] args) {
        DiskAnalyzer();
    }
    /// Класс для хранения данных о файле
    static class FileData {
        Path path;
        long size;
        FileTime lastModified;
        String extension;
        // Конструктор для объекта класса
        FileData(Path path, BasicFileAttributes attrs) {
            this.path = path.toAbsolutePath();
            this.size = attrs.size();
            this.lastModified = attrs.lastModifiedTime();
            String name = path.getFileName().toString();
            int dotIndex = name.lastIndexOf('.');
            this.extension = (dotIndex == -1 || dotIndex == name.length() - 1)
                    ? "без расширения"
                    : name.substring(dotIndex).toLowerCase();
        }
    }
    /// Дисковый анализатор
    private static void DiskAnalyzer() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите путь к папке для анализа: ");
        String input = scanner.nextLine();
        System.out.println("Анализирую...");
        try {
            Path startPath = Paths.get(input);
            List<FileData> allFiles = new ArrayList<>();
            final int[] dirCount = {0};
            Files.walkFileTree(startPath, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    dirCount[0]++;
                    return FileVisitResult.CONTINUE;
                }
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    allFiles.add(new FileData(file, attrs));
                    return FileVisitResult.CONTINUE;
                }
            });
            printReport(startPath, allFiles, dirCount[0]);

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
    /// Вывод информации о папке
    private static void printReport(Path root, List<FileData> files, int folders) {
        long totalSize = files.stream().mapToLong(f -> f.size).sum();
        System.out.println("========================================");
        System.out.println("ОТЧЁТ ПО ПАПКЕ: " + root.toAbsolutePath());
        System.out.println("========================================");
        System.out.println("Всего файлов: " + files.size());
        System.out.println("Всего папок: " + folders + " (включая корневую)");
        System.out.println("Общий размер: " + formatSize(totalSize));
        System.out.println("\nТОП-5 САМЫХ БОЛЬШИХ ФАЙЛОВ:");
        files.stream()
                .sorted(Comparator.comparingLong((FileData f) -> f.size).reversed())
                .limit(5)
                .forEach(f -> System.out.printf("%s (%s)%n", f.path, formatSize(f.size)));
        System.out.println("\nТОП-5 САМЫХ СТАРЫХ ФАЙЛОВ (по дате изменения):");
        files.stream()
                .sorted(Comparator.comparing(f -> f.lastModified))
                .limit(5)
                .forEach(f -> System.out.printf("%s (%s)%n", f.path, f.lastModified.toInstant().atZone(ZoneId.systemDefault()).format(_dateFormatter)));
        System.out.println("\nСТАТИСТИКА ПО РАСШИРЕНИЯМ (по убыванию количества):");
        Map<String, Long> stats = files.stream()
                .collect(Collectors.groupingBy(f -> f.extension, Collectors.counting()));
        stats.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .forEach(e -> System.out.printf("%s: %d файл(ов)%n", e.getKey(), e.getValue()));
        System.out.println("========================================");
    }
    /// Форматный вывод для размера
    private static String formatSize(long bytes) {
        if (bytes < 1024) return bytes + " байт";
        if (bytes < 1048576) return String.format("%.2f КБ", bytes / 1024.0);
        if (bytes < 1073741824) return String.format("%.2f МБ", bytes / (1024.0 * 1024.0));
        return String.format("%.2f ГБ", bytes / (1024.0 * 1024.0 * 1024.0));
    }
}

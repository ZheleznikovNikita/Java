import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.FileTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.List;
import java.util.stream.Stream;

public class Task2 {
    /// Поле для формата даты
    private static final DateTimeFormatter _dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    // Исполняющая функция
    public static void main(String[] args) {
        SearchFiles();
    }
    /// Поиск файлов по маске
    private static void SearchFiles(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите путь к папке: ");
        String folderInput = scanner.nextLine();
        System.out.print("Введите маску: ");
        String maskInput = scanner.nextLine();
        try {
            Path startPath = Paths.get(folderInput);
            if (!Files.exists(startPath))
                throw new NoSuchFileException(folderInput);
            PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:" + maskInput);
            System.out.println("\nПоиск по маске \"" + maskInput + "\" в папке " + startPath.toAbsolutePath() + "...");
            try (Stream<Path> stream = Files.walk(startPath)) {
                List<Path> foundFiles = stream.filter(Files::isRegularFile)
                        .filter(path -> matcher.matches(path.getFileName()))
                        .toList();
                if (foundFiles.isEmpty())
                    System.out.println("По маске \"" + maskInput + "\" файлов не найдено.");
                else {
                    System.out.println("Найдено файлов: " + foundFiles.size());
                    for (Path file : foundFiles)
                        DisplayInfo(file);
                }
            }
        }
        catch (InvalidPathException e) {
            System.err.println("Ошибка: некорректный путь: " + folderInput);
        }
        catch (NoSuchFileException e) {
            System.err.println("Ошибка: файл или папка не найдены: " + e.getFile());
        }
        catch (AccessDeniedException e) {
            System.err.println("Ошибка: нет прав доступа: " + e.getFile());
        }
        catch (IOException e) {
            System.err.println("Ошибка ввода/вывода: " + e.getMessage());
        }
    }
    /// Вывод информации о файле
    private static void DisplayInfo(Path path) throws IOException {
        long size = Files.size(path);
        FileTime lastModified = Files.getLastModifiedTime(path);
        String formattedDate = lastModified.toInstant()
                .atZone(ZoneId.systemDefault())
                .format(_dateFormatter);
        System.out.println(path.toAbsolutePath());
        System.out.println("Размер: " + size + " байт");
        System.out.println("Изменён: " + formattedDate);
    }
}

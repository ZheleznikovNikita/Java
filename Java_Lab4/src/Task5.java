import java.io.IOException;
import java.nio.file.*;
import java.util.Scanner;
import java.util.stream.Stream;

public class Task5 {
    // Исполняющая функция
    public static void main(String[] args) {
        OrganizeFolder();
    }
    /// Организатор папки
    private static void OrganizeFolder() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите путь к папке: ");
        String input = scanner.nextLine();
        try {
            Path rootPath = Paths.get(input);
            if (!Files.isDirectory(rootPath)) {
                System.err.println("Ошибка: указанный путь не является папкой.");
                return;
            }
            System.out.println("Организация папки: " + rootPath.toAbsolutePath());
            int movedCount = 0;
            try (Stream<Path> stream = Files.list(rootPath)) {
                Iterable<Path> files = stream.filter(Files::isRegularFile)::iterator;
                for (Path file : files) {
                    String extension = GetExtension(file);
                    Path targetDir = rootPath.resolve(extension);
                    if (!Files.exists(targetDir))
                        Files.createDirectories(targetDir);
                    Path targetPath = targetDir.resolve(file.getFileName());
                    Files.move(file, targetPath, StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("Перемещён: " + file.getFileName() + " → " + extension + "/");
                    movedCount++;
                }
            }
            if (movedCount == 0)
                System.out.println("В папке нет файлов для организации.");
            else
                System.out.println("Готово. Перемещено файлов: " + movedCount);
        }
        catch (InvalidPathException e) {
            System.err.println("Ошибка: некорректный путь: " + input);
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
    /// Определение расширения
    private static String GetExtension(Path file) {
        String fileName = file.getFileName().toString();
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex <= 0)
            return "other";
        if (lastDotIndex == fileName.length() - 1)
            return "other";
        return fileName.substring(lastDotIndex + 1).toLowerCase();
    }
}

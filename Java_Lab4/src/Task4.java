import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Task4 {
    // Исполняющая функция
    public static void main(String[] args) {
        FindDuplicates();
    }
    /// Поиск дубликатов
    private static void FindDuplicates() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите путь к папке: ");
        String input = scanner.nextLine();
        System.out.println("Ищу дубликаты...");
        try {
            Path startPath = Paths.get(input);
            Map<Long, List<Path>> sizeGroups;
            try (Stream<Path> walk = Files.walk(startPath)) {
                sizeGroups = walk
                        .filter(Files::isRegularFile)
                        .collect(Collectors.groupingBy(path -> {
                            try {
                                return Files.size(path);
                            } catch (IOException e) {
                                return -1L;
                            }}));
            }
            Map<String, List<Path>> hashGroups = new LinkedHashMap<>();
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            for (Map.Entry<Long, List<Path>> entry : sizeGroups.entrySet()) {
                if (entry.getValue().size() > 1 && entry.getKey() != -1L) {
                    Map<String, List<Path>> subGroups = entry.getValue().stream()
                        .collect(Collectors.groupingBy(path -> GetFileHash(path, digest)));
                        subGroups.forEach((hash, paths) -> {
                            if (paths.size() > 1)
                                hashGroups.put(hash, paths);});
                    }
            }
            printResults(hashGroups);
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
        catch (IOException | NoSuchAlgorithmException e) {
            System.err.println("Ошибка ввода/вывода: " + e.getMessage());
        }
    }
    /// Вычисление хеша
    private static String GetFileHash(Path path, MessageDigest digest) {
        try (InputStream is = Files.newInputStream(path)) {
            byte[] buffer = new byte[8192];
            int read;
            digest.reset();
            while ((read = is.read(buffer)) != -1)
                digest.update(buffer, 0, read);
            byte[] hashBytes = digest.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes)
                sb.append(String.format("%02x", b));
            return sb.toString();
        }
        catch (IOException e) {
            return "error-" + path.toString();
        }
    }
    /// Вывод статистики
    private static void printResults(Map<String, List<Path>> groups) throws IOException {
        if (groups.isEmpty()) {
            System.out.println("Дубликатов не найдено.");
            return;
        }
        System.out.println("\nАнализ завершён. Найдено групп дубликатов: " + groups.size());
        int groupNum = 1;
        for (Map.Entry<String, List<Path>> entry : groups.entrySet()) {
            Path firstFile = entry.getValue().getFirst();
            long size = Files.size(firstFile);
            String shortHash = entry.getKey().substring(0, Math.min(entry.getKey().length(), 16));
            System.out.println("--- Группа " + groupNum++ + " ---");
            System.out.println("Размер: " + size + " байт Хеш: " + shortHash + "...");
            for (Path path : entry.getValue())
                System.out.println(path.toAbsolutePath());
        }
    }
}

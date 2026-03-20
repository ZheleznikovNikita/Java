import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Task6 {
    // Исполняющая функция
    public static void main(String[] args) {
        CompareDirectories();
    }
    /// Сравнение папок
    private static void CompareDirectories() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите путь к первой папке: ");
        String pathAInput = scanner.nextLine();
        System.out.print("Введите путь ко второй папке: ");
        String pathBInput = scanner.nextLine();
        System.out.println("Сравниваю...");
        try {
            Path rootA = Paths.get(pathAInput).toAbsolutePath();
            Path rootB = Paths.get(pathBInput).toAbsolutePath();
            Map<Path, Long> filesA = scanDirectory(rootA);
            Map<Path, Long> filesB = scanDirectory(rootB);
            List<Path> onlyInA = new ArrayList<>();
            List<Path> onlyInB = new ArrayList<>();
            List<Path> differentContent = new ArrayList<>();
            List<Path> identical = new ArrayList<>();
            for (Path relPath : filesA.keySet()) {
                if (!filesB.containsKey(relPath))
                    onlyInA.add(relPath);
                else {
                    long sizeA = filesA.get(relPath);
                    long sizeB = filesB.get(relPath);
                    if (sizeA != sizeB)
                        differentContent.add(relPath);
                    else if (IsContentEqual(rootA.resolve(relPath), rootB.resolve(relPath)))
                        identical.add(relPath);
                    else
                        differentContent.add(relPath);
                }
            }
            for (Path relPath : filesB.keySet())
                if (!filesA.containsKey(relPath))
                    onlyInB.add(relPath);
            PrintReport(rootA, rootB, onlyInA, onlyInB, differentContent, identical, filesA, filesB);
        }
        catch (InvalidPathException e) {
            System.err.println("Ошибка: некорректный путь: " + e.getInput());
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
    ///  Сканирование папки
    private static Map<Path, Long> scanDirectory(Path root) throws IOException {
        try (Stream<Path> walk = Files.walk(root)) {
            return walk.filter(Files::isRegularFile)
                    .collect(Collectors.toMap(
                            root::relativize,
                            path -> {
                                try {
                                    return Files.size(path);
                                } catch (IOException e) {
                                    return -1L;
                                }
                            }
                    ));
        }
    }
    /// Сравнение файлов
    private static boolean IsContentEqual(Path p1, Path p2) throws IOException, NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        return Arrays.equals(GetFileHash(p1, md), GetFileHash(p2, md));
    }
    /// Вычисление хеша файла
    private static byte[] GetFileHash(Path path, MessageDigest digest) throws IOException {
        try (InputStream is = Files.newInputStream(path)) {
            byte[] buffer = new byte[8192];
            int read;
            digest.reset();
            while ((read = is.read(buffer)) != -1)
                digest.update(buffer, 0, read);
            return digest.digest();
        }
    }
    /// Вывод статистики
    private static void PrintReport(Path a, Path b, List<Path> onlyA, List<Path> onlyB,
                                    List<Path> diff, List<Path> iden,
                                    Map<Path, Long> sizesA, Map<Path, Long> sizesB) {
        System.out.println("\nСравнение папок:");
        System.out.println("A: " + a);
        System.out.println("B: " + b);
        System.out.println("----------------------------------------");
        System.out.println("Только в A (" + onlyA.size() + " файл(ов)):");
        onlyA.forEach(System.out::println);
        System.out.println("\nТолько в B (" + onlyB.size() + " файл(ов)):");
        onlyB.forEach(System.out::println);
        System.out.println("\nОтличаются по содержимому (" + diff.size() + " файл(ов)):");
        for (Path p : diff)
            System.out.printf("%s (A: %d байт, B: %d байт)%n", p, sizesA.get(p), sizesB.getOrDefault(p, 0L));
        System.out.println("\nИдентичны (" + iden.size() + " файл(ов)):");
        iden.forEach(System.out::println);
    }
}

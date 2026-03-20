import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.stream.Stream;

public class Task1 {
    /// Поле для формата даты
    private static final DateTimeFormatter _dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    // Исполняющая функция
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            DisplayCommands();
            var input = scanner.nextInt();
            switch (input){
                case 1 -> DisplayFolderContent();
                case 2 -> CreateFolder();
                case 3 -> DeleteFileOrFolder();
                case 4 -> CopyFile();
                case 5 -> MoveOrRename();
                case 6 -> {
                    System.out.println("Завершение программы");
                    return;
                }
                default -> System.out.println("Введена неправильная команда, попробуйте ещё раз: ");
            }
        }
    }
    /// Вывод всех команд
    private static void DisplayCommands() {
        System.out.println("=== Файловый менеджер ===");
        System.out.println("Текущая папка: " + Paths.get("").toAbsolutePath());
        System.out.println("1 Показать содержимое папки");
        System.out.println("2 Создать папку");
        System.out.println("3 Удалить файл или папку");
        System.out.println("4 Копировать файл");
        System.out.println("5 Переместить / переименовать");
        System.out.println("6 Выход");
        System.out.println("Выберите действие: ");
    }
    /// Показ содержимого папки
    private static void DisplayFolderContent() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите путь к папке: ");
        String input = scanner.nextLine();
        try {
            Path path = Paths.get(input);
            try (Stream<Path> stream = Files.list(path)){
                Object[] items = stream.toArray();
                if (items.length == 0)
                    System.out.println("Папка пуста.");
                else {
                    for (var obj : items){
                        var item = (Path)obj;
                        BasicFileAttributes attrs = Files.readAttributes(item, BasicFileAttributes.class);
                        String date = attrs.lastModifiedTime().toInstant().atZone(ZoneId.systemDefault()).format(_dateFormatter);
                        if (Files.isDirectory(item))
                            System.out.printf("[П] %s <%s>%n", item.getFileName(), date);
                        else
                            System.out.printf("[Ф] %s <%d> байт <%s>%n", item.getFileName(), attrs.size(), date);
                    }
                }
            }
        }
        catch (NoSuchFileException e){
            System.err.println("Ошибка: файл или папка не найдены: " + input);
        }
        catch (AccessDeniedException e){
            System.err.println("Ошибка: нет прав доступа: " + input);
        }
        catch (InvalidPathException e){
            System.err.println("Ошибка: некорректный путь:" + input);
        }
        catch (IOException e) {
            System.err.println("Ошибка ввода/вывода: " + e.getMessage());
        }
    }
    /// Создание папки
    private static void CreateFolder() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите путь для новой папки: ");
        String input = scanner.nextLine();
        try {
            Path path = Paths.get(input);
            if (Files.exists(path))
                System.out.println("Папка уже существует: " + path);
            else {
                Files.createDirectories(path);
                System.out.println("Папка создана: " + path.toAbsolutePath());
            }
        }
        catch (AccessDeniedException e){
            System.err.println("Ошибка: нет прав доступа: " + input);
        }
        catch (InvalidPathException e){
            System.err.println("Ошибка: некорректный путь:" + input);
        }
        catch (IOException e) {
            System.err.println("Ошибка ввода/вывода: " + e.getMessage());
        }
    }
    /// Удаление файла или папки
    private static void DeleteFileOrFolder() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите путь для удаления: ");
        String input = scanner.nextLine();
        try {
            Path path = Paths.get(input);
            if (Files.isDirectory(path)) {
                Files.walkFileTree(path, new SimpleFileVisitor<>() {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        Files.delete(file);
                        return FileVisitResult.CONTINUE;
                    }
                    @Override
                    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                        Files.delete(dir);
                        return FileVisitResult.CONTINUE;
                    }
                });
            } else {
                Files.delete(path);
            }
            System.out.println("Удалено: " + path);
        } catch (InvalidPathException e) {
            System.err.println("Ошибка: некорректный путь: " + input);
        } catch (NoSuchFileException e) {
            System.err.println("Ошибка: файл или папка не найдены: " + input);
        } catch (AccessDeniedException e) {
            System.err.println("Ошибка: нет прав доступа: " + input);
        } catch (IOException e) {
            System.err.println("Ошибка ввода/вывода: " + e.getMessage());
        }
    }
    /// Копирование файла
    private static void CopyFile() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите путь к источнику: ");
        String srcInput = scanner.nextLine();
        System.out.println("Введите путь к цели: ");
        String targetInput = scanner.nextLine();
        try {
            Path source = Paths.get(srcInput);
            Path target = Paths.get(targetInput);
            Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Скопировано: " + source + " → " + target);
        }
        catch (InvalidPathException e) {
            System.err.println("Ошибка: некорректный путь: " + e.getInput());
        } catch (NoSuchFileException e) {
            System.err.println("Ошибка: файл или папка не найдены: " + e.getFile());
        } catch (AccessDeniedException e) {
            System.err.println("Ошибка: нет прав доступа: " + e.getFile());
        } catch (IOException e) {
            System.err.println("Ошибка ввода/вывода: " + e.getMessage());
        }
    }
    /// Перемещение/переименование
    private static void MoveOrRename() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите путь к источнику: ");
        String srcInput = scanner.nextLine();
        System.out.print("Введите путь к цели: ");
        String targetInput = scanner.nextLine();
        try {
            Path source = Paths.get(srcInput);
            Path target = Paths.get(targetInput);
            Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Перемещено: " + source + " → " + target);
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
        catch (IOException e) {
            System.err.println("Ошибка ввода/вывода: " + e.getMessage());
        }
    }
}

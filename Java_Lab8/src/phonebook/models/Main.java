package phonebook.models;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final PhoneBook phoneBook = new PhoneBook();

    static void main(String[] args) {
        System.out.println("Телефонная книга");
        boolean running = true;
        // непонятно почему в требованиях нумерация с 4
        while (running) {
            System.out.println("\nМеню:");
            System.out.println("1. Добавить контакт");
            System.out.println("2. Удалить контакт");
            System.out.println("3. Найти по имени");
            System.out.println("4. Найти по номеру");
            System.out.println("5. Найти по категории");
            System.out.println("6. Показать все контакты");
            System.out.println("7. Показать все, сгруппированные по категориям");
            System.out.println("8. Сохранить в файл");
            System.out.println("9. Загрузить из файла");
            System.out.println("0. Выход");
            System.out.print("Выберите действие: ");

            int choice = readInt();
            switch (choice) {
                case 1 -> addContact();
                case 2 -> removeContact();
                case 3 -> findByName();
                case 4 -> findByPhone();
                case 5 -> findByCategory();
                case 6 -> showAllContacts();
                case 7 -> showGroupedByCategory();
                case 8 -> saveToFile();
                case 9 -> loadFromFile();
                case 0 -> { running = false; System.out.println("До свидания!"); }
                default -> System.out.println("Неверный выбор. Введите число от 0 до 9.");
            }
        }
        scanner.close();
    }

    // Вспомогательные методы для безопасного ввода
    private static int readInt() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Введите корректное число: ");
            }
        }
    }

    private static String readNonEmptyString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty())
                return input;
            System.out.println("Поле не может быть пустым. Попробуйте снова");
        }
    }

    private static void addContact() {
        try {
            String name = readNonEmptyString("Введите имя: ");
            String phone = readNonEmptyString("Введите номер телефона: ");
            System.out.print("Введите email (необязательно): ");
            String email = scanner.nextLine().trim();
            System.out.print("Введите категорию (необязательно): ");
            String category = scanner.nextLine();

            Contact c = new Contact(name, phone, email, category);
            if (phoneBook.addContact(c)) {
                System.out.println("Контакт успешно добавлен");
            }
        }
        catch (IllegalArgumentException e) {
            System.out.println("Ошибка валидации: " + e.getMessage());
        }
    }

    private static void removeContact() {
        String phone = readNonEmptyString("Введите номер телефона для удаления: ");
        if (phoneBook.removeContact(phone)) {
            System.out.println("Контакт удален");
        } else {
            System.out.println("Контакт с таким номером не найден");
        }
    }

    private static void findByName() {
        String name = readNonEmptyString("Введите имя для поиска: ");
        ArrayList<Contact> found = phoneBook.findByName(name);
        if (found.isEmpty()) {
            System.out.println("Контакты не найдены");
        } else {
            System.out.println("Найдено контактов: " + found.size());
            found.forEach(System.out::println);
        }
    }

    private static void findByPhone() {
        String phone = readNonEmptyString("Введите номер телефона для поиска: ");
        Contact found = phoneBook.findByPhone(phone);
        if (found != null) {
            System.out.println("Найден: " + found);
        } else {
            System.out.println("Контакт не найден");
        }
    }

    private static void findByCategory() {
        System.out.print("Введите категорию: ");
        String category = scanner.nextLine();
        var found = phoneBook.findByCategory(category);
        if (found.isEmpty())
            System.out.println("Контакты в категории «" + category + "» не найдены.");
        else {
            System.out.println("Найдено в категории «" + category + "»: " + found.size());
            found.forEach(System.out::println);
        }
    }

    private static void showAllContacts() {
        ArrayList<Contact> all = phoneBook.getAllContacts();
        if (all.isEmpty()) {
            System.out.println("Телефонная книга пуста");
        } else {
            System.out.println("Все контакты:");
            all.forEach(System.out::println);
        }
    }

    private static void showGroupedByCategory() {
        Map<String, List<Contact>> grouped = phoneBook.getContactsGroupedByCategory();
        if (grouped.isEmpty()) {
            System.out.println("Телефонная книга пуста.");
            return;
        }

        grouped.keySet().stream().sorted().forEach(cat -> {
            System.out.println("\nКатегория: " + cat + " (" + grouped.get(cat).size() + ")");
            grouped.get(cat).forEach(System.out::println);
        });
    }

    private static void saveToFile() {
        System.out.print("Введите имя файла для сохранения: ");
        String filename = scanner.nextLine().trim();
        if (filename.isEmpty()) {
            System.out.println("Имя файла не может быть пустым");
            return;
        }
        try {
            phoneBook.saveToFile(filename);
            System.out.println("Контакты сохранены в файл: " + filename);
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении файла: " + e.getMessage());
        }
    }

    private static void loadFromFile() {
        System.out.print("Введите имя файла для загрузки: ");
        String filename = scanner.nextLine().trim();
        if (filename.isEmpty()) {
            System.out.println("Имя файла не может быть пустым");
            return;
        }
        try {
            phoneBook.loadFromFile(filename);
            System.out.println("Контакты успешно загружены из файла: " + filename);
        } catch (IOException e) {
            System.out.println("Ошибка при загрузке файла: " + e.getMessage());
            System.out.println("Телефонная книга осталась без изменений");
        }
    }
}
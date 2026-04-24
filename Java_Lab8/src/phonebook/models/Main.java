package phonebook.models;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final PhoneBook phoneBook = new PhoneBook();

    static void main(String[] args) {
        System.out.println("Телефонная книга");
        boolean running = true;
        // непонятно почему в требованиях такая нумерация
        while (running) {
            System.out.println("Меню:");
            System.out.println("4. Добавить контакт");
            System.out.println("5. Удалить контакт");
            System.out.println("6. Найти по имени");
            System.out.println("7. Найти по номеру");
            System.out.println("8. Показать все контакты");
            System.out.println("9. Сохранить в файл");
            System.out.println("10. Загрузить из файла");
            System.out.println("11. Выход");
            System.out.print("Выберите действие: ");

            int choice = readInt();
            switch (choice) {
                case 4 -> addContact();
                case 5 -> removeContact();
                case 6 -> findByName();
                case 7 -> findByPhone();
                case 8 -> showAllContacts();
                case 9 -> saveToFile();
                case 10 -> loadFromFile();
                case 11 -> { running = false; System.out.println("До свидания!"); }
                default -> System.out.println("Неверный выбор. Введите число от 4 до 11");
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

            Contact c = new Contact(name, phone, email);
            if (phoneBook.addContact(c)) {
                System.out.println("Контакт успешно добавлен");
            }
        } catch (IllegalArgumentException e) {
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

    private static void showAllContacts() {
        ArrayList<Contact> all = phoneBook.getAllContacts();
        if (all.isEmpty()) {
            System.out.println("Телефонная книга пуста");
        } else {
            System.out.println("Все контакты:");
            all.forEach(System.out::println);
        }
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
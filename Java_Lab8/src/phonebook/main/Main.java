package phonebook.main;

import phonebook.models.Contact;
import phonebook.models.PhoneBook;
import phonebook.utils.ActionLogger;
import phonebook.utils.PhoneNumberValidator;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final PhoneBook phoneBook = new PhoneBook();

    public static void main(String[] args) {
        System.out.println("Телефонная книга");
        boolean running = true;
        // непонятно почему в требованиях нумерация с 4
        while (running) {
            System.out.println("\nМеню:");
            System.out.println("1. Добавить контакт");
            System.out.println("2. Удалить контакт");
            System.out.println("3. Редактировать контакт");
            System.out.println("4. Найти по имени");
            System.out.println("5. Найти по номеру");
            System.out.println("6. Найти по категории");
            System.out.println("7. Показать все контакты");
            System.out.println("8. Показать все, сгруппированные по категориям");
            System.out.println("9. Сохранить в файл");
            System.out.println("10. Загрузить из файла");
            System.out.println("11. Экспорт в CSV");
            System.out.println("12. Импорт из CSV");
            System.out.println("13. Показать логи");
            System.out.println("0. Выход");
            System.out.print("Выберите действие: ");

            int choice = readInt();
            switch (choice) {
                case 1 -> addContact();
                case 2 -> removeContact();
                case 3 -> editContact();
                case 4 -> findByName();
                case 5 -> findByPhone();
                case 6 -> findByCategory();
                case 7 -> showAllContacts();
                case 8 -> showGroupedByCategory();
                case 9 -> saveToTxt();
                case 10 -> loadFromTxt();
                case 11 -> exportToCsv();
                case 12 -> importFromCsv();
                case 13 -> showHistory();
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

    private static String readNonEmptyString() {
        while (true) {
            String input = scanner.nextLine().trim();
            if (!input.isEmpty())
                return input;
            System.out.println("Поле не может быть пустым. Попробуйте снова");
        }
    }

    private static String readValidPhone() {
        while (true) {
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Номер не может быть пустым.");
                continue;
            }
            String formatted = PhoneNumberValidator.format(input);
            if (PhoneNumberValidator.isValid(formatted))
                return formatted;
            System.out.println("Неверный формат номера.");
        }
    }

    private static void addContact() {
        try {
            System.out.print("Введите имя: ");
            String name = readNonEmptyString();
            System.out.print("Телефона: ");
            String phone = readValidPhone();
            System.out.print("Введите email (необязательно): ");
            String email = scanner.nextLine().trim();
            System.out.print("Введите категорию (необязательно): ");
            String category = scanner.nextLine();

            Contact c = new Contact(name, phone, email, category);
            if (phoneBook.addContact(c)) {
                System.out.println("Контакт успешно добавлен");
                ActionLogger.log("Добавлен контакт: " + name);
            }
        }
        catch (IllegalArgumentException e) {
            System.out.println("Ошибка валидации: " + e.getMessage());
        }
    }

    private static void removeContact() {
        String phone = readValidPhone();
        if (phoneBook.removeContact(phone)) {
            System.out.println("Контакт удален");
        } else {
            System.out.println("Контакт с таким номером не найден");
            ActionLogger.log("Удалён контакт: " + phoneBook.findByPhone(phone).getName());
        }
    }

    private static void editContact() {
        System.out.print("Введите номер телефона контакта для редактирования: ");
        String oldPhone = scanner.nextLine().trim();
        Contact contact = phoneBook.findByPhone(oldPhone);

        if (contact == null) {
            System.out.println("Контакт не найден.");
            return;
        }

        System.out.println("Текущие данные: " + contact);
        System.out.println("Введите новые значения (нажмите Enter, чтобы оставить без изменений):");

        System.out.print("Имя [" + contact.getName() + "]: ");
        String newName = scanner.nextLine().trim();
        if (newName.isEmpty())
            newName = contact.getName();

        System.out.print("Телефон [" + contact.getPhoneNumber() + "]: ");
        String newPhone = scanner.nextLine().trim();
        if (newPhone.isEmpty())
            newPhone = contact.getPhoneNumber();
        else {
            if (!PhoneNumberValidator.isValid(newPhone)) {
                System.out.println("Неверный формат нового номера. Отмена.");
                return;
            }
        }

        System.out.print("Email [" + contact.getEmail() + "]: ");
        String newEmail = scanner.nextLine().trim();
        if (newEmail.isEmpty())
            newEmail = contact.getEmail();

        System.out.print("Категория [" + contact.getCategory() + "]: ");
        String newCategory = scanner.nextLine();
        if (newCategory.isEmpty())
            newCategory = contact.getCategory();

        if (phoneBook.updateContact(oldPhone, newName, newPhone, newEmail, newCategory))
            ActionLogger.log("Изменён контакт: " + oldPhone + " -> " + newPhone);
    }

    private static void findByName() {
        System.out.println("Введите имя для поиска: ");
        String name = readNonEmptyString();
        ArrayList<Contact> found = phoneBook.findByName(name);
        ActionLogger.log("Поиск по имени: " + name);
        if (found.isEmpty()) {
            System.out.println("Контакты не найдены");
        } else {
            System.out.println("Найдено контактов: " + found.size());
            found.forEach(System.out::println);
        }
    }

    private static void findByPhone() {
        String phone = readValidPhone();
        Contact found = phoneBook.findByPhone(phone);
        ActionLogger.log("Поиск по номеру: " + phone);
        if (found != null) {
            System.out.println("Найден: " + found);
        } else {
            System.out.println("Контакт не найден");
        }
    }

    private static void findByCategory() {
        System.out.print("Введите категорию: ");
        String category = scanner.nextLine();
        ActionLogger.log("Поиск по категории: " + category);
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
        ActionLogger.log("Демонстрация всех контактов");
        if (all.isEmpty()) {
            System.out.println("Телефонная книга пуста");
        } else {
            System.out.println("Все контакты:");
            all.forEach(System.out::println);
        }
    }

    private static void showGroupedByCategory() {
        Map<String, List<Contact>> grouped = phoneBook.getContactsGroupedByCategory();
        ActionLogger.log("Демонстрация контактов по группам");
        if (grouped.isEmpty()) {
            System.out.println("Телефонная книга пуста.");
            return;
        }

        grouped.keySet().stream().sorted().forEach(cat -> {
            System.out.println("\nКатегория: " + cat + " (" + grouped.get(cat).size() + ")");
            grouped.get(cat).forEach(System.out::println);
        });
    }

    private static void saveToTxt() {
        fileAction("сохранения (TXT)", filename -> {
            try { phoneBook.saveToFile(filename); ActionLogger.log("Сохранение в файл: " + filename); }
            catch (IOException e) { throw new RuntimeException(e); }
        });
    }

    private static void loadFromTxt() {
        fileAction("загрузки (TXT)", filename -> {
            try { phoneBook.loadFromFile(filename); ActionLogger.log("Загрузка из файла" + filename); }
            catch (IOException e) { throw new RuntimeException(e); }
        });
    }

    private static void exportToCsv() {
        fileAction("экспорта в CSV", filename -> {
            try { phoneBook.exportToCsv(filename); ActionLogger.log("Загрузка в таблицу: " + filename); }
            catch (IOException e) { throw new RuntimeException(e); }
        });
    }

    private static void importFromCsv() {
        fileAction("импорта из CSV", filename -> {
            try { phoneBook.importFromCsv(filename); ActionLogger.log("Загрузка из таблицы: " + filename);}
            catch (IOException e) { throw new RuntimeException(e); }
        });
    }

    private static void showHistory() {
        ActionLogger.showHistory();
    }

    // Универсальный обработчик файловых операций
    private static void fileAction(String actionName, FileOperation op) {
        System.out.print("Введите имя файла для " + actionName + ": ");
        String filename = scanner.nextLine().trim();
        if (filename.isEmpty()) {
            System.out.println("Имя файла не может быть пустым.");
            return;
        }
        try {
            op.execute(filename);
            System.out.println("Операция завершена успешно.");
        }
        catch (Exception e) {
            System.out.println("Ошибка: " + e.getCause().getMessage());
        }
    }

    @FunctionalInterface
    private interface FileOperation {
        void execute(String filename) throws Exception;
    }
}
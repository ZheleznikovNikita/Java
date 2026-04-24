package phonebook.models;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class PhoneBook {
    private ArrayList<Contact> contacts;

    public PhoneBook() {
        contacts = new ArrayList<>();
    }

    public boolean addContact(Contact contact) {
        if (contacts.contains(contact)){
            System.out.println("Такой контакт уже существует");
            return false;
        }
        return contacts.add(contact);
    }

    public boolean removeContact(String phoneNumber) {
        //return contacts.removeAll((Collection<?>) contacts.stream().filter((Contact c) -> c.getPhoneNumber().equals(phoneNumber)));
        return contacts.removeIf((Contact c) -> c.getPhoneNumber().equals(phoneNumber));
    }

    public boolean updateContact(String oldPhone, String newName, String newPhone, String newEmail, String category) {
        Contact contact = findByPhone(oldPhone);
        if (contact == null) {
            System.out.println("Контакт с номером " + oldPhone + " не найден.");
            return false;
        }

        if (!oldPhone.equals(newPhone)) {
            for (Contact c : contacts) {
                if (c.getPhoneNumber().equals(newPhone)) {
                    System.out.println("Новый номер уже занят другим контактом.");
                    return false;
                }
            }
        }

        contact.setName(newName);
        contact.setPhoneNumber(newPhone);
        contact.setEmail(newEmail);
        contact.setCategory(category);
        System.out.println("Контакт успешно обновлён.");
        return true;
    }

    public ArrayList<Contact> findByName(String name) {
        ArrayList<Contact> res = new ArrayList<>();
        for (var elem : contacts)
            if (elem.getName().equalsIgnoreCase(name))
                res.add(elem);
        return res;
    }

    public Contact findByPhone(String phoneNumber) {
        Contact res = null;
        for (var elem : contacts)
            if (elem.getPhoneNumber().equals(phoneNumber))
                res = elem;
        return res;
    }

    public ArrayList<Contact> findByCategory(String category) {
        return contacts.stream().filter(c -> c.getCategory().equalsIgnoreCase(category)).collect(Collectors.toCollection(ArrayList::new));
    }

    public Map<String, List<Contact>> getContactsGroupedByCategory() {
        return contacts.stream().collect(Collectors.groupingBy(Contact::getCategory));
    }

    public ArrayList<Contact> getAllContacts() {
        ArrayList<Contact> res = new ArrayList<>(contacts);
        Collections.sort(res, Comparator.comparing(Contact::getName, String.CASE_INSENSITIVE_ORDER));
        return res;
    }

    public void saveToFile(String filename) throws IOException {
        try (FileWriter fw = new FileWriter(filename, false)) {
            for (var elem : contacts) {
                String cur = String.format("%s|%s|%s|%s\n", elem.getName(), elem.getPhoneNumber(), elem.getEmail(), elem.getCategory());
                fw.write(cur);
                fw.flush();
            }
        }
    }

    public void loadFromFile(String filename) throws IOException {
        File file = new File(filename);
        if (!file.exists())
            throw new IOException("Файл не найден: " + filename);

        ArrayList<Contact> tempContacts = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split("\\|", -1);
                if (parts.length < 2)
                    throw new IOException("Неверный формат строки в файле: " + line);

                String name = parts[0].trim();
                String phone = parts[1].trim();
                String email = parts.length > 2 ? parts[2].trim() : "";
                String category = parts.length > 3 ? parts[3].trim() : "Без категории";
                tempContacts.add(new Contact(name, phone, email, category));
            }
        }

        for (Contact c : tempContacts)
            addContact(c);
    }

    public void exportToCsv(String filename) throws IOException {
        try (FileWriter fw = new FileWriter(filename, false)) {
            fw.write("Name,Phone,Email,Category\n");
            for (Contact c : contacts) {
                String name = c.getName().replace("\"", "\"\"");
                String phoneNumber = c.getPhoneNumber().replace("\"", "\"\"");
                String email = c.getEmail().replace("\"", "\"\"");
                String category = c.getCategory().replace("\"", "\"\"");
                fw.write(String.format("\"%s\",\"%s\",\"%s\",\"%s\"%n", name, phoneNumber, email, category));
            }
        }
    }

    public void importFromCsv(String filename) throws IOException {
        File file = new File(filename);
        if (!file.exists())
            throw new IOException("Файл не найден: " + filename);

        int added = 0, skipped = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty())
                    continue;

                String[] parts = line.replace("\"", "").split(",", 4);
                if (parts.length < 4)
                    continue;

                try {
                    Contact c = new Contact(parts[0].trim(), parts[1].trim(), parts[2].trim(), parts[3].trim());
                    if (addContact(c))
                        added++;
                    else
                        skipped++;
                }
                catch (IllegalArgumentException e) {
                    skipped++;
                }
            }
        }
        System.out.printf("Загружено: %d, пропущено дубликатов/ошибок: %d.%n", added, skipped);
    }
}

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
}

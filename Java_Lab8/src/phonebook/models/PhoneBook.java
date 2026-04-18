package phonebook.models;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class PhoneBook {
    private ArrayList<Contact> contacts;

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
        return (ArrayList<Contact>)contacts.stream().filter((Contact c) -> c.getName().toLowerCase().contains(name.toLowerCase()));
    }

    public Contact findByPhone(String phoneNumber) {
        Contact res = null;
        for (var elem : contacts)
            if (elem.getPhoneNumber().equals(phoneNumber))
                res = elem;
        return res;
    }

    public ArrayList<Contact> getAllContacts() {
        ArrayList<Contact> res = new ArrayList<>();
        Collections.sort(res, Comparator.comparing(Contact::getName));
        return res;
    }

    public void saveToFile(String filename) {
        try (FileWriter fw = new FileWriter(filename, false)) {
            for (var elem : contacts) {
                String cur = String.format("%s | %s | %s", elem.getName(), elem.getPhoneNumber(), elem.getEmail());
                fw.write(cur);
                fw.flush();
            }
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void loadFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null){
                var cur = line.trim().split("|");
                Contact c = new Contact(cur[0], cur[1], cur[2]);
                addContact(c);
            }
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}

package phonebook.models;

import java.util.Objects;
import phonebook.utils.Checker;

public class Contact {
    private String name;
    private String phoneNumber;
    private String email;

    public Contact(String name, String phoneNumber, String email) {
        setName(name);
        setPhoneNumber(phoneNumber);
        if (email == null || email.trim().isEmpty())
            email = "";
        this.email = email;
    }
    public Contact(String name, String phoneNumber) {
        setName(name);
        setPhoneNumber(phoneNumber);
    }

    // Геттеры
    public String getName() { return name; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getEmail() { return email; }
    // Сеттеры
    public void setName(String name) {
        Checker.check_string(name);
        this.name = name;
    }
    public void setPhoneNumber(String phoneNumber) {
        Checker.check_string(phoneNumber);
        this.phoneNumber = phoneNumber;
    }
    public void setEmail(String email) {
        Checker.check_string(email);
        this.email = email;
    }

    @Override
    public String toString() {
        return String.format("%s: %s, %s", name, phoneNumber, email);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact other = (Contact) o;
        return Objects.equals(name, other.name)
                && Objects.equals(phoneNumber, other.phoneNumber)
                && Objects.equals(email, other.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, phoneNumber, email);
    }
}

package phonebook.models;

import java.util.Objects;
import java.util.Set;

import phonebook.utils.Checker;
import phonebook.utils.PhoneNumberValidator;

public class Contact {
    private static final Set<String> CATEGORIES = Set.of("семья", "работа", "друзья");

    private String name;
    private String phoneNumber;
    private String email;
    private String category;

    public Contact(String name, String phoneNumber, String email, String category) {
        setName(name);
        setPhoneNumber(phoneNumber);
        setEmail(email);
        setCategory(category);
    }

    // Геттеры
    public String getName() { return name; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getEmail() { return email; }
    public String getCategory() { return category; }
    // Сеттеры
    public void setName(String name) {
        Checker.check_string(name);
        this.name = name;
    }
    public void setPhoneNumber(String phoneNumber) {
        Checker.check_string(phoneNumber);
        String formatted = PhoneNumberValidator.format(phoneNumber);
        if (!PhoneNumberValidator.isValid(formatted))
            throw new IllegalArgumentException("Неверный формат номера");
        this.phoneNumber = formatted;
    }
    public void setEmail(String email) {
        this.email = (email == null || email.trim().isEmpty()) ? "" : email;
    }
    public void setCategory(String category) {
        if (category == null || category.trim().isEmpty()) {
            this.category = "";
            return;
        }
        if (!CATEGORIES.contains(category.toLowerCase()))
                throw new IllegalArgumentException("Такой категории нет");
        this.category = category;
    }

    @Override
    public String toString() {
        return email.trim().isEmpty() ? String.format("%s: %s, [%s]", name, phoneNumber, category) :
                String.format("%s: %s, %s, [%s]", name, phoneNumber, email, category);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact other = (Contact)o;
        return Objects.equals(PhoneNumberValidator.normalize(phoneNumber), PhoneNumberValidator.normalize(other.phoneNumber));
    }

    @Override
    public int hashCode() {
        return Objects.hash(phoneNumber);
    }
}

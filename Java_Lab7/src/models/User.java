package models;

import utils.ValueChecker;

public class User {
    private String firstName;
    private String lastName;
    private String email;
    private int age;
    private String phone;
    private String address;

    private User(Builder builder) {
        firstName = builder.firstName;
        lastName = builder.lastName;
        email = builder.email;
        age = builder.age;
        phone = builder.phone;
        address = builder.address;
    }

    // Геттеры
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public int getAge() { return age; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }

    public static class Builder {
        private String firstName;
        private String lastName;
        private String email;
        private int age = 0;
        private String phone = null;
        private String address = null;

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }
        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }
        public Builder email(String email) {
            this.email = email;
            return this;
        }
        public Builder age(int age) {
            ValueChecker.check_low_equal_zero_int(age);
            this.age = age;
            return this;
        }
        public Builder phone(String phone) {
            ValueChecker.check_string(phone);
            this.phone = phone;
            return this;
        }
        public Builder address(String address) {
            ValueChecker.check_string(address);
            this.address = address;
            return this;
        }

        public User build() {
            if (firstName == null || firstName.trim().isEmpty())
                throw new IllegalStateException("Пустое имя");
            if (lastName == null || lastName.trim().isEmpty())
                throw new IllegalStateException("Пустое имя");
            if (email == null || email.trim().isEmpty())
                throw new IllegalStateException("Пустое имя");
            return new User(this);
        }
    }

    @Override
    public String toString() {
        return "Имя: " + firstName +
                "\nФамилия: " + lastName +
                "\nEmail: " + email +
                (age == 0 ? "" : "\nВозраст: " + age) +
                (phone == null ? "" : "\nТелефон: " + phone) +
                (address == null ? "" : "\nАдрес: " + address);
    }

    static void main() {
        User user1 = new User.Builder()
                .firstName("Иван")
                .lastName("Иванов")
                .email("ivanov@example.com")
                .age(28)
                .phone("+7 (999) 123-45-67")
                .address("Имеется")
                .build();
        System.out.println(user1);

        User user2 = new User.Builder()
                .firstName("Валерий")
                .lastName("Абрамов")
                .email("asyou@sfedu.ru")
                .build();
        System.out.println(user2);

        try {
            new User.Builder()
                    .firstName("Тест")
                    .lastName("Ошибка")
                    .age(20)
                    .build();
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }

        User user3 = new User.Builder()
                .firstName("Весомое")
                .lastName("Важная")
                .email("important@gmail.com")
                .address("Козырный")
                .build();
        System.out.println(user3);
    }
}

package phonebook.utils;
public class Checker {
    public static void check_string(String s) {
        if (s == null || s.trim().isEmpty())
            throw new IllegalArgumentException("Empty string");
    }
}

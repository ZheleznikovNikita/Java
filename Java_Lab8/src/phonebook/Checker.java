package phonebook;

public class Checker {
    static void check_string(String s) {
        if (s == null || s.trim().isEmpty())
            throw new IllegalArgumentException("Empty string");
    }
}

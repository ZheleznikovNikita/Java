package utils;

public class ValueChecker {
    public static void check_string(String s) throws IllegalArgumentException {
        if (s == null || s.trim().isEmpty())
            throw new IllegalArgumentException("Подана пустая строка");
    }
    public static void check_low_zero_int(int num) throws IllegalArgumentException {
        if (num < 0)
            throw new IllegalArgumentException("Число меньше нуля");
    }
    public static void check_low_equal_zero_double(double num) throws IllegalArgumentException {
        if (num <= 0)
            throw new IllegalArgumentException("Число меньше нуля");
    }
    public static void check_low_equal_zero_int(int num) throws IllegalArgumentException {
        if (num <= 0)
            throw new IllegalArgumentException("Число меньше нуля");
    }

}

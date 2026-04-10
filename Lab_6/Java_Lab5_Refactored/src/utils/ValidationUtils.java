package utils;

public class ValidationUtils {

    private ValidationUtils() { }

    // Проверка строки
    public static void check_name(String s, String error_message) throws Exception {
        if (s == null || s.isEmpty())
            throw new Exception(error_message);
    }
    // Проверка числового значения (меньше нуля)
    public static void check_number_less(double n, String error_message) throws Exception {
        if (n < 0)
            throw new Exception(error_message);
    }
    // Проверка числового значения (меньше или равно нуля)
    public static void check_number_less_or_equal(double n, String error_message) throws Exception {
        if (n <= 0)
            throw new Exception(error_message);
    }
}

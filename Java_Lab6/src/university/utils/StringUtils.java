package university.utils;

public class StringUtils {
    public static void check_string(String s, String error_message) throws Exception {
        if (s == null || s.isEmpty())
            throw new Exception(error_message);
    }
    public static boolean isValidName(String name) throws Exception {
        check_string(name, "Передана пустая строка");
        return name.matches("^[a-zA-Zа-яА-ЯёЁ\\s]+$");
    }
    public static String capitalize(String str) throws Exception {
        check_string(str, "Передана пустая строка");
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}

package phonebook.utils;

import java.util.regex.Pattern;

public class PhoneNumberValidator {

    private static final Pattern PHONE_PATTERN = Pattern.compile("^[\\d+\\-\\s()]+$");

    public static boolean isValid(String phone) {
        if (phone == null || phone.trim().isEmpty())
            return false;
        if (!PHONE_PATTERN.matcher(phone.trim()).matches())
            return false;
        String withoutSpaces = phone.replaceAll("\\s+", "");
        return withoutSpaces.length() >= 5 && withoutSpaces.length() <= 15;
    }

    public static String format(String phone) {
        if (phone == null)
            return "";
        return phone.replaceAll("\\s+", " ").trim();
    }

    public static String normalize(String phone) {
        if (phone == null)
            return "";
        return phone.replaceAll("[^\\d+]", "");
    }
}

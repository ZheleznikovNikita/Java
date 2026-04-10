package bank.utils;

import java.util.Random;

public class IdGenerator {
    public static long generateId() {
        return System.currentTimeMillis() + new Random().nextLong();
    }
}

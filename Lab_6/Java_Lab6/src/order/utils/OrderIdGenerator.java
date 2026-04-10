package order.utils;

import java.util.Random;

public class OrderIdGenerator {
    public static long generateOrderItemId() { return Math.abs(System.currentTimeMillis() + new Random().nextLong()); }
}

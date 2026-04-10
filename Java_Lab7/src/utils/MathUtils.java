package utils;

public class MathUtils {
    public static final double PI = Math.PI;
    public static final double E = Math.E;

    private MathUtils() {}

    /**
     * Проверка числа на простоту
     * @param n проверяемое число
     * @return {@code true}, если {@code n} простое, иначе {@code false}
     */
    public static boolean isPrime(int n) {
        if (n <= 1)
            return false;
        if (n <= 3)
            return true;
        if (n % 2 == 0 || n % 3 == 0)
            return false;
        for (int i = 5; i * i <= n; i += 6)
            if (n % i == 0 || n % (i + 2) == 0)
                return false;
        return true;
    }

    /**
     * Вычисляет наибольший общий делитель
     * @param a первое число
     * @param b второе число
     * @return НОД(a, b) всегда положительный
     */
    public static int gcd(int a, int b) {
        a = Math.abs(a);
        b = Math.abs(b);
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    /**
     * Вычисляет наименьшее общее кратное
     * @param a первое число
     * @param b второе число
     * @return НОК(a, b)
     */
    public static int lcm(int a, int b) {
        if (a == 0 || b == 0)
            return 0;
        int gcdVal = gcd(a, b);
        return (int) Math.abs((long) a / gcdVal * b);
    }

    /**
     * Вычисляет факториал числа n
     * @param n неотрицательное целое число
     * @return {@code n}! в виде long
     * @throws IllegalArgumentException если {@code n < 0}
     * @throws ArithmeticException если результат превышает {@code Long.VAX_VALUE}
     */
    public static long factorial(int n) {
        if (n < 0)
            throw new IllegalArgumentException("Число меньше нуля");
        long res = 1;
        for (int i = 2; i <= n; i++) {
            if (Long.MAX_VALUE / i < res)
                throw new ArithmeticException("Переполнение: " + n + "! Превышает Long.MAX_VALUE");
            res *= i;
        }
        return res;
    }

    /**
     * Вычисляет n-тое число Фибоначчи
     * @param n индекс числа
     * @return {@code n-тое} число Фибоначчи
     * @throws IllegalArgumentException если {@code n < 0}
     */
    public static int fibonacci(int n) {
        if (n < 0)
            throw new IllegalArgumentException("Индекс числа не может быть отрицательным");
        if (n == 0)
            return  0;
        if (n == 1)
            return 1;
        int a = 0, b = 1;
        for (int i = 2; i <= n; i++) {
            int temp = a + b;
            a = b;
            b = temp;
        }
        return b;
    }
}

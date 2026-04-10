package main;

import static utils.MathUtils.*;

public class Main {
    static void main() {
        System.out.println("Математические константы:");
        System.out.printf("PI = %.15f%n", PI);
        System.out.printf("E = %.15f%n%n", E);

        System.out.println("Проверка на простоту:");
        System.out.println("isPrime(17) = " + isPrime(17));
        System.out.println("isPrime(20) = " + isPrime(20));
        System.out.println("isPrime(2) = " + isPrime(2));
        System.out.println("isPrime(1) = " + isPrime(1));

        System.out.println("\nНОД и НОК:");
        System.out.println("gcd(48, 18) = " + gcd(48, 18));
        System.out.println("lcm(4, 6) = " + lcm(4, 6));
        System.out.println("lcm(-12, 8) = " + lcm(-12, 8)); // Корректно обрабатывает отрицательные

        System.out.println("\nФакториал:");
        System.out.println("factorial(10) = " + factorial(10));
        System.out.println("factorial(0) = " + factorial(0));
        try {
            factorial(21); // 21! > Long.MAX_VALUE
        } catch (ArithmeticException ex) {
            System.out.println("Ошибка factorial(21): " + ex.getMessage());
        }

        System.out.println("\nЧисла Фибоначчи:");
        System.out.println("Fibonacci(10) = " + fibonacci(10));
        System.out.println("Fibonacci(0) = " + fibonacci(0));
        try {
            fibonacci(-1);
        } catch (IllegalArgumentException ex) {
            System.out.println("Ошибка Fibonacci(-1): " + ex.getMessage());
        }
    }
}

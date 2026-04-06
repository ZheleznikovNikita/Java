import java.util.Scanner;

public class Task7 {
    public static void MultiplicationTable() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите число от 1 до 10");
        int num = scanner.nextInt();
        if (num < 1 || num > 10) {
            System.out.print("Число за пределами диапазона!");
            return;
        }
        for (int i = 1; i < 11; ++i)
            System.out.printf("%d * %d = %d\n", i, num, i * num);
    }
}

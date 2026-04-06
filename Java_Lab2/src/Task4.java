import java.util.Scanner;
public class Task4 {
    public static void DaysInMonth() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите номер месяца: ");
        int month = scanner.nextInt();
        if (month < 1 || month > 12) {
            System.out.print("Такого месяца не существует\n");
            return;
        }
        switch (month){
            case 1, 3, 5, 7, 8, 10, 12 -> System.out.print("31 день\n");
            case 4, 6, 9, 11 -> System.out.print("30 дней\n");
            case 2 -> System.out.print("28 или 29 дней\n");
            default -> System.out.print("Некорректный номер месяца\n");
        }
    }
}

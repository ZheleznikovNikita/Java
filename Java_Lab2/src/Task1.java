import java.util.Scanner;
public class Task1 {
    public static void CurrentTime() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите текущий час: ");
        int hour = scanner.nextInt();
        if (hour < 0 || hour > 23) {
            System.out.print("Время за пределами диапазона\n");
            return;
        }
        if (hour >= 0 && hour <= 5)
            System.out.print("Ночь\n");
        else if (hour >= 6 && hour <= 11)
            System.out.print("Утро\n");
        else if (hour >= 12 && hour <= 17)
            System.out.print("День\n");
        else System.out.print("Вечер\n");
    }
}

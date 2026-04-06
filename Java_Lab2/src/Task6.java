import java.util.Arrays;
import java.util.Scanner;

public class Task6 {
    public static void CountPosNegZero() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите размер массива: ");
        int size = scanner.nextInt();
        int[] arr = new int[size];
        for (int i = 0; i < size; i++)
            arr[i] = (int)(Math.random() * 21) - 10;
        System.out.print("Заполненный массив: ");
        for (var elem : arr)
            System.out.print(elem + " ");
        System.out.print("\n");
        int pos = 0;
        int neg = 0;
        int zero = 0;
        for (var elem : arr) {
            if (elem > 0)
                pos++;
            else if (elem < 0)
                neg++;
            else
                zero++;
        }
        System.out.printf("В массиве %d положительных, %d отрицательеных, %d нулей\n", pos, neg, zero);
    }
}

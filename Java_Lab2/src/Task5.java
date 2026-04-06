import java.util.Scanner;

public class Task5 {
    public static void MaxAndMin(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите размер массива: ");
        int size = scanner.nextInt();
        int[] arr = new int[size];
        for (int i = 0; i < size; i++)
            arr[i] = (int)(Math.random() * 101) - 50;
        System.out.print("Заполненный массив: ");
        for (var elem : arr)
            System.out.print(elem + " ");
        System.out.print("\n");
        var max = Integer.MIN_VALUE;
        var maxInd = -1;
        var min = Integer.MAX_VALUE;
        var minInd = -1;
        for (int i = 0; i < size; ++i) {
            if (arr[i] > max) {
                max = arr[i];
                maxInd = i;
            }
            if (arr[i] < min) {
                min = arr[i];
                minInd = i;
            }
        }
        System.out.printf("Максимальное значение: %d, индекс: %d\nМинимальное значение: %d, индекс: %d\n", max, maxInd, min, minInd);
    }
}

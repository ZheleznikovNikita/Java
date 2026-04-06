import java.util.Arrays;
import java.util.Scanner;
public class Task2 {
    public static void arrSum() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите размер массива: ");
        int size = scanner.nextInt();
        int[] arr = new int[size];
        for (int i = 0; i < size; i++)
            arr[i] = (int)(Math.random() * 51);
        System.out.print("Заполненный массив: ");
        for (var elem : arr)
            System.out.print(elem + " ");
        var sum = Arrays.stream(arr).sum();
        var average = sum / (double)size;
        System.out.printf("\nСумма элементов массива: %d\nСреднее арифметическое: %.2f\n", sum, average);
    }
}

import java.util.Scanner;

public class Task8 {
    public static void MagicSquare() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите размер двумерного массива: ");
        int size = scanner.nextInt();
        int[][] matr = new int[size][size];
        for (int i = 0; i < size; ++i)
            for (int j = 0; j < size; ++j)
                matr[i][j] = (int)(Math.random() * 10);
        for (var row : matr){
            for (var elem : row)
                System.out.print(elem + " ");
            System.out.print("\n");
        }
        boolean isMagicSquare = true;
        int expectedSum = 0;
        // Считаем сумму с которой будем сравнивать
        for (var elem : matr[0])
            expectedSum += elem;
        // Считаем суммы в строках и столбцах
        for (int i = 0; i < size; ++i) {
            int sumInRow = 0;
            int sumInCol = 0;
            for (int j = 0; j < size; ++j) {
                sumInRow += matr[i][j];
                sumInCol += matr[j][i];
            }
            if (expectedSum != sumInRow || expectedSum != sumInCol){
                isMagicSquare = false;
                break;
            }
        }
        // Суммы по диагоналям
        int leftDiagonalSum = 0;
        int rightDiagonalSum = 0;
        // Левая диагональ
        for (int i = 0; i < size; ++i)
            leftDiagonalSum += matr[i][i];
        // Правая диагональ
        for (int i = size - 1; i >= 0; --i)
            rightDiagonalSum += matr[i][i];
        if (leftDiagonalSum != expectedSum || rightDiagonalSum != expectedSum)
            isMagicSquare = false;
        if (isMagicSquare)
            System.out.print("Да, это магический квадрат");
        else System.out.print("Нет, это не магический квадрат!");
    }
}

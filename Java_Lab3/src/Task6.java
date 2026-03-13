import java.io.*;
import java.util.*;

public class Task6 {
    public static void NumsInFile() {
        var file = new File("numbers.txt");
        ArrayList<Integer> nums = new ArrayList<>();
        try(Scanner scanner = new Scanner(new FileInputStream(file))){
            while (scanner.hasNextInt())
                nums.add(scanner.nextInt());
        }
        catch (IOException e){
            System.out.println("Ошибка чтения файла: " + e.getMessage());
        }
        int sum = 0;
        for (var elem : nums)
            sum += elem;
        double average = (double)sum / nums.toArray().length;
        var min = Collections.min(nums); // Collections - интерфейс коллекции элементов
        var max = Collections.max(nums);
        System.out.printf("Сумма: %d\nСреднее: %f.2\nМинимум: %d\nМаксимум: %d", sum, average, min, max);
    }
}

import java.util.Arrays;
import java.util.Scanner;
import java.io.*;

public class Task3 {
    public static void CountCharsStrsWords() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите имя файла: ");
        var input_file_name = scanner.nextLine();
        int chars = 0, strings = 0, words = 0;
        // Проверяем, существует ли указанный файл
        var input_file = new File(input_file_name);
        if (!input_file.exists()){
            System.out.println("Файл не существует");
            return;
        }
        // Читаем файл и считаем
        try(BufferedReader br = new BufferedReader(new FileReader(input_file))){
            String line;
            while ((line = br.readLine()) != null){
                strings++;
                chars += line.length() + 1; // +1 за каждый перенос каретки
                var words_arr = Arrays.stream(line.trim().replaceAll("\\s+", " ").split(" ")).filter(s -> !s.isEmpty());
                words+= words_arr.toArray().length;
            }
        }
        catch (IOException e){
            System.out.println("Ошибка чтения файла: " + e.getMessage());
        }
        System.out.printf("Символов: %d\nСлов: %d\nСтрок: %d\n", chars, words, strings);
    }
}

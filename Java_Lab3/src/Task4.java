import java.util.*;
import java.io.*;

public class Task4 {
    public static void FindWordInFile() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите имя файла: ");
        var input_file_name = scanner.nextLine();
        System.out.println("Введите слово для поиска: ");
        var aim = scanner.nextLine().toLowerCase(Locale.ROOT);
        // Проверяем, существует ли файл
        var input_file = new File(input_file_name);
        if (!input_file.exists()){
            System.out.println("Файл не существует");
            return;
        }
        // Список пар для итогового результата
        ArrayList<Map.Entry<Integer, String>> res = new ArrayList<>();
        // Читаем файл и формируем ответ
        try(BufferedReader br = new BufferedReader(new FileReader(input_file))) {
            String line;
            int num = 1;
            while ((line = br.readLine()) != null){
                if (line.toLowerCase().contains(aim))
                    res.add(new AbstractMap.SimpleEntry<Integer, String>(num, line));
                num++;
            }
        }
        catch (IOException e){
            System.out.println("Ошибка чтения файла: " + e.getMessage());
        }
        for (var elem : res) {
            System.out.printf("Строка %d: %s\n", elem.getKey(), elem.getValue());
        }
    }
}

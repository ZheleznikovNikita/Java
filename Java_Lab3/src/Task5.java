import java.io.*;
import java.util.Scanner;

public class Task5 {
    public static void WriteFromConsole() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Имя файла: ");
        var input_file_name = scanner.nextLine();
        // Проверяем, существует ли файл
        var input_file = new File(input_file_name);
        if (!input_file.exists()){
            System.out.println("Файл не существует");
            return;
        }
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Введите строки (пустая строка — завершить):");
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(input_file))) {
            String line;
            while(true){
                System.out.print("> ");
                line = consoleReader.readLine();
                if (line.isEmpty())
                    break;
                bw.write(line);
                bw.newLine();
            }
        }
        catch(IOException e){
            System.out.println("Ошибка чтения файла: " + e.getMessage());
        }
        System.out.println("Файл notes.txt успешно сохранён.\n");
    }
}

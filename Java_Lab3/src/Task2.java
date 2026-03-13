import java.io.*;
import java.nio.ByteBuffer;
import java.util.Scanner;

public class Task2 {
    public static void CopyWithBuffer() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите название исходного файла: ");
        var input_file_name = scanner.nextLine();
        System.out.println("Введите название целевого файла");
        var output_file_name = scanner.nextLine();
        // Проверяем, существуют ли файлы
        var input_file = new File(input_file_name);
        var output_file = new File(output_file_name);
        if (!input_file.exists()){
            System.out.println("Файл не существует");
            return;
        }
        if (!output_file.exists()){
            System.out.println("Файл не существует");
            return;
        }
        // Копирование файла
        var start_time = System.currentTimeMillis();
        try(BufferedInputStream bis = new BufferedInputStream(new FileInputStream(input_file));
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(output_file))){
            byte[] buffer = new byte[8192];
            int read_bytes;
            while ((read_bytes = bis.read(buffer)) != -1)
                bos.write(buffer, 0, read_bytes);
        }
        catch (IOException e) {
            System.out.println("Ошибка работы с файлами: " + e.getMessage());
        }
        var end_time = System.currentTimeMillis();
        System.out.println("Размер файла: " + output_file.length() + " байт");
        System.out.println("Время копирования: " + (end_time - start_time) + " мс");
        System.out.println("Файл успешно скопирован!");
    }
}

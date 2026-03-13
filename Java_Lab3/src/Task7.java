import java.io.*;
import java.util.Scanner;

public class Task7 {
    // Проверка русская ли буква
    private static boolean isRU(char c) {
        return (c >= 'а' && c <= 'я') || (c >= 'А' && c <= 'Я') || c == 'ё' || c == 'Ё';
    }
    // Проверка латинская ли буква
    private static boolean isLAT(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }
    // Шифр для русской буквы
    private static char cipherRU(char c, int shift) {
        if (!Character.isLetter(c))
            return c;
        int size = 32; // не учитываем ё
        char start = Character.isLowerCase(c) ? 'а' : 'А';
        char end = Character.isLowerCase(c) ? 'я' : 'Я';
        if (c < start || c > end)
            return c;
        int pos = c - start;
        int normal_shift = ((shift % size) + size) % size;
        int new_pos = (pos + normal_shift) % size;
        return (char)(start + new_pos);
    }
    // Шифр для латинской буквы
    private static char cipherLAT(char c, int shift) {
        int size = 26;
        char start = Character.isLowerCase(c) ? 'a' : 'A';
        char end = Character.isLowerCase(c) ? 'z' : 'Z';
        if (c < start || c > end)
            return c;
        int pos = c - start;
        int normal_shift = ((shift % size) + size) % size;
        int new_pos = (pos + normal_shift) % size;
        return (char)(start + new_pos);
    }
    // Шифр всех букв вынесен в общий метод
    private static char cipher(char c, int shift) {
        if (!Character.isLetter(c))
            return c;
        if (isRU(c))
            return cipherRU(c, shift);
        else if (isLAT(c))
            return cipherLAT(c,shift);
        return c;
    }

    public static void CaesarCipher() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите имя входного файла: ");
        var input_file_name = scanner.nextLine();
        System.out.println("Введите имя выходного файла: ");
        var output_file_name = scanner.nextLine();
        System.out.println("Введите сдвиг: ");
        var shift = scanner.nextInt();
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
        //
        try (BufferedReader br = new BufferedReader(new FileReader(input_file));
             BufferedWriter bw = new BufferedWriter(new FileWriter(output_file))){
            int c;
            while ((c = br.read()) != -1){
                char new_char = cipher((char)c, shift);
                bw.write(new_char);
            }
        }
        catch (IOException e){
            System.out.println("Ошибка чтения или записи: " + e.getMessage());
        }
        System.out.println("Файл зашифрован");
    }
}

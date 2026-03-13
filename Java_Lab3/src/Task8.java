import java.io.*;

public class Task8 {
    public static void CompareSpeeds() {
        var big_file = new File("big.txt");
        System.out.print("Создание тестового файла... ");
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(big_file))){
            for (int i = 0; i < 80000; ++i){
                bw.write("Это похоже на какую-то длинную строку для тестирования скорости чтения!\n");
            }
        }
        catch(IOException e){
            System.out.println("Ошибка записи файла: " + e.getMessage());
        }

        System.out.printf("Готово (%d байт)\n", big_file.length());
        System.out.print("Чтение побайтово (FileInputStream): ");
        var start = System.currentTimeMillis();
        try(FileInputStream fis = new FileInputStream(big_file)){
            while (fis.read() != -1);
        }
        catch(IOException e){
            System.out.println("Ошибка чтения файла: " + e.getMessage());
        }
        var end = System.currentTimeMillis();
        var res1 = end - start;
        System.out.print(res1 + " мс\n");
        System.out.println("Чтение с буфером 4096 байт (BufferedInputStream): ");
        start = System.currentTimeMillis();
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(big_file))){
            byte[] buffer = new byte[4096];
            while (bis.read(buffer) != -1);
        }
        catch (IOException e){
            System.out.println("Ошибка чтения файла: " + e.getMessage());
        }
        end = System.currentTimeMillis();
        var res2 = end - start;
        System.out.print(res2 + " мс\n");
        System.out.printf("Вывод: буферизация ускоряет чтение примерно в %d раза!", (res1 / res2));
    }
}

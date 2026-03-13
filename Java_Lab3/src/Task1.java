import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Random;

public class Task1 {
    // Приватные поля (private), свойственные классу, а не экземпляру (static), неизменяемые (final)
    private static final String _fileName = "input.bin";
    private static final int _count = 100;

    public static void ReadAndWriteBytes() {
        // Переменные для чисел и дальнейшего сравнения и генерации
        int[] numbersForWrite = new int[_count];
        ArrayList<Integer> numbersAfterRead = new ArrayList<>();
        Random random = new Random();
        // Запись элементов в файл
        try (FileOutputStream fos = new FileOutputStream(_fileName)){
            System.out.println("Запись чисел в файл...");
            ByteBuffer buffer = ByteBuffer.allocate(4); // Создаётся буфер ёмкостью 4
            for (int i = 0; i < _count; ++i) {
                var num = random.nextInt(-10000,10000);
                numbersForWrite[i] = num;
                buffer.putInt(0, num);
                fos.write(buffer.array());
            }
            System.out.println("Числа записаны в файл");
        }
        catch (IOException e){
            System.out.println("Ошибка при записи файла: " + e.getMessage());
        }
        // Чтение элементов из файла
        try (FileInputStream fis = new FileInputStream(_fileName)){
            System.out.println("Чтение чисел из файла: ");
            byte[] bytes = new byte[4];
            int cnt = 0;
            while (fis.read(bytes) != -1) {
                int num = ByteBuffer.wrap(bytes).getInt(); // wrap создаёт буфер из имеющегося массива
                numbersAfterRead.add(num);
                // +- понятный и красивый вывод
                System.out.print(num + " ");
                if (((cnt + 1) % 10) == 0)
                    System.out.print("\n");
                ++cnt;
            }
        }
        catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
        }
        // Сравнение записанных и прочитанных элементов
        for (int i = 0; i < _count; ++i) {
            if (numbersForWrite[i] != numbersAfterRead.get(i)){
                System.out.println("Числа записаны или прочитаны неправильно");
                return;
            }
        }
        System.out.println("Числа записаны верно!");
    }
}

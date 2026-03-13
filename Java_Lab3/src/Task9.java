import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class Task9 {
    public static void DownloadHTML() {
        try {
            URL url = new URL("https://httpbin.org/html ");
            try(InputStream is = url.openStream();
                BufferedInputStream bis = new BufferedInputStream(is);
                FileOutputStream fos = new FileOutputStream("url.txt");
                BufferedOutputStream bos = new BufferedOutputStream(fos)) {
                byte[] buffer = new byte[8192];
                int read_bytes;
                while ((read_bytes = bis.read(buffer)) != -1)
                    bos.write(buffer, 0, read_bytes);
                System.out.println("HTML-код успешно загружен!");
            }
            catch (IOException e) {
                System.out.println("Ошибка чтения: " + e.getMessage());
        }
        }
        catch (MalformedURLException e) {
            System.out.println("Ошибка чтения HTML: " + e.getMessage());
        }
    }
}

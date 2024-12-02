import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class LoadTask implements Runnable {
    private String urlFile;
    private String savePath;

    private int numberFile;

    public LoadTask(String urlFile, String savePath, int numberFile) {
        this.urlFile = urlFile;
        this.savePath = savePath;
        this.numberFile = numberFile;
    }

    @Override
    public void run() {
        try (InputStream in = new BufferedInputStream(new URL(urlFile).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(savePath)) {
            System.out.println(Thread.currentThread().getName() + " start download file number " + numberFile);

            byte[] dataBuffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }

            System.out.println(Thread.currentThread().getName() + " finish download file number " + numberFile);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }
}

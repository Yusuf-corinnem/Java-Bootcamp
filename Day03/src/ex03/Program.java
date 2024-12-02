import java.io.*;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Program {
    private static final HashMap<Integer, String> urls = new HashMap<>();
    private static int threadsCount = 0;

    public static void main(String[] args) {
        startProgram(args);
    }

    public static void startProgram(String[] args) {
        checkArgument(args);
        readFile();
        processDownloads();
    }

    private static void checkArgument(String[] args) {
        if (args.length != 1) error("No arguments");
        if (!args[0].contains("--threadsCount=")) error("Not founds arguments");

        threadsCount = Integer.parseInt(args[0].substring("--threadsCount=".length()));
    }

    public static void readFile() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("files_urls.txt"))) {
            String str = "";

            while ((str = bufferedReader.readLine()) != null) {
                urls.put(Integer.parseInt(str.split(" ")[0]), str.split(" ")[1]);
            }

        } catch (IOException e) {
            error(e.getMessage());
        }
    }

    public static void processDownloads() {
        ExecutorService service = Executors.newFixedThreadPool(threadsCount);

        for (int i = 0; i < urls.size(); ++i) {
            service.execute(new LoadTask(urls.get(i + 1), getNameOfUrlFile(i + 1), i + 1));
        }

        service.shutdown();
    }

    public static String getNameOfUrlFile(int key) {
        return urls.get(key).substring(urls.get(key).lastIndexOf("/") + 1);
    }

    private static void error(String message) {
        System.out.println(message);
        System.exit(-1);
    }
}

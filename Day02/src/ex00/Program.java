import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) throws IOException {
        startProgram();
    }

    private static void startProgram() throws IOException {
        while (true) {
            if (compareValue(getFileTypesOfFile(), getDataFromFile()) == 1) break;
        }
    }

    private static HashMap<String, String> getFileTypesOfFile() throws IOException {
        HashMap<String, String> fileTypes = new HashMap<>();

        try {
            FileInputStream inputStream = new FileInputStream("signatures.txt");
            Scanner scanner = new Scanner(inputStream);

            while (scanner.hasNextLine()) {
                String str = scanner.nextLine();
                fileTypes.put(str.split(", ")[0], str.split(", ")[1]);
            }

            inputStream.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }

        return fileTypes;
    }

    private static StringBuilder getDataFromFile() throws IOException {
        Scanner scanner = new Scanner(System.in);
        StringBuilder str = new StringBuilder(scanner.nextLine());

        if (str.toString().equals("42")) return str;

        try {
            FileInputStream inputStream = new FileInputStream(String.valueOf(str));

            for (int i = 0; i < 1000; ++i) {
                int number = inputStream.read();

                if (Integer.toHexString(number).length() == 1) str.append("0");

                str.append(Integer.toHexString(number)).append(" ");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }

        return str;
    }

    private static int compareValue(HashMap<String, String> fileTypes, StringBuilder str) throws IOException {
        if (str.toString().equals("42")) return 1;

        try {
            FileOutputStream outputStream = new FileOutputStream("result.txt");
            String result = "";

            for (Map.Entry<String, String> entry : fileTypes.entrySet()) {
                if (str.toString().toUpperCase().contains(entry.getValue())) {
                    result = "PROCESSED";
                    outputStream.write(entry.getKey().getBytes());
                    break;
                } else {
                    result = "UNDEFINED";
                }
            }

            System.out.println(result);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }

        return 0;
    }
}

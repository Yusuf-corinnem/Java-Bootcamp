import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;

import static java.lang.Math.sqrt;

public class Program {
    public static void main(String[] args) throws IOException {
        start(args);
    }

    public static void start(String[] args) throws IOException {
        HashMap<String, String> words = new HashMap<>();
        for (String path : args) {
            readFile(path, words);
        }

        Vector<Integer> a = frequencyOfWordsInLine(args[0], words);
        Vector<Integer> b = frequencyOfWordsInLine(args[1], words);

        createDictionaryFile(words);

        System.out.println("Similarity = " + Math.floor(findNumerator(a, b) / findDenominator(a, b) * 100) / 100);
    }

    public static void readFile(String path, HashMap<String, String> words) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            String str = "";

            while ((str = bufferedReader.readLine()) != null) {
                String[] strSplit = str.split(" ");
                for (String s : strSplit) {
                    if (!s.equals(words.get(s))) {
                        words.put(s, s);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    public static Vector<Integer> frequencyOfWordsInLine(String path, HashMap<String, String> words) {
        Vector<Integer> vector = new Vector<>(words.size());

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {

            for (int i = 0; i < words.size(); ++i) {
                vector.add(0);
            }

            String str = "";

            while ((str = bufferedReader.readLine()) != null) {
                String[] strSplit = str.split(" ");
                for (String s : strSplit) {
                    int count = 0;
                    for (String values : words.values()) {
                        if (s.equals(values)) {
                            vector.set(count, vector.get(count) + 1);
                        }
                        count++;
                    }
                }
            }

        } catch (IOException e) {
            System.err.println("Ошибка при считывании частоты слов в тексте: " + e.getMessage());
            System.exit(-1);
        }

        return vector;
    }

    public static int findNumerator(Vector<Integer> a, Vector<Integer> b) {
        int numerator = 0;

        for (int i = 0; i < a.size(); ++i) {
            numerator += a.get(i) * b.get(i);
        }

        return numerator;
    }

    public static double findDenominator(Vector<Integer> a, Vector<Integer> b) {
        return sqrt(vectorSquaring(a)) * sqrt(vectorSquaring(b));
    }

    public static int vectorSquaring(Vector<Integer> vector) {
        int result = 0;

        for (int i = 0; i < vector.size(); ++i) {
            result += vector.get(i) * vector.get(i);
        }

        return result;
    }

    public static void createDictionaryFile(HashMap<String, String> words) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("dictionary.txt"))) {
            for (HashMap.Entry<String, String> entry : words.entrySet()) {
                writer.write("key: " + entry.getKey() + ", value: " + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Ошибка при создании файла: " + e.getMessage());
            System.exit(-1);
        }
    }

    }

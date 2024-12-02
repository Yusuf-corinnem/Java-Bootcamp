import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        start();
    }

    static void start() {
        Scanner scanner = new Scanner(System.in);
        String[] array = new String[18];
        int countFill = 0;

        for (int i = 0; i < array.length; ++i) {
            array[i] = scanner.nextLine();
            if (array[i].equals("42")) break;
            countFill++;
        }

        checkCorrectInput(array, countFill);
        String[] scores = new String[countFill / 2];
        findMinScore(array, countFill, scores);
        String[] result = new String[countFill];
        drawGraph(array, result, scores);
    }

    static void checkCorrectInput(String[] array, int countFill) {
        for (int i = 0; i < countFill - 1; ++i) {
            if (i % 2 != 0) continue;
            for (int j = i + 2; j < countFill; j += 2) {
                char chI = array[i].charAt(5);
                int numI = Character.getNumericValue(chI);
                char chJ = array[j].charAt(5);
                int numJ = Character.getNumericValue(chJ);
                if (numJ < numI) {
                    System.err.println("Illegal Argument");
                    System.exit(-1);
                }
            }
        }
    }

    static void findMinScore(String[] array, int countFill, String[] scores) {
        for (int i = 0; i < countFill; ++i) {
            if (i % 2 == 0) continue;
            int minScore = 9;
            for (int j = 0; j < 9; ++j) {
                char ch = array[i].charAt(j);
                if (ch == ' ') continue;
                int num = Character.getNumericValue(ch);
                if (num < minScore) minScore = num;
            }
            scores[i / 2] = Integer.toString(minScore);
        }
    }

    static void drawGraph(String[] array, String[] result, String[] scores) {
        for (int i = 0; i < result.length; ++i) {
            if (i % 2 != 0) continue;
            result[i] = array[i];
            result[i] = result[i].concat(" ");
            for (int j = 0; j < Integer.parseInt(scores[i / 2]); ++j) result[i] = result[i].concat("=");
            result[i] = result[i].concat(">");
            System.out.println(result[i]);
        }
    }
}

import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        start();
    }

    static void start() {
        Scanner scanner = new Scanner(System.in);
        String inputStr = scanner.nextLine();
        int countUnique = countUniqueChars(inputStr);
        int[][] countDuplicate = countDuplicateChars(inputStr, countUnique);
        int[][] graph = fillGraphMatrix(countDuplicate, countUnique);
        drawGraph(graph);
    }

    static int countUniqueChars(String str) {
        boolean[] isUniqueChars = new boolean[256]; // Массив для отслеживания уникальных символов (256 для всех возможных значений char)
        int countUnique = 0;

        for (int i = 0; i < str.length(); ++i) {
            if (i < str.length() - 1 && str.charAt(i) == '4' && str.charAt(i + 1) == '2') break;
            if (!isUniqueChars[str.charAt(i)]) {
                isUniqueChars[str.charAt(i)] = true;
                countUnique++;
            }
        }

        return countUnique;
    }

    static int[][] countDuplicateChars(String str, int countUnique) {
        int[][] array = new int[2][65535];
        int[][] chars = new int[2][countUnique];

        for (char c : str.toCharArray()) {
            if (c == '4') break;
            if (array[0][c] != c) array[0][c] = c; // char
            if (array[1][c] <= 999) array[1][c]++; // count
        }

        for (int i = 0, j = 0; i < 65535 && j < countUnique; ++i) {
            if (array[0][i] == 0) continue;
            chars[0][j] = array[0][i];
            chars[1][j] = array[1][i];
            j++;
        }

        return chars;
    }

    static int[][] fillGraphMatrix(int[][] array, int countUnique) {
        int[][] graph = new int[12][countUnique];
        int k = 0;
        int firstMax = findMax(array);

        for (int i = 0; i < countUnique; ++i) {
            int max = findMax(array);
            for (int j = 0; j < array[0].length; ++j) {
                int index = 10 - (max * 10 / firstMax);
                if (max == array[1][j] && k < graph[0].length && index >= 0 && index < 11) {
                    graph[index][k] = array[1][j];
                    for (int l = index + 1; l < 11; ++l) {
                        graph[l][k] = -1;
                    }
                    graph[11][k] = array[0][j];
                    k++;
                    array[1][j] = 0;
                    array[0][j] = 0;
                }
            }
        }

        return graph;
    }

    static int findMax(int[][] array) {
        int max = 0;

        for (int i = 0; i < array[0].length; ++i) {
            if (array[1][i] > max) max = array[1][i];
        }

        return max;
    }

    static void drawGraph(int[][] graph) {
        System.out.println();
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph[i].length; j++) {
                if (graph[i][j] == -1) System.out.print(" #  ");
                else if (graph[i][j] == 0) System.out.print("    ");
                else if (i == 11) System.out.print(" " + (char) graph[i][j] + "  ");
                else if (graph[i][j] >= 10 && graph[i][j] <= 99) System.out.print(graph[i][j] + "  ");
                else if (graph[i][j] >= 100 && graph[i][j] <= 999) System.out.print(graph[i][j]);
                else System.out.print(" " + graph[i][j] + "  ");
            }
            System.out.println();
        }
    }
}

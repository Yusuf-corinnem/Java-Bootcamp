package edu.school21.printer.logic;

public class ImageDrawer {
    public static void drawImage(char[][] array, char white, char black) {
        for (int y = 0; y < array.length; ++y) {
            for (int x = 0; x < array[y].length; ++x) {
                if (array[y][x] == 1)  System.out.print(black);
                else if (array[y][x] == 0) System.out.print(white);
            }
            System.out.print("\n");
        }
    }
}

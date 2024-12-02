package edu.school21.printer.rendering;

import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi;

public class ImageDrawer {
    public static void drawImage(char[][] array, String white, String black) {
        ColoredPrinter coloredPrinter = new ColoredPrinter();
        for (int y = 0; y < array.length; ++y) {
            for (int x = 0; x < array[y].length; ++x) {
                if (array[y][x] == 1) {
                    coloredPrinter.print(" ", Ansi.Attribute.NONE, Ansi.FColor.BLACK, stringToColor(black));
                } else if (array[y][x] == 0) {
                    coloredPrinter.print(" ", Ansi.Attribute.NONE, Ansi.FColor.WHITE, stringToColor(white));
                }
            }
            System.out.print("\n");
        }
    }

    private static Ansi.BColor stringToColor(String color) {
        return switch (color.toLowerCase()) {
            case "black" -> Ansi.BColor.BLACK;
            case "red" -> Ansi.BColor.RED;
            case "green" -> Ansi.BColor.GREEN;
            case "yellow" -> Ansi.BColor.YELLOW;
            case "blue" -> Ansi.BColor.BLUE;
            case "magenta" -> Ansi.BColor.MAGENTA;
            case "cyan" -> Ansi.BColor.CYAN;
            case "white" -> Ansi.BColor.WHITE;
            default -> null;
        };
    }
}

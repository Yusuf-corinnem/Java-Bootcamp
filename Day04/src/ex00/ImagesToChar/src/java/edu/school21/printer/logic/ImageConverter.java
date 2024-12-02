package edu.school21.printer.logic;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageConverter {
    private final String path;
    private BufferedImage image;
    private char[][] chars;

    public ImageConverter(String path) {
        this.path = path;
    }

    public char[][] convertToArray() {
        readFile();
        fillArray();
        return chars;
    }

    private void readFile() {
        try {
            File file = new File(path);
            image = ImageIO.read(file);
            chars = new char[image.getHeight()][image.getWidth()];
        } catch (IOException e) {
            error(e.getMessage());
        }
    }

    private void fillArray() {
        for (int y = 0; y < image.getHeight(); ++y) {
            for (int x = 0; x < image.getWidth(); ++x) {
                int rgb = image.getRGB(x, y);

                int red = (rgb >> 16) & 0xff;
                int green = (rgb >> 8) & 0xff;
                int blue = rgb & 0xff;

                if (red == 0 && green == 0 && blue == 0) { // black
                    chars[y][x] = 1;
                } else if (red == 255 && green == 255 && blue == 255) { // white
                    chars[y][x] = 0;
                }
            }
        }
    }

    private static void error(String message) {
        System.out.println(message);
        System.exit(-1);
    }
}

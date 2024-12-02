package edu.school21.printer.logic;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class ImageConverter {
    private BufferedImage image;
    private char[][] chars;

    public char[][] convertToArray() {
        readFile();
        fillArray();
        return chars;
    }

    private void readFile() {
        try {
            InputStream inputStream = getClass().getResourceAsStream("/it.bmp");
            if (inputStream == null) throw new IOException("File not found");

            image = ImageIO.read(inputStream);
            if (image == null) throw new IOException("Image could not be read");

            chars = new char[image.getHeight()][image.getWidth()];
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
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
}

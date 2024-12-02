package edu.school21.printer.app;

import edu.school21.printer.logic.ImageConverter;
import edu.school21.printer.logic.ImageDrawer;

class MainApp {
    private static char white;
    private static char black;
    private static String pathToImage;

    public static void main(String[] args) {
        checkArguments(args);
        ImageConverter imageConverter = new ImageConverter(pathToImage);
        ImageDrawer.drawImage(imageConverter.convertToArray(), white, black);
    }

    private static void checkArguments(String[] args) {
        if (args.length != 3) error("No arguments");
        if (!args[0].contains("--white=")) error("Not founds argument: white");
        if (!args[1].contains("--black=")) error("Not founds arguments: black");
        if (!args[2].contains("--pathToImage=")) error("Not founds arguments: pathToImage");

        white = args[0].substring("--white=".length()).charAt(0);
        black = args[1].substring("--black=".length()).charAt(0);
        pathToImage = args[2].substring("--pathToImage=".length());
    }

    private static void error(String message) {
        System.out.println(message);
        System.exit(-1);
    }
}
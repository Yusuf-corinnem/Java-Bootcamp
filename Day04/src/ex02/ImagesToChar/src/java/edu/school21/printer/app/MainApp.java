package edu.school21.printer.app;

import edu.school21.printer.exception.IllegalParametersException;
import edu.school21.printer.logic.ImageConverter;
import edu.school21.printer.rendering.ImageDrawer;
import com.beust.jcommander.JCommander;
import edu.school21.printer.parsing.Args;

class MainApp {
    public static void main(String[] args) {
        try {
            Args args1 = new Args();
            JCommander.newBuilder().addObject(args1).build().parse(args);

            if (args1.getWhite() == null || args1.getBlack() == null) {
                throw new IllegalParametersException("Illegal Parameters");
            }
            
            ImageConverter imageConverter = new ImageConverter();
            ImageDrawer.drawImage(imageConverter.convertToArray(), args1.getWhite(), args1.getBlack());
        } catch (IllegalParametersException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }
}
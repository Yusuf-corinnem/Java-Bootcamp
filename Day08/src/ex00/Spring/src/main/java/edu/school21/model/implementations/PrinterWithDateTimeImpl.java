package edu.school21.model.implementations;

import edu.school21.model.interfaces.Printer;
import edu.school21.model.interfaces.Renderer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PrinterWithDateTimeImpl implements Printer {
    private final Renderer renderer;

    public PrinterWithDateTimeImpl(Renderer renderer) {
        this.renderer = renderer;
    }

    @Override
    public void print(String text) {
        renderer.print("Date: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "\nText: " + text);
    }
}

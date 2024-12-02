package edu.school21.model.implementations;

import edu.school21.model.interfaces.Printer;
import edu.school21.model.interfaces.Renderer;

public class PrinterWithPrefixImpl implements Printer {
    private final Renderer renderer;

    public PrinterWithPrefixImpl(Renderer renderer) {
        this.renderer = renderer;
    }

    private String prefix = "";

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public void print(String text) {
        renderer.print(prefix + " " + text);
    }
}

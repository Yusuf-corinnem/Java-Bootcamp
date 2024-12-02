package edu.school21.model.implementations;

import edu.school21.model.interfaces.PreProcessor;
import edu.school21.model.interfaces.Renderer;

public class RendererStandardImpl implements Renderer {
    private final PreProcessor preProcessor;

    public RendererStandardImpl(PreProcessor preProcessor) {
        this.preProcessor = preProcessor;
    }

    @Override
    public void print(String text) {
        System.out.println(preProcessor.changeCase(text));
    }
}

package edu.school21.model.implementations;

import edu.school21.model.interfaces.PreProcessor;
import edu.school21.model.interfaces.Renderer;

public class RendererErrImpl implements Renderer {
    private final PreProcessor preProcessor;

    public RendererErrImpl(PreProcessor preProcessor) {
        this.preProcessor = preProcessor;
    }

    @Override
    public void print(String text) {
        System.err.println(preProcessor.changeCase(text));
    }
}

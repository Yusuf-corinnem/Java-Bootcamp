package edu.school21.model.implementations;

import edu.school21.model.interfaces.PreProcessor;

public class PreProcessorToUpperImpl implements PreProcessor {
    @Override
    public String changeCase(String text) {
        return text.toUpperCase();
    }
}

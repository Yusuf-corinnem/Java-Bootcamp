package edu.school21.model.implementations;

import edu.school21.model.interfaces.PreProcessor;

public class PreProcessorToLowerImpl implements PreProcessor {
    @Override
    public String changeCase(String text) {
        return text.toLowerCase();
    }
}

package edu.school21.printer.parsing;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(separators = "=")
public class Args {
    @Parameter(names = "--white")
    private String white;

    @Parameter(names = "--black")
    private String black;

    public String getWhite() {
        return white;
    }

    public String getBlack() {
        return black;
    }
}
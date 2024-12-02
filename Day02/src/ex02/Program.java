import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) throws IOException {
        startProgram(args);
    }

    public static void startProgram(String[] args) throws IOException {
        checkArgument(args);

        PathManager pathManager = new PathManager(args[0].substring("--current-folder=".length()));

        System.out.println(pathManager.getPath());

        CommandHandler commandHandler = new CommandHandler();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("-> ");
            String input = scanner.nextLine();
            if (!commandHandler.handleCommand(input, pathManager)) {
                break;
            }
        }
    }

    private static void checkArgument(String[] args) {
        if (args.length == 0) error("No arguments");

        if (!args[0].contains("--current-folder=")) error("Not founds arguments");

        if (!(new File(args[0].substring("--current-folder=".length())).exists())) {
            error("No such file or directory: " + args[0].substring("--current-folder=".length()));
        }
    }

    private static void error(String message) {
        System.out.println(message);
        System.exit(-1);
    }
}
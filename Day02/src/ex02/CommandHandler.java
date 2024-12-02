import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CommandHandler {
    private final Map<String, Command> commands = new HashMap<>();

    public CommandHandler() {
        commands.put("ls", new LsCommand());
        commands.put("cd", new CdCommand());
        commands.put("mv", new MvCommand());
    }

    public boolean handleCommand(String input, PathManager pathManager) throws IOException {
        String[] arguments = input.split(" ");
        Command command = commands.get(arguments[0]);

        if (command != null) {
            command.execute(arguments, pathManager);
        } else if ("exit".equals(input)) {
            return false;
        } else {
            System.out.println("Invalid command: " + input + "\nUsage:\n- ls\n- cd <directory>\n- mv <source> <destination>");
        }

        return true;
    }
}
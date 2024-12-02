import java.io.IOException;

public interface Command {
    void execute(String[] args, PathManager pathManager) throws IOException;
}
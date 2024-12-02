import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class MvCommand implements Command {
    @Override
    public void execute(String[] args, PathManager pathManager) throws IOException {
        if (args.length < 3) {
            System.out.println("Invalid input. Usage: mv <source> <destination>");
            return;
        }

        if (!pathManager.checkPath(pathManager.getPath() + "/" + args[1])) return;

        String sourcePath = pathManager.getPath() + "/" + args[1];
        String destinationPath = pathManager.getPath() + "/" + args[2];
        File sourceFile = new File(sourcePath);
        File destinationFile = new File(destinationPath);

        if (destinationFile.isDirectory()) {
            destinationPath = destinationFile.getPath() + "/" + sourceFile.getName();
            destinationFile = new File(destinationPath);
        }

        try {
            Files.move(sourceFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }
}

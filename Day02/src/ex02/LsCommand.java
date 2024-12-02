import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class LsCommand implements Command {
    @Override
    public void execute(String[] args, PathManager pathManager) throws IOException {
        File file = new File(pathManager.getPath());
        File[] filesList = file.listFiles();

        for (File f : Objects.requireNonNull(filesList)) {
            if (f.isFile()) {
                System.out.println(f.getName() + " " + f.length() / 1024 + " KB");
            } else if (f.isDirectory()) {
                System.out.println(f.getName() + " " + getDirectorySize(f) / 1024 + " KB");
            }
        }
    }

    public static long getDirectorySize(File file) {
        long size = 0;
        File[] filesList = file.listFiles();

        for (File f : Objects.requireNonNull(filesList)) {
            if (f.isFile()) {
                size += f.length();
            } else if (f.isDirectory()) {
                size += getDirectorySize(f);
            }
        }

        return size;
    }
}

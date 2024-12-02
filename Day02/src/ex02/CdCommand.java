import java.io.IOException;

public class CdCommand implements Command{
    @Override
    public void execute(String[] args, PathManager pathManager) throws IOException {
        if (args.length < 2) {
            System.out.println("Invalid input. Usage: cd <directory>");
            return;
        }

        if (!pathManager.checkPath(pathManager.getPath() + "/" + args[1])) return;

        if (args[1].contains("..")) {
            int index = 0, count = 0;

            while ((index = args[1].indexOf("..", index)) != -1) {
                count++;
                index += "..".length();
            }

            String newPath = getNewPath(args[1], count, pathManager.getPath());

            if (pathManager.checkPath(newPath)) pathManager.setPath(newPath);
        } else if (pathManager.checkPath(pathManager.getPath() + "/" + args[1])) pathManager.setPath(pathManager.getPath() + "/" + args[1]);

        System.out.println(pathManager.getPath());
    }

    private static String getNewPath(String directory, int count, String path) {
        String[] directorySplit = directory.split("/");
        String[] pathSplit = path.split("/");
        String newPath = "";

        for (int i = 0; i < pathSplit.length - count; ++i) {
            if (i == pathSplit.length - count - 1) {
                newPath += pathSplit[i];
            } else newPath += pathSplit[i] + "/";
        }

        for (String s : directorySplit) {
            if (s.equals("..")) continue;
            newPath += "/" + s;
        }
        return newPath;
    }
}

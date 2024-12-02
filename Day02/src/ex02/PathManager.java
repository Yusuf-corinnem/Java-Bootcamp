import java.io.File;

public class PathManager {
    private String path;

    public PathManager(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean checkPath(String path) {
        if (!(new File(path).exists())) {
            System.out.println("No such file or directory in this path: " + path);
            return false;
        }

        return true;
    }


}

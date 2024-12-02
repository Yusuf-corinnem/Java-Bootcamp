public class Program {
    private static int count = 0;
    private static final Object lock = new Object();
    private static boolean isThread1Turn = true;

    public static boolean getIsIsThread1Turn() {
        return isThread1Turn;
    }

    public static void setIsThread1Turn(boolean isThread1Turn) {
        Program.isThread1Turn = isThread1Turn;
    }

    public static int getCount() {
        return count;
    }

    public static Object getLock() {
        return lock;
    }

    public static void main(String[] args) throws InterruptedException {
        startProgram(args);
    }

    public static void startProgram(String[] args) throws InterruptedException {
        checkArgument(args);

        count = Integer.parseInt(args[0].substring("--count=".length()));

        Thread thread1 = new Thread(new EggThread()); // producer
        Thread thread2 = new Thread(new HenThread()); // consumer

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            error(e.getMessage());
        }

        printMessage("Human");
    }

    private static void checkArgument(String[] args) {
        if (args.length == 0) error("No arguments");

        if (!args[0].contains("--count=")) error("Not founds arguments");

    }

    public static void printMessage(String message) {
        for (int i = 0; i < count; i++) {
            System.out.println(message);
        }
    }

    private static void error(String message) {
        System.out.println(message);
        System.exit(-1);
    }
}

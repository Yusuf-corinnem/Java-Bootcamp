public class Program {
    private static int arraySize = 0, threadsCount = 0;

    public static void main(String[] args) throws InterruptedException {
        startProgram(args);
    }

    public static void startProgram(String[] args) throws InterruptedException {
        checkArgument(args);

        SumManager sumManager = new SumManager(arraySize, threadsCount);
        sumManager.run();
    }

    private static void checkArgument(String[] args) {
        if (args.length != 2) error("No arguments");

        if (!args[0].contains("--arraySize=") || !args[1].contains("--threadsCount=")) error("Not founds arguments");

        arraySize = Integer.parseInt(args[0].substring("--arraySize=".length()));
        threadsCount = Integer.parseInt(args[1].substring("--threadsCount=".length()));

        if (arraySize > 2000000) error("Error: arraySize > 2 000 000");
        if (threadsCount > 2000000) error("Error: threadsCount > 2 000 000");
    }

    private static void error(String message) {
        System.out.println(message);
        System.exit(-1);
    }
}

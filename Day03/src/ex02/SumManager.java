import java.util.Random;

public class SumManager {
    private static int[] array;
    private int threadsCount;
    private Thread[] threads;
    private SumTask[] tasks;

    public static int[] getArray() {
        return array;
    }

    public SumManager(int arraySize, int threadsCount) {
        this.threadsCount = threadsCount;
        this.array = generateRandomArray(arraySize);
        this.threads = new Thread[threadsCount];
        this.tasks = new SumTask[threadsCount];
    }

    public void run() throws InterruptedException {
        System.out.println("Sum: " + simpleSum());

        System.out.println("Sum by threads: " + sumByThreads());
    }

    private int[] generateRandomArray(int size) {
        Random random = new Random();
        int[] array = new int[size];

        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(2001) - 1000;
        }

        return array;
    }

    private void fillThreads(int partSize) {
        for (int i = 0; i < threads.length; ++i) {
            int begin = i * partSize;
            int end = i == threads.length - 1 ? array.length : begin + partSize;

            tasks[i] = new SumTask(begin, end);
            threads[i] = new Thread(tasks[i]);

            threads[i].start();
        }
    }

    private int simpleSum() {
        int sum = 0;

        for (int value : array) {
            sum += value;
        }

        return sum;
    }

    private int sumByThreads() throws InterruptedException {
        fillThreads(array.length / threadsCount);

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
                System.exit(-1);
            }
        }

        for (int i = 0; i < threads.length; ++i) {
            System.out.println("Threads " + (i + 1) + ": from " + tasks[i].getBegin() + " to " + tasks[i].getEnd() + " sum is " + tasks[i].getSum());
        }

        return sumThreads();
    }

    private int sumThreads() {
        int sum = 0;

        for (SumTask task : tasks) {
            sum += task.getSum();
        }
        return sum;
    }
}

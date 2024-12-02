public class SumTask implements Runnable {
    private final int begin;
    private final int end;
    private int sum;

    public int getBegin() {
        return begin;
    }

    public int getEnd() {
        return end;
    }

    public SumTask(int begin, int end) {
        this.begin = begin;
        this.end = end;
        this.sum = 0;
    }

    public int getSum() {
        return sum;
    }

    @Override
    public void run() {
        for (int i = begin; i < end; ++i) {
            sum += SumManager.getArray()[i];
        }
    }
}

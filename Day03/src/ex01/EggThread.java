public class EggThread implements Runnable {
    @Override
    public void run() {
        Object lock = Program.getLock();
        for (int i = 0; i < Program.getCount(); i++) {
            synchronized (lock) {
                while (!Program.getIsIsThread1Turn()) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                System.out.println("Egg");
                Program.setIsThread1Turn(false);
                lock.notify();
            }
        }
    }
}

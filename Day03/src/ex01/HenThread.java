public class HenThread implements Runnable {
    @Override
    public void run() {
        Object lock = Program.getLock();
        for (int i = 0; i < Program.getCount(); i++) {
            synchronized (lock) {
                while (Program.getIsIsThread1Turn()) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                System.out.println("Hen");
                Program.setIsThread1Turn(true);
                lock.notify();
            }
        }
    }
}

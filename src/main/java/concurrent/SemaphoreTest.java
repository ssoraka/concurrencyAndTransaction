package concurrent;

import java.util.concurrent.Semaphore;

public class SemaphoreTest extends Thread {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);

        for (int i = 0; i < 10; i++) {
            new SemaphoreTest(semaphore).start();
        }
    }

    Semaphore semaphore;

    public SemaphoreTest(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        System.out.println(getName() + " wait");
        try {
            semaphore.acquire();
            System.out.println(getName() + " work");
            Thread.sleep(1000);
            semaphore.release();
            System.out.println(getName() + " end");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

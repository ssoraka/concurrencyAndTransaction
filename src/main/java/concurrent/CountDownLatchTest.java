package concurrent;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest extends Thread{
    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(3);

        for (int i = 0; i < 6; i++) {
            new CountDownLatchTest(countDownLatch).start();
        }

        try {
            countDownLatch.await();
        } catch (Exception e) {}
        System.out.println(Thread.currentThread().getName() + " work " + countDownLatch.getCount());
    }


    CountDownLatch countDownLatch;

    public CountDownLatchTest(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        System.out.println(getName() + " work " + countDownLatch.getCount());
        try {
            Thread.sleep(1000l);

        } catch (Exception e) {}
        countDownLatch.countDown();

    }
}

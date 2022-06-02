package concurrent;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CyclicBarrierTest extends Thread {
    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(2);

        new CyclicBarrierTest(barrier, 3).start();
        new CyclicBarrierTest(barrier, 3).start();
        new CyclicBarrierTest(barrier, 3).start();
    }

    Random random = new Random();
    CyclicBarrier barrier;
    int stageCount;

    public CyclicBarrierTest(CyclicBarrier barrier, int count) {
        this.barrier = barrier;
        this.stageCount = count;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < stageCount; i++) {
                Thread.sleep(random.nextInt(5000));
                barrier.await(6, TimeUnit.SECONDS);
                System.out.println(Thread.currentThread().getName() + " start stage " + i);
            }
        } catch (TimeoutException e) {
            System.out.println(Thread.currentThread().getName() + " without party");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

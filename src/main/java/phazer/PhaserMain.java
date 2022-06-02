package phazer;

import filo.RunnableWithException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;
import java.util.concurrent.ThreadPoolExecutor;

public class PhaserMain implements Runnable{
    public static void main(String[] args) throws InterruptedException {
        Phaser phaser = new Phaser(3);
        System.out.println("start_" + phaser.getPhase());


        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(new PhaserMain("thread-1", phaser));
        executorService.submit(new PhaserMain("thread-2", phaser));
        executorService.submit(new PhaserMain("thread-3", phaser));

//        phaser.arriveAndAwaitAdvance();

        

        executorService.submit(new PhaserMain("thread-4", phaser));
        executorService.submit(new PhaserMain("thread-5", phaser));
//        executorService.submit(new PhaserMain("thread-6", phaser));

//        phaser.arriveAndDeregister();

        while (((ThreadPoolExecutor)executorService).getActiveCount() > 0) {
            RunnableWithException.silent(() ->  Thread.sleep(1));
        }
        Thread.sleep(20000);
        executorService.shutdown();
    }




    Phaser phaser;
    String name;

    public PhaserMain(String name, Phaser phaser) {
        this.name = name;
        this.phaser = phaser;
    }

    @Override
    public void run() {
        System.out.println("register " + name + " from " + phaser.getPhase() + " to " + phaser.arriveAndAwaitAdvance());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        System.out.println("deregister " + name + " from " + phaser.getPhase() + " to " + phaser.arriveAndDeregister());


        System.out.println("register " + name + " from " + phaser.getPhase() + " to " + phaser.arriveAndAwaitAdvance());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("deregister " + name + " from " + phaser.getPhase() + " to " + phaser.arriveAndDeregister());
    }
}

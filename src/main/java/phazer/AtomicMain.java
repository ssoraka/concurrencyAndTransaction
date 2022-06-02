package phazer;

import filo.RunnableWithException;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class AtomicMain implements Runnable {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(0);


        Stream.generate(() -> new Thread(new AtomicMain(atomicInteger)))
                .limit(1000)
                .parallel()
                .map(t -> {
                    t.setDaemon(true);
                    t.start();
                    return t;
                }).forEach(t -> silent(() -> t.join()));

        System.out.println(atomicInteger);

    }

    static void silent(RunnableWithException callable) {
        try {
            callable.run();
        } catch (Exception e) {;}
    }






    AtomicInteger atomicInteger;

    public AtomicMain(AtomicInteger atomicInteger) {
        this.atomicInteger = atomicInteger;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            atomicInteger.updateAndGet(a -> a + 1);
        }
    }
}

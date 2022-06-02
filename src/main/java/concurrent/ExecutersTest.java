package concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ExecutersTest implements Callable<Double> {
    public static void main(String[] args) {
        ThreadFactory tf = new ThreadFactory() {
            AtomicInteger i = new AtomicInteger();
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setDaemon(false);
                t.setName("thread_" + i.getAndIncrement());
                return t;
            }
        };


        Random random = new Random();

        List<Callable<Double>> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(new ExecutersTest(random.nextInt(10), random.nextInt(10)));
        }

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), tf);
        try {
            List<Future<Double>> futures = executorService.invokeAll(list, 10, TimeUnit.SECONDS);

            for (Future<Double> f : futures) {
                try {
                    System.out.println(f.get(1, TimeUnit.SECONDS));
                } catch (TimeoutException t) {
                    System.out.println("timeout");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        executorService.shutdown();
    }

    int num;
    int pow;

    public ExecutersTest(int num, int pow) {
        this.num = num;
        this.pow = pow;
    }

    @Override
    public Double call() {
        double answer = 1.0;

        while (pow > 0) {
            answer *= num;
            pow--;
        }
        return answer;
    }
}

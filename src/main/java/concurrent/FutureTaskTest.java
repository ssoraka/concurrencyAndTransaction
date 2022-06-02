package concurrent;

import java.math.BigDecimal;
import java.util.concurrent.*;

public class FutureTaskTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Callable<BigDecimal> call = () -> {
            System.out.println("start task");
            Thread.sleep(800l);
            BigDecimal t = BigDecimal.ONE;
            for (int j = 1; j < 10_000; j++) {
                t = t.multiply(new BigDecimal(j));
            }
            System.out.println("end task");
            return t;
        };

        FutureTask<BigDecimal> task = new FutureTask<>(call);
        new Thread(task).start();

        while (!task.isDone()) {
            System.out.println("wait");
            Thread.sleep(20l);
        }

        System.out.println(task.get());
    }
}

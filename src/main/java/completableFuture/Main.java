package completableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
//        final int i = CompletableFuture.supplyAsync(Main::getMessage)
//                .exceptionally(e -> 25)
//                .get(500l, TimeUnit.MILLISECONDS)
//                .intValue();
//        System.out.println(i);

        final int i2 = CompletableFuture.supplyAsync(Main::getMessageWithException)
                .exceptionally(e -> 25)
                .get()
                .intValue();

        System.out.println(i2);

        final int i3 = CompletableFuture.supplyAsync(Main::getMessage)
                .get()
                .intValue();
    }


    static int getMessage() {
        try {
            Thread.sleep(1000l);
        } catch (Exception e) {}

        return 101;
    }

    static int getMessageWithException() {
        if (true)
            throw new RuntimeException();

        return 101;
    }
}

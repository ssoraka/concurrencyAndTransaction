package concurrent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ForkJoinTest implements Callable<Void> {

    static volatile int[] arr;
    static ForkJoinPool forkJoinPool;

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();

        Random random = new Random();

        for (int j = 0; j < 1; j++) {


            arr = Stream.generate(() -> random.nextInt(1000000)).limit(1000000).mapToInt(i -> i).toArray();
//            arr = new int[]{750, 561, 130, 984, 277, 498, 415, 304, 803, 494, 306, 810, 893};
//            System.out.println(Arrays.toString(arr));

            ForkJoinTask<Void> adapt = RecursiveTask.adapt(new ForkJoinTest(0, arr.length));
            final ForkJoinTask<Void> submit = forkJoinPool.submit(adapt);
            try {
                submit.get(60, TimeUnit.SECONDS);
                while (forkJoinPool.getActiveThreadCount() > 0) {
                    Thread.sleep(50);
                }
            } catch (TimeoutException e) {
                e.printStackTrace();
                System.out.println("timeout");
            } catch (Exception e) {
                e.printStackTrace();
            }

            forkJoinPool.shutdown();

            System.out.println(Arrays.toString(arr));
            for (int i = 1; i < arr.length; i++) {
                if (arr[i - 1] > arr[i]) throw new RuntimeException(arr[i - 1] + " " + arr[i]);
            }
        }
    }

    int start;
    int end;

    public ForkJoinTest(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public Void call() {
//        System.out.println(start + " " + end + " " + Arrays.toString(arr));
        int []b = arr;
        int s = start;

        int e = end - 1;

        if (s >= e) return null;
        if (s + 1 == e) {
            if (arr[s] > arr[e]) {
                int a = arr[e];
                arr[e] = arr[s];
                arr[s] = a;
            }
            return null;
        }

        while (e > s) {
            int mid = arr[s];
            if (arr[e] < mid) {
                arr[s] = arr[e];
                arr[e] = arr[s + 1];
                arr[s + 1] = mid;
                s++;
            } else {
                e--;
            }
        }

        RecursiveTask.adapt(new ForkJoinTest(start, s)).fork();
        RecursiveTask.adapt(new ForkJoinTest(s + 1, end)).fork();

//        ForkJoinTask<Void> adapt = RecursiveTask.adapt(new ForkJoinTest(start, s)).fork();
//        new ForkJoinTest(s + 1, end).call();
//        adapt.join();
        return null;
    }
}


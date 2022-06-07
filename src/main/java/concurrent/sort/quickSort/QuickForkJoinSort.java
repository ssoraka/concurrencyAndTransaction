package concurrent.sort.quickSort;

import concurrent.sort.Sorts;

import java.util.concurrent.*;

public class QuickForkJoinSort implements Callable<Void> {

    static int COUNT = 100000000;
    static ForkJoinPool POOL = ForkJoinPool.commonPool();
    static int[] arr;

    static {
        arr = Sorts.generate(COUNT);
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        ForkJoinTask<Void> submit = RecursiveTask.adapt(new QuickForkJoinSort(0, arr.length)).fork();

        try {
            submit.get(60, TimeUnit.SECONDS);
            while (POOL.getActiveThreadCount() > 0) {
                Thread.sleep(50);
            }
        } catch (TimeoutException e) {
            e.printStackTrace();
            System.out.println("timeout");
        } catch (Exception e) {
            e.printStackTrace();
        }

        POOL.shutdown();

        System.out.println("QuickForkJoinSort time = " + (System.currentTimeMillis() - start) + " mc");

//        Sorts.printSortedArr(arr);
    }

    int start;
    int end;

    public QuickForkJoinSort(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public Void call() {
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

        RecursiveTask.adapt(new QuickForkJoinSort(start, s)).fork();
        RecursiveTask.adapt(new QuickForkJoinSort(s + 1, end)).fork();
        return null;
    }
}


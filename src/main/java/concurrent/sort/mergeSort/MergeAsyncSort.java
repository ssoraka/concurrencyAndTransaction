package concurrent.sort.mergeSort;

import concurrent.sort.Sorts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class MergeAsyncSort implements Runnable {

    static int COUNT = 2048;
    static ForkJoinPool POOL = ForkJoinPool.commonPool();
    static int[] arr;
    static int[] buffer;
    static List<List<MergeAsyncSort>> tasks = new ArrayList<>();

    static {
        arr = Sorts.generate(COUNT);
        buffer = new int[arr.length];
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        try {
            CompletableFuture.runAsync(() -> sort2(0, arr.length)).get();
//            CompletableFuture.runAsync(new MergeAsyncSort(0, arr.length)).get();
//            while (POOL.) {
//                Thread.sleep(5000);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        POOL.shutdown();

        System.out.println("MergeForkJoinSort time = " + (System.currentTimeMillis() - start) + " mc");
        Sorts.printSortedArr(arr);
    }

    static void swap(int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    int start;
    int middle;
    int end;

    public MergeAsyncSort(int start, int end) {
        this.start = start;
        this.end = end;
        middle = (end + start) / 2;
    }

    public Void sort() {
        int i = start;
        int j = middle;
        int k = i;
        for (; i < middle && j < end; k++) {
            buffer[k] = Math.min(arr[i], arr[j]);
            int tmp = buffer[k] == arr[i] ? i++ : j++;
        }
        for (; i < middle; k++, i++) {
            buffer[k] = arr[i];
        }
        for (; j < end; k++, j++) {
            buffer[k] = arr[j];
        }

        System.arraycopy(buffer, start, arr, start, end - start);
        return null;
    }

    public static void sort2(int start, int end) {
        int middle = (end + start) / 2;

        CompletableFuture<Void> left = (middle - start > 2) ? CompletableFuture.runAsync(() -> sort2(start, middle)) : createCompletableFuture(start, middle);
        CompletableFuture<Void> right = (end - middle > 2) ? CompletableFuture.runAsync(() -> sort2(middle, end)) : createCompletableFuture(middle, end);
        CompletableFuture.allOf(left, right).join();


        int i = start;
        int j = middle;
        int k = i;
        for (; i < middle && j < end; k++) {
            buffer[k] = Math.min(arr[i], arr[j]);
            int tmp = buffer[k] == arr[i] ? i++ : j++;
        }
        for (; i < middle; k++, i++) {
            buffer[k] = arr[i];
        }
        for (; j < end; k++, j++) {
            buffer[k] = arr[j];
        }

        System.arraycopy(buffer, start, arr, start, end - start);
    }


    @Override
    public void run() {
//        System.out.println(String.format("%d %d %d", start, middle, end));
//        System.out.println("start  " + Arrays.toString(arr));

        CompletableFuture<Void> left = (middle - start > 2) ? CompletableFuture.runAsync(new MergeAsyncSort(start, middle)) : createCompletableFuture(start, middle);
        CompletableFuture<Void> right = (end - middle > 2) ? CompletableFuture.runAsync(new MergeAsyncSort(middle, end)) : createCompletableFuture(middle, end);

        try {
            CompletableFuture.allOf(left, right)
//                    .thenRun(() -> System.out.println("before " + Arrays.toString(arr)))

//                    .thenCompose(v -> sort())
                    .thenRun(()  -> sort()).join();
//                    .thenRun(() -> System.out.println("after  " + Arrays.toString(arr)))
//                    .get()
            ;
        } catch (Exception e) {

        }

    }

    static CompletableFuture<Void> createCompletableFuture(int start, int end) {
        if (end - start == 2 & arr[start] > arr[start + 1]) {
            swap(start, start + 1);
        }
        return CompletableFuture.completedFuture(null);
    }

}


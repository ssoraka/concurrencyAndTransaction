package concurrent.sort.mergeSort;

import concurrent.sort.Sorts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class MergeForkJoinSort implements Callable<Void> {

    static int COUNT = 100000000;
    static ForkJoinPool POOL = ForkJoinPool.commonPool();
    static int[] arr;
    static int[] buffer;
    static List<List<MergeForkJoinSort>> tasks = new ArrayList<>();

    static {
        arr = Sorts.generate(COUNT);
        buffer = new int[arr.length];
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        List<MergeForkJoinSort> tmp = Arrays.asList(new MergeForkJoinSort(0, arr.length));
        while (!tmp.isEmpty()) {
            tasks.add(tmp);
            tmp = nextLevelTask(tmp);
        }
        try {
            for (int i = tasks.size() - 1; i >= 0 ; i--) {
                tmp = tasks.remove(i);
                POOL.invokeAll(tmp);
//                .forEach(future -> {
//                    try {
//                        future.get();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                });

                while (POOL.getActiveThreadCount() > 0) {
                    Thread.sleep(50);
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        POOL.shutdown();

        System.out.println("MergeForkJoinSort time = " + (System.currentTimeMillis() - start) + " mc");
//        Sorts.printSortedArr(arr);
    }

    static List<MergeForkJoinSort> nextLevelTask(List<MergeForkJoinSort> list) {
        List<MergeForkJoinSort> answer = new ArrayList<>();

        for (MergeForkJoinSort elem : list) {
            if (elem.end - elem.start <= 2) continue;
            addTask(answer, elem.start, elem.middle);
            addTask(answer, elem.middle, elem.end);
        }
        return answer;
    }

    static void addTask(List<MergeForkJoinSort> list, int start, int end) {
        if (end - start < 2) return;
        if (end - start == 2) {
            if (arr[start] > arr[start + 1]) {
                swap(start, start + 1);
            }
            return;
        }
        list.add(new MergeForkJoinSort(start, end));
    }

    static void swap(int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    int start;
    int middle;
    int end;

    public MergeForkJoinSort(int start, int end) {
        this.start = start;
        this.end = end;
        middle = (end + start) / 2;
    }

    @Override
    public Void call() {
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
}


package concurrent.sort.mergeSort;

import concurrent.sort.Sorts;

public class RecursiveMergeSort {

    static int COUNT = 100000000;
    static int[] arr;
    static int[] buffer;

    static {
        arr = Sorts.generate(COUNT);
        buffer = new int[arr.length];
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        mergeSort(new Arr(0, arr.length));

        System.out.println("RecursiveMergeSort time = " + (System.currentTimeMillis() - start) + " mc");
//        Sorts.printSortedArr(arr);
    }

    static Arr mergeSort(Arr a) {
        if (a.end - a.start < 2) return a;

        int middle = (a.start + a.end) / 2;

        Arr a1 = mergeSort(new Arr(a.start, middle));
        Arr a2 = mergeSort(new Arr(middle, a.end));


        return merge(a1, a2);
    }

    static Arr merge(Arr a, Arr b) {
        int i = a.start;
        int j = b.start;
        int k = i;
        for (; i < a.end && j < b.end; k++) {
            buffer[k] = Math.min(arr[i], arr[j]);
            int i1 = (buffer[k] == arr[i]) ? i++ : j++;
        }
        for (; i < a.end; i++, k++) {
            buffer[k] = arr[i];
        }
        for (; j < b.end; j++, k++) {
            buffer[k] = arr[j];
        }
        System.arraycopy(buffer, a.start, arr, a.start, b.end - a.start);
        return new Arr(a.start, b.end);
    }

    static class Arr {
        int start;
        int end;

        public Arr(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }
}

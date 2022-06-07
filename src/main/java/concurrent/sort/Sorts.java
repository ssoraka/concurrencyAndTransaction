package concurrent.sort;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Stream;

public abstract class Sorts {
    static Random RANDOM = new Random();

    public static int[] generate(int count) {
        return Stream.generate(() -> RANDOM.nextInt(count)).limit(count).mapToInt(i -> i).toArray();
    }

    public static void printSortedArr(int[] arr) {
        System.out.println(Arrays.toString(arr));
        for (int i = 1; i < arr.length; i++) {
            if (arr[i - 1] > arr[i]) throw new RuntimeException(arr[i - 1] + " " + arr[i]);
        }
    }

//    QuickForkJoinSort time = 6557 mc
//    RecursiveMergeSort time = 17438 mc
//    MergeForkJoinSort time = 30212 mc   ???
}

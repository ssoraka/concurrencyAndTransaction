package cov;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        Integer a = 1;

        List<Integer> arr = new ArrayList<>();
        arr.add(a);
        List<Long> arrD = new ArrayList<>();
        arrD.add(2L);

        List<? extends Number> arrN1 = arr;
        List<? super Integer> arrN2 = arr;


        arrN2.add(a);
        arrN1 = arrD;
        System.out.println(arrN1.get(0));
        System.out.println(arrN2.get(0));
    }
}

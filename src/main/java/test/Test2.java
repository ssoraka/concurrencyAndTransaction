package test;

import java.util.Arrays;
import java.util.List;

public class Test2 {
    public static void main(String[] args) {
//        List<String> test = Arrays.asList("1", "22", "333", "1", "22", "333","1", "22", "333");
//        test.stream()
//                .parallel()
//                .filter(s -> s.length() != 2 )
//                .forEach(Test2::print);
//        System.out.println("123123123");


        Thread main = Thread.currentThread();

        Runnable runner = new Runnable() {
            @Override
            public void run() {
                StackTraceElement[] stackTrace = Thread.getAllStackTraces().get(main);
                for (StackTraceElement trace : stackTrace) {
                    System.out.println(trace);
                }
            }
        };

        I1 func = runner::run;

//        I1 func = () -> {
//            StackTraceElement[] stackTrace = Thread.getAllStackTraces().get(main);
//            for (StackTraceElement trace : stackTrace) {
//                System.out.println(trace);
//            }
//        };
        I1 finalFunc = func;
        I2 func2 = () -> finalFunc.f1();
//        I2 func2 = func::f1;
        func = func2::f2;
        func2 = func::f1;
        func = func2::f2;
        func2 = func::f1;
        func = func2::f2;
        func2 = func::f1;
        func = func2::f2;
        func2 = func::f1;
        func = func2::f2;
        func2 = func::f1;
        func = func2::f2;
        func2 = func::f1;
        func = func2::f2;

        func.f1();
    }
}

interface I1 {
    void f1();
}

interface I2 {
    void f2();
}
package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test3 implements Runnable{

    static int value = 0;

    public static void main(String[] args) {
        // все статические синхронизированные методы синхронизируются по классу
        // нестатические - по объекту
        List<Thread> threads = Arrays.asList(
            new Thread(() -> {
                for (int i = 0; i < 1000000; i++) {
                    Test3.inc();
                }
            }),
            new Thread(() -> {
                for (int i = 0; i < 1000000; i++) {
                    Test3.dec();
                }
            })
        );

        threads.forEach(thread -> thread.start());
        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (Exception e){}
        } );
        System.out.println(value);
    }

    static synchronized void inc() {
        value++;
    }

    static synchronized void dec() {
        value--;
    }

    @Override
    public void run() {

    }
}

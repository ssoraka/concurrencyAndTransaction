package test;

import java.util.HashMap;
import java.util.Map;

public class ThreadEx {

    static Map<String, Throwable> map;

    public static void main(String[] args) {

        map = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            createWithException("thread-" + i).start();
        }
        try {
            Thread.sleep(2000);
        } catch (Exception e) {}
        System.out.println(map);


        map = new HashMap<>();
        ThreadGroup group = new ThreadGroup("group");
        for (int i = 0; i < 10; i++) {
            createWithWait(group, "thread-" + i).start();
        }
        group.interrupt(); //?
        try {
            Thread.sleep(2000);
        } catch (Exception e) {}
        System.out.println(map);

    }

    private static Thread createWithException(String name) {
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {}

            if (true)
                throw new RuntimeException(name + " error");
        });
        thread.setName(name);
        thread.setUncaughtExceptionHandler((t, e) -> map.put(t.getName(), e));
        return thread;
    }

    private static Thread createWithWait(ThreadGroup group, String name) {
        Thread thread = new Thread(group, () -> {
            try {
                Thread.currentThread().wait();
            } catch (InterruptedException e) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("освобождаем ресурсы");
                }
            }
        });
        thread.setName(name);
        thread.setUncaughtExceptionHandler((t, e) -> map.put(t.getName(), e));

        return thread;
    }
}

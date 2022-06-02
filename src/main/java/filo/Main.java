package filo;

import java.util.ArrayList;
import java.util.HashSet;

public class Main {


    public static void main(String[] args) {

//        Arrays.stream(args).forEach(System.out::println);
//
//        for (Map.Entry<String, String> e : System.getenv().entrySet()) {
//            System.out.println(e.getKey() + " " + e.getValue());
//        }
//
//        System.exit(1);

        Object key1 = new Object();
        Object key2 = new Object();

        Filo f1 = new Filo("f1", key1, key2);
        Filo f2 = new Filo("f2", key2, key1);

        ArrayList<Thread> threads = new ArrayList<>();
        threads.add(start(f1, "f1"));
        threads.add(start(f2, "f2"));

        int i = 10;

        RunnableWithException.silent(() -> threads.get(0).join());
        RunnableWithException.silent(() -> threads.get(1).join());
    }

    static Thread start(Filo f, String name) {
        Thread thread = new Thread(f);
        thread.setName(name);
        thread.setDaemon(true);
        thread.start();
        return thread;
    }
}

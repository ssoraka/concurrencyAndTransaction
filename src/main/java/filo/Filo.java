package filo;

import java.util.*;

public class Filo implements Runnable{

    Object left;
    Object right;
    String name;
    long live = 20;

    Random random = new Random();

    public Filo(String name, Object left, Object right) {
        this.name = name;

        Set<Object> set = new HashSet<>();
        set.add(left);
        set.add(right);
        Iterator<Object> iterator = set.stream().iterator();
        this.left = iterator.next();
        this.right = iterator.next();
    }

    @Override
    public void run() {
        live = System.currentTimeMillis();
        while (true) {
            synchronized (left) {
                checkLive();
                say("get left");

                RunnableWithException.silent(() -> Thread.sleep(random.nextInt(1000)));
                synchronized (right) {
                    checkLive();
                    say("get right");
                    eat();
                    RunnableWithException.silent(() -> Thread.sleep(random.nextInt(1000)));
                }

                say("throw right");
                RunnableWithException.silent(() -> Thread.sleep(random.nextInt(1000)));
            }
            say("throw left");

        }
    }

    void checkLive() {
        if (live < System.currentTimeMillis() - 5000) {
            say("dead");
            System.exit(0);
        } else {
            say(String.format("still live %d", System.currentTimeMillis() - live));
        }
    }

    void eat() {
        live = System.currentTimeMillis();
        say("eat");
    }

    void say(String text) {
        System.out.println(name + " " + text);
    }
}

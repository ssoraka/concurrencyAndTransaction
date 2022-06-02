package filo;

import java.util.*;

public class Filo implements Runnable{

    static Comparator<Object> comparator = Comparator.comparingInt(Object::hashCode);

    Object left;
    Object right;
    String name;
    long live = 20;

    Random random = new Random();

    public Filo(String name, Object first, Object second) {
        this.name = name;

        if (comparator.compare(first, second) == 0) throw new RuntimeException("2 одинаковых вилки в одни руки");

        this.left = comparator.compare(first, second) > 0 ? first : second;
        this.right = comparator.compare(first, second) > 0 ? second : first;
    }

    public String getName() {
        return name;
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

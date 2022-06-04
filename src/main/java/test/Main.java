package test;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.concurrent.locks.*;

public class Main {
    public static void main(String[] args) {
        Main m = new Main("1");
        Main m2 = new Main("2");

        Main m3 = m.concat(m2);

//        m.test1();
        Optional<Object> empty = Optional.empty();

        System.out.println("new".hashCode());

        System.out.println(Runtime.getRuntime().freeMemory() / 8 /1024/1024 + " " + Runtime.getRuntime().totalMemory() / 8/1024/1024 + " " + Runtime.getRuntime().maxMemory() / 8 / 1024/1024);
        ReadWriteLock lock = new ReentrantReadWriteLock();
    }


    private String str;

    public Main(String str) {
        this.str = str;
    }

    Main concat(Main m2) {
        return new Main(str + m2.str);
    }



    public static void trew(Main m, int i) {
        m.str = "asD";
    }

    public static int test2(String i) {
        if ("1".equals(i)) {
            return -1;
        } else if ("2".equals(i)) {
            return -2;
        } else if ("3".equals(i)) {
            return -3;
        } else {
            return 0;
        }
    }
}

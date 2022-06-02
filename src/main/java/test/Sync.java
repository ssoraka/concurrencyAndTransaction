package test;

public class Sync {

    public static boolean flag = false;

    public static void main(String[] args) {
        Object key = new Object();

        new Thread(() -> {
            synchronized (key) {
                System.out.println("1");
                Sync.flag = true;

                try {
                    key.wait();
                } catch (Exception e) {}
                System.out.println("2");
            }
        }).start();

//        while (!flag);

        System.out.println("3");
        synchronized (key) {
            System.out.println("4");
            key.notifyAll();
//            try {
//                key.wait();
//            } catch (Exception e) {}
        }

        System.out.println("5");
    }
}

package gc;

public class Main {
    int i = 10;

    @Override
    protected void finalize() throws Throwable {
        System.out.println("haha");
        Thread.sleep(1000);
    }

    public static void main(String[] args) {
        int i = 0;
        while (true) {
            Main main = new Main();
            main.i = 1;
            System.out.println(i);
            i++;
        }
    }
}

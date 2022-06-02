package concurrent;

import java.util.Random;
import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ExchangerTest extends Thread {
    public static void main(String[] args) {
        Exchanger<String> exchanger = new Exchanger<>();

        new ExchangerTest("Dima", exchanger).start();
        new ExchangerTest("Roma", exchanger).start();
        new ExchangerTest("Valera", exchanger).start();
    }

    String name;
    Exchanger<String> exchanger;

    public ExchangerTest(String name, Exchanger<String> exchanger) {
        this.name = name;
        this.exchanger = exchanger;
    }

    @Override
    public void run() {
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            try {
                Thread.sleep(random.nextInt(1000));
                System.out.println(name + " inner:" + exchanger.exchange(name + " message: " + random.nextInt(1000), 3, TimeUnit.SECONDS));
            } catch (TimeoutException e) {
                System.out.println(name + " have not message");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

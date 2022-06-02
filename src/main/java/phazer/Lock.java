package phazer;

import javafx.animation.ScaleTransition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Lock {
    static ReentrantLock lock = new ReentrantLock();
    static Condition notEmpty = lock.newCondition();
    static Condition notFull = lock.newCondition();


    public static void main(String[] args) {
        new Thread(() -> {
            int i = 0;
            while (true) {

                lock.lock();
                try {
                    notFull.await();
                    System.out.println("put " + i);
                    notEmpty.signal();
                } catch (Exception e) {
                } finally {
                    lock.unlock();
                }
                i++;
            }
        }).start();

        new Thread(() -> {
            int i = 0;
            while (true) {
                lock.lock();
                try {
                    notEmpty.await();
                    System.out.println("get " + i);
                    notFull.signal();
                } catch (Exception e) {
                } finally {
                    lock.unlock();
                }
                i++;
            }
        }).start();


        while (true) {
            if (lock.tryLock()) {
//                lock.lock();
                try {
                    notFull.signal();
                } catch (Exception e) {
                } finally {
                    lock.unlock();
                }
            }

            try {
                Thread.sleep(1000);
            } catch (Exception e) {}
        }


    }



}

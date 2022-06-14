package concurrent.transaction.account;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Account {
    private static final AtomicInteger COUNTER = new AtomicInteger(0);

    int id;
    int score;
    Lock lock = new ReentrantLock();

    public Account(int score) {
        id = COUNTER.getAndIncrement();
        this.score = score;
    }

    public boolean has(int count) {
        return score >= count;
    }

    public void add(int num) {
        score += num;
    }

    public void sub(int num) {
        score -= num;
    }

    public void lock() {
        lock.lock();
    }

    public void unlock() {
        lock.unlock();
    }
}

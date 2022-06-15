package concurrent.transaction.account;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

public class TransactionService {

    List<Account> accounts;
    Random random = new Random();
    volatile boolean isTransfer = true;

    public TransactionService(List<Account> accounts) {
        Objects.requireNonNull(accounts);
        this.accounts = accounts;
    }

    public void setTransfer(boolean transfer) {
        isTransfer = transfer;
    }

    public void randomTransfer() {
        if (!isTransfer) return;

        Account from = accounts.get(random.nextInt(accounts.size()));
        Account to = accounts.get(random.nextInt(accounts.size()));
        transfer(from, to, random.nextInt(50));
    }

    private void transfer(Account from, Account to, int count) {
        Account first = from.id < to.id ? from : to;
        Account second = from.id < to.id ? to : from;

        try {
            first.lock();
            if (first == from && !from.has(count)) return ;
            try {
                second.lock();
                if (from.has(count)) {
                    from.sub(count);
                    to.add(count);
                }
            } finally {
                second.unlock();
            }

        } finally {
            first.unlock();
        }
    }

    public void printScoreSum() {
        lockOrPrint(0, 0);
    }

    private void lockOrPrint(int num, int sum) {
        if (num >= accounts.size()) {
            System.out.print("sum=" + sum + " ");
            accounts.forEach(a -> System.out.print(a.score + " "));
            System.out.println();
        } else {
            try {
                accounts.get(num).lock();
                lockOrPrint(num + 1, sum + accounts.get(num).score);
            } finally {
                accounts.get(num).unlock();
            }
        }


    }
}

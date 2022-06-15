package concurrent.transaction.account;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Account> accounts = Arrays.asList(
                new Account(500), new Account(0),
                new Account(500), new Account(0),
                new Account(500), new Account(0),
                new Account(500), new Account(0),
                new Account(500), new Account(0),
                new Account(500), new Account(0),
                new Account(500), new Account(0),
                new Account(500), new Account(0),
                new Account(500), new Account(0)
        );


        TransactionService transactionService = new TransactionService(accounts);

        for (int i = 0; i < 16; i++) {
            new Thread(() -> {
                while (true) {
                    transactionService.randomTransfer();
                }
            }, "broker-" + i).start();
        }

        new Thread(() -> {
            while (true) {
                transactionService.setTransfer(false);
                try {
                    Thread.sleep(1000l);
                } catch (Exception e) {}
                transactionService.printScoreSum();
                transactionService.setTransfer(true);
                try {
                    Thread.sleep(3000l);
                } catch (Exception e) {}
            }
        }, "checker").start();

    }

}

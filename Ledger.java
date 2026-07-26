package model;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Ledger {

    private Map<Integer, Account> accounts;
    private AtomicInteger accountCounter;

    public Ledger() {
        accounts = new HashMap<>();
        accountCounter = new AtomicInteger(1000);
    }

    public Account createAccount(String owner, int initialBalance) {
        int number = accountCounter.getAndIncrement();
        Account acc = new Account(number, owner, initialBalance);
        accounts.put(number, acc);
        return acc;
    }



    public Collection<Account> getAllAccounts() {
        return accounts.values();
    }

    public Account getAccount(int number) {
        return accounts.get(number);
    }

    public boolean newTransaction(Transaction t) {
        Account from = t.getDebitAccount();
        Account to = t.getCreditAccount();
        int amount = t.getAmount();

        if (from == null || to == null) return false;
        if (amount <= 0) return false;

        if (!from.withdraw(amount)) {
            return false;
        }

        to.deposit(amount);

        from.addTransactionToHistory(t);
        to.addTransactionToHistory(t);

        return true;
    }

    public List<Account> getAccounts() {
        return new ArrayList<>(accounts.values());
    }

    public void addAccount(Account newAcc) {
        accounts.put(newAcc.getNumber(), newAcc);
    }

    public void deleteAccount(int number) {
        accounts.remove(number);
    }
}

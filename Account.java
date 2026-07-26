package model;

import java.util.ArrayList;
import java.util.List;

public class Account {

    private int number;
    private String owner;
    private int overdraftLimit;
    private int balance;
    private List<Transaction> history;

    public Account(int number, String owner, int balance) {
        this.number = number;
        this.owner = owner;
        this.balance = balance;

        this.history = new ArrayList<>();
    }




    public int getNumber() { return number; }
    public String getOwner() { return owner; }
    public int getBalance() { return balance; }
    public int getOverdraftLimit() { return overdraftLimit; }

    public void deposit(int amount) {
        if (amount > 0) balance += amount;
    }

    public boolean withdraw(int amount) {
        if (amount <= 0) return false;
        if (balance - amount < -overdraftLimit) return false;
        balance -= amount;
        return true;
    }

    public void addTransactionToHistory(Transaction t) {
        if (t != null) history.add(t);
    }

    public List<Transaction> getHistory() {
        return new ArrayList<>(history);
    }

    public boolean addTransaction(Transaction t) {
        return false;
    }

    public String toString() {
        return "حساب " + number + " - " + owner + " - موجودی: " + balance + " تومان";
    }
}

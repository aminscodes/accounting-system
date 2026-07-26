package model;

public class Debit extends Transaction {
    public Debit(Account from, Account to, int amount, String desc) {
        super(from, to, amount, desc);
    }
}

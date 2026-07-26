package model;

public class Credit extends Transaction {
    public Credit(Account from, Account to, int amount, String desc) {
        super(from, to, amount, desc);
    }
}

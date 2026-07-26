package model;

public class Transaction {
    protected Account debitAccount;
    protected Account creditAccount;
    protected int amount;
    protected String description;
    protected long timestamp;

    public Transaction(Account debitAccount, Account creditAccount, int amount, String description) {
        this.debitAccount = debitAccount;
        this.creditAccount = creditAccount;
        this.amount = amount;
        this.description = description;
        this.timestamp = System.currentTimeMillis();
    }

    public Account getDebitAccount() { return debitAccount; }
    public Account getCreditAccount() { return creditAccount; }
    public int getAmount() { return amount; }
    public String getDescription() { return description; }
    public long getTimestamp() { return timestamp; }

    public String toString() {
        return "برداشت از حساب " + debitAccount.getNumber() +
                " و واریز به حساب " + creditAccount.getNumber() +
                " | مبلغ: " + amount +
                " | توضیح: " + description;
    }
}

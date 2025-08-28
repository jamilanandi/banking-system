public abstract class Account {
    protected String accountNumber;
    protected double balance;
    protected String branch;

    public Account(String accountNumber, double balance, String branch) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.branch = branch;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited BWP " + amount + " into " + getAccountType());
        }
    }

    public abstract void withdraw(double amount);
    public abstract String getAccountType();

    public double getBalance() {
        return balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
}

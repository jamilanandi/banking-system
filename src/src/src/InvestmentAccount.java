package banking.model;

public class InvestmentAccount extends Account {
    private static final double MIN_OPENING_DEPOSIT = 500.00;

    public InvestmentAccount(String accountNumber, Customer customer, double initialDeposit) {
        super(accountNumber, customer, initialDeposit);
        if (initialDeposit < MIN_OPENING_DEPOSIT) {
            throw new IllegalArgumentException("Minimum opening deposit for Investment Account is BWP " + MIN_OPENING_DEPOSIT);
        }
        this.monthlyInterestRate = 0.001; // 0.1% monthly interest as example
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            this.balance -= amount;
            addTransaction("WITHDRAWAL", amount);
            System.out.println("Withdrawn: BWP " + amount + " | New Balance: BWP " + this.balance);
            return true;
        } else {
            System.out.println("Insufficient funds or invalid amount for withdrawal");
            return false;
        }
    }

    public static double getMinOpeningDeposit() {
        return MIN_OPENING_DEPOSIT;
    }

    @Override
    public String toString() {
        return "InvestmentAccount{" +
                "accountNumber='" + accountNumber + '\'' +
                ", balance=" + balance +
                ", interestRate=" + (monthlyInterestRate * 100) + "%" +
                '}';
    }
}

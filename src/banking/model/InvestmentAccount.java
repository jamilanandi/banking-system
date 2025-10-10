package banking.model;

public class InvestmentAccount extends Account {
    private static final double MIN_OPENING_DEPOSIT = 500.00;

    public InvestmentAccount(String accountNumber, Customer customer, double initialDeposit) {
        super(accountNumber, customer, initialDeposit);
        if (initialDeposit < MIN_OPENING_DEPOSIT) {
            throw new IllegalArgumentException("Minimum opening deposit for Investment Account is BWP " + MIN_OPENING_DEPOSIT);
        }
        // Investment accounts earn monthly interest (rate can be customized)
        this.monthlyInterestRate = 0.0015; // 0.15% monthly interest as example
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            this.balance -= amount;
            addTransaction("WITHDRAWAL", amount);
            System.out.println("Withdrawn: BWP " + amount + " | New Balance: BWP " + this.balance);
            return true;
        } else {
            System.out.println("Error: Insufficient funds or invalid amount for withdrawal");
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
                ", balance=BWP " + String.format("%.2f", balance) +
                ", interestRate=" + String.format("%.3f", monthlyInterestRate * 100) + "%" +
                '}';
    }
}

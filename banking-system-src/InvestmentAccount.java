public class InvestmentAccount extends Account implements InterestBearing {
    private static final double INTEREST_RATE = 0.05; // 5%
    private static final double MIN_OPENING_DEPOSIT = 500.0;

    public InvestmentAccount(String accountNumber, double balance, String branch) {
        super(accountNumber, balance, branch);
        if (balance < MIN_OPENING_DEPOSIT) {
            throw new IllegalArgumentException("Minimum opening deposit is BWP 500.");
        }
    }

    @Override
    public void withdraw(double amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            System.out.println("Withdrew BWP " + amount + " from Investment Account.");
        } else {
            System.out.println("Invalid withdrawal.");
        }
    }

    @Override
    public double calculateInterest() {
        return balance * INTEREST_RATE;
    }

    @Override
    public String getAccountType() {
        return "Investment Account";
    }
}

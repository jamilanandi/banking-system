package banking.model;

public class SavingsAccount extends Account {
    
    public SavingsAccount(String accountNumber, Customer customer, double initialDeposit) {
        super(accountNumber, customer, initialDeposit);
        // Set interest rate based on customer type
        this.monthlyInterestRate = customer.isCompany() ? 0.00075 : 0.00025; // 0.075% or 0.025%
    }

    @Override
    public boolean withdraw(double amount) {
        // Savings account does not allow withdrawals per requirements
        System.out.println("Withdrawals are not allowed from Savings Account");
        return false;
    }

    @Override
    public String toString() {
        return "SavingsAccount{" +
                "accountNumber='" + accountNumber + '\'' +
                ", balance=" + balance +
                ", interestRate=" + (monthlyInterestRate * 100) + "%" +
                '}';
    }
}

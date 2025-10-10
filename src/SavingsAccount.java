package banking.model;

public class SavingsAccount extends Account {
    
    public SavingsAccount(String accountNumber, Customer customer, double initialDeposit) {
        super(accountNumber, customer, initialDeposit);
        // Set interest rate based on customer type as per requirements
        // Individual: 0.025% monthly, Company: 0.075% monthly
        if (customer.isCompany()) {
            this.monthlyInterestRate = 0.00075; // 0.075% for companies
        } else {
            this.monthlyInterestRate = 0.00025; // 0.025% for individuals
        }
    }

    @Override
    public boolean withdraw(double amount) {
        // Savings account does not allow withdrawals per requirements
        System.out.println("Error: Withdrawals are not allowed from Savings Account");
        return false;
    }

    @Override
    public String toString() {
        String customerType = getCustomer().isCompany() ? "Company" : "Individual";
        return "SavingsAccount{" +
                "accountNumber='" + accountNumber + '\'' +
                ", balance=BWP " + String.format("%.2f", balance) +
                ", interestRate=" + String.format("%.3f", monthlyInterestRate * 100) + "%" +
                ", customerType=" + customerType +
                '}';
    }
}

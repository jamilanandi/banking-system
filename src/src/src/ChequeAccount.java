package banking.model;

public class ChequeAccount extends Account {
    private String employerName;
    private String employerContact;

    public ChequeAccount(String accountNumber, Customer customer, double initialDeposit, 
                        String employerName, String employerContact) {
        super(accountNumber, customer, initialDeposit);
        this.employerName = employerName;
        this.employerContact = employerContact;
        this.monthlyInterestRate = 0.0; // Cheque accounts typically don't earn interest
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

    public void creditSalary(double amount) {
        if (amount > 0) {
            this.balance += amount;
            addTransaction("SALARY_CREDIT", amount);
            System.out.println("Salary credited: BWP " + amount + " | New Balance: BWP " + this.balance);
        }
    }

    // Getters
    public String getEmployerName() { return employerName; }
    public String getEmployerContact() { return employerContact; }

    @Override
    public String toString() {
        return "ChequeAccount{" +
                "accountNumber='" + accountNumber + '\'' +
                ", balance=" + balance +
                ", employer=" + employerName +
                '}';
    }
}

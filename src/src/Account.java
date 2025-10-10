package banking.model;

import java.util.ArrayList;
import java.util.List;

public abstract class Account {
    protected String accountNumber;
    protected double balance;
    protected String openingDate;
    protected Customer customer;
    protected List<Transaction> transactions;
    protected double monthlyInterestRate;
    protected boolean interestAppliedThisMonth;

    public Account(String accountNumber, Customer customer, double initialDeposit) {
        this.accountNumber = accountNumber;
        this.customer = customer;
        this.balance = initialDeposit;
        this.transactions = new ArrayList<>();
        this.openingDate = java.time.LocalDate.now().toString();
        this.interestAppliedThisMonth = false;
        
        // Record initial deposit
        if (initialDeposit > 0) {
            transactions.add(new Transaction("OPENING_DEPOSIT", initialDeposit, this.balance));
        }
    }

    // Getters
    public String getAccountNumber() { return accountNumber; }
    public double getBalance() { return balance; }
    public String getOpeningDate() { return openingDate; }
    public Customer getCustomer() { return customer; }
    public List<Transaction> getTransactions() { return transactions; }
    public double getMonthlyInterestRate() { return monthlyInterestRate; }
    public boolean isInterestAppliedThisMonth() { return interestAppliedThisMonth; }

    // Core business methods
    public void deposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
            transactions.add(new Transaction("DEPOSIT", amount, this.balance));
            System.out.println("Deposited: BWP " + amount + " | New Balance: BWP " + this.balance);
        }
    }

    public abstract boolean withdraw(double amount);

    /**
     * Calculate monthly interest based on current balance and account type
     * @return calculated interest amount
     */
    public double calculateInterest() {
        return balance * monthlyInterestRate;
    }

    /**
     * Apply monthly interest to the account balance
     * This should be called once per month (e.g., by a monthly scheduler)
     */
    public void applyMonthlyInterest() {
        if (monthlyInterestRate > 0) {
            double interest = calculateInterest();
            if (interest > 0) {
                this.balance += interest;
                this.interestAppliedThisMonth = true;
                transactions.add(new Transaction("INTEREST", interest, this.balance));
                System.out.println("Interest applied: BWP " + String.format("%.2f", interest) + 
                                 " | New Balance: BWP " + String.format("%.2f", this.balance));
            }
        }
    }

    /**
     * Reset the monthly interest flag (should be called at the start of each month)
     */
    public void resetMonthlyInterestFlag() {
        this.interestAppliedThisMonth = false;
    }

    protected void addTransaction(String type, double amount) {
        transactions.add(new Transaction(type, amount, this.balance));
    }
}

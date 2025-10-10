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

    public Account(String accountNumber, Customer customer, double initialDeposit) {
        this.accountNumber = accountNumber;
        this.customer = customer;
        this.balance = initialDeposit;
        this.transactions = new ArrayList<>();
        this.openingDate = java.time.LocalDate.now().toString();
        
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

    // Core business methods
    public void deposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
            transactions.add(new Transaction("DEPOSIT", amount, this.balance));
            System.out.println("Deposited: BWP " + amount + " | New Balance: BWP " + this.balance);
        }
    }

    public abstract boolean withdraw(double amount);

    public double calculateInterest() {
        double interest = balance * monthlyInterestRate;
        return interest;
    }

    public void applyMonthlyInterest() {
        double interest = calculateInterest();
        if (interest > 0) {
            this.balance += interest;
            transactions.add(new Transaction("INTEREST", interest, this.balance));
        }
    }

    protected void addTransaction(String type, double amount) {
        transactions.add(new Transaction(type, amount, this.balance));
    }
}

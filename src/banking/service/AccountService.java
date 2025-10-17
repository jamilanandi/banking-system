package banking.service;

import banking.model.*;
import java.util.*;

public class AccountService {
    private Map<String, Customer> customers;
    private Map<String, Account> accounts;
    private String currentCustomerId;
    
    public AccountService() {
        this.customers = new HashMap<>();
        this.accounts = new HashMap<>();
        initializeSampleData();
    }
    
    private void initializeSampleData() {
        // Create sample customers
        Customer individual = new Customer("CUST001", "John", "Doe", "123 Main St", "john@email.com");
        Customer company = new Customer("CUST002", "ABC Corp", "456 Business Ave", "info@abccorp.com");
        
        customers.put(individual.getCustomerId(), individual);
        customers.put(company.getCustomerId(), company);
        
        // Create sample accounts
        SavingsAccount johnSavings = new SavingsAccount("SAV001", individual, 1000.00);
        ChequeAccount johnCheque = new ChequeAccount("CHQ001", individual, 500.00, "Tech Inc", "hr@tech.com");
        InvestmentAccount abcInvestment = new InvestmentAccount("INV001", company, 5000.00);
        SavingsAccount companySavings = new SavingsAccount("SAV002", company, 10000.00);
        
        // Add accounts to customers
        individual.addAccount(johnSavings);
        individual.addAccount(johnCheque);
        company.addAccount(abcInvestment);
        company.addAccount(companySavings);
        
        // Store all accounts
        accounts.put(johnSavings.getAccountNumber(), johnSavings);
        accounts.put(johnCheque.getAccountNumber(), johnCheque);
        accounts.put(abcInvestment.getAccountNumber(), abcInvestment);
        accounts.put(companySavings.getAccountNumber(), companySavings);
    }
    
    // Authentication
    public boolean authenticateCustomer(String customerId, String password) {
        // Simple authentication - in real app, use proper password hashing
        Customer customer = customers.get(customerId);
        return customer != null && customer.login(password);
    }
    
    public void setCurrentCustomer(String customerId) {
        this.currentCustomerId = customerId;
    }
    
    public Customer getCurrentCustomer() {
        return customers.get(currentCustomerId);
    }
    
    // Account operations
    public List<Account> getCustomerAccounts() {
        Customer customer = getCurrentCustomer();
        return customer != null ? customer.getAccounts() : new ArrayList<>();
    }
    
    public Account getAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }
    
    public boolean deposit(String accountNumber, double amount) {
        Account account = accounts.get(accountNumber);
        if (account != null && amount > 0) {
            account.deposit(amount);
            return true;
        }
        return false;
    }
    
    public boolean withdraw(String accountNumber, double amount) {
        Account account = accounts.get(accountNumber);
        if (account != null && amount > 0) {
            return account.withdraw(amount);
        }
        return false;
    }
    
    public double getAccountBalance(String accountNumber) {
        Account account = accounts.get(accountNumber);
        return account != null ? account.getBalance() : 0.0;
    }
    
    public double getTotalBalance() {
        return getCustomerAccounts().stream()
                .mapToDouble(Account::getBalance)
                .sum();
    }
    
    // Account creation
    public boolean createSavingsAccount(double initialDeposit) {
        try {
            Customer customer = getCurrentCustomer();
            String accountNumber = "SAV" + (accounts.size() + 1);
            SavingsAccount newAccount = new SavingsAccount(accountNumber, customer, initialDeposit);
            customer.addAccount(newAccount);
            accounts.put(accountNumber, newAccount);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean createInvestmentAccount(double initialDeposit) {
        try {
            if (initialDeposit < InvestmentAccount.getMinOpeningDeposit()) {
                return false;
            }
            Customer customer = getCurrentCustomer();
            String accountNumber = "INV" + (accounts.size() + 1);
            InvestmentAccount newAccount = new InvestmentAccount(accountNumber, customer, initialDeposit);
            customer.addAccount(newAccount);
            accounts.put(accountNumber, newAccount);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean createChequeAccount(double initialDeposit, String employerName, String employerContact) {
        try {
            Customer customer = getCurrentCustomer();
            String accountNumber = "CHQ" + (accounts.size() + 1);
            ChequeAccount newAccount = new ChequeAccount(accountNumber, customer, initialDeposit, 
                                                       employerName, employerContact);
            customer.addAccount(newAccount);
            accounts.put(accountNumber, newAccount);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

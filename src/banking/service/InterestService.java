package banking.service;

import banking.model.Account;
import java.util.List;

public class InterestService {
    private AccountService accountService;
    
    public InterestService(AccountService accountService) {
        this.accountService = accountService;
    }
    
    /**
     * Apply monthly interest to all accounts of current customer
     */
    public void applyMonthlyInterestToCustomer() {
        List<Account> accounts = accountService.getCustomerAccounts();
        applyMonthlyInterestToAll(accounts);
    }
    
    /**
     * Apply monthly interest to specific accounts
     */
    public void applyMonthlyInterestToAll(List<Account> accounts) {
        System.out.println("\n=== APPLYING MONTHLY INTEREST ===");
        int accountsProcessed = 0;
        double totalInterestPaid = 0;
        
        for (Account account : accounts) {
            if (!account.isInterestAppliedThisMonth()) {
                double previousBalance = account.getBalance();
                account.applyMonthlyInterest();
                double interestPaid = account.getBalance() - previousBalance;
                
                if (interestPaid > 0) {
                    accountsProcessed++;
                    totalInterestPaid += interestPaid;
                }
            }
        }
        
        System.out.println("Monthly interest applied to " + accountsProcessed + " accounts");
        System.out.println("Total interest paid: BWP " + String.format("%.2f", totalInterestPaid));
        System.out.println("================================\n");
    }
    
    /**
     * Reset monthly interest flags for all customer accounts
     */
    public void resetMonthlyInterestFlags() {
        accountService.getCustomerAccounts().forEach(Account::resetMonthlyInterestFlag);
        System.out.println("Monthly interest flags reset for all accounts");
    }
    
    /**
     * Calculate projected annual interest for an account
     */
    public double calculateProjectedAnnualInterest(String accountNumber) {
        Account account = accountService.getAccount(accountNumber);
        if (account != null) {
            double monthlyRate = account.getMonthlyInterestRate();
            double projectedBalance = account.getBalance() * Math.pow(1 + monthlyRate, 12);
            return projectedBalance - account.getBalance();
        }
        return 0.0;
    }
    
    /**
     * Get total interest earned by customer across all accounts
     */
    public double getTotalInterestEarned() {
        return accountService.getCustomerAccounts().stream()
                .flatMap(account -> account.getTransactions().stream())
                .filter(transaction -> "INTEREST".equals(transaction.getType()))
                .mapToDouble(Transaction::getAmount)
                .sum();
    }
}

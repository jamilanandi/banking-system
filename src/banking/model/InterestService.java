package banking.service;

import banking.model.Account;
import java.util.List;

public class InterestService {
    
    /**
     * Apply monthly interest to all accounts in the system
     * This should be called by a monthly scheduler
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
     * Reset monthly interest flags for all accounts
     * This should be called at the beginning of each month
     */
    public void resetMonthlyInterestFlags(List<Account> accounts) {
        for (Account account : accounts) {
            account.resetMonthlyInterestFlag();
        }
        System.out.println("Monthly interest flags reset for all accounts");
    }
    
    /**
     * Calculate projected annual interest for an account
     */
    public double calculateProjectedAnnualInterest(Account account) {
        // Compound interest calculation: A = P(1 + r)^12
        double monthlyRate = account.getMonthlyInterestRate();
        double projectedBalance = account.getBalance() * Math.pow(1 + monthlyRate, 12);
        return projectedBalance - account.getBalance();
    }
}

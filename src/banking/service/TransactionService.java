package banking.service;

import banking.model.*;
import java.util.*;
import java.util.stream.Collectors;

public class TransactionService {
    private AccountService accountService;
    
    public TransactionService(AccountService accountService) {
        this.accountService = accountService;
    }
    
    public List<Transaction> getAccountTransactions(String accountNumber) {
        Account account = accountService.getAccount(accountNumber);
        return account != null ? account.getTransactions() : new ArrayList<>();
    }
    
    public List<Transaction> getAllCustomerTransactions() {
        return accountService.getCustomerAccounts().stream()
                .flatMap(account -> account.getTransactions().stream())
                .sorted((t1, t2) -> t2.getTimestamp().compareTo(t1.getTimestamp()))
                .collect(Collectors.toList());
    }
    
    public List<Transaction> getRecentTransactions(int count) {
        return getAllCustomerTransactions().stream()
                .limit(count)
                .collect(Collectors.toList());
    }
    
    public List<Transaction> getTransactionsByType(String type) {
        return getAllCustomerTransactions().stream()
                .filter(transaction -> transaction.getType().equalsIgnoreCase(type))
                .collect(Collectors.toList());
    }
    
    public double getTotalDeposits() {
        return getTransactionsByType("DEPOSIT").stream()
                .mapToDouble(Transaction::getAmount)
                .sum();
    }
    
    public double getTotalWithdrawals() {
        return getTransactionsByType("WITHDRAWAL").stream()
                .mapToDouble(Transaction::getAmount)
                .sum();
    }
}

package banking.service;

public class BankingController {
    private AccountService accountService;
    private TransactionService transactionService;
    private InterestService interestService;
    
    public BankingController() {
        this.accountService = new AccountService();
        this.transactionService = new TransactionService(accountService);
        this.interestService = new InterestService(accountService);
    }
    
    // Authentication
    public boolean login(String customerId, String password) {
        boolean authenticated = accountService.authenticateCustomer(customerId, password);
        if (authenticated) {
            accountService.setCurrentCustomer(customerId);
        }
        return authenticated;
    }
    
    public void logout() {
        accountService.setCurrentCustomer(null);
    }
    
    // Getters for services
    public AccountService getAccountService() { return accountService; }
    public TransactionService getTransactionService() { return transactionService; }
    public InterestService getInterestService() { return interestService; }
}

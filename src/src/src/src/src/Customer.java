import java.util.ArrayList;
import java.util.List;

public class Customer {
    private String firstname;
    private String surname;
    private String address;
    private List<Account> accounts;

    public Customer(String firstname, String surname, String address) {
        this.firstname = firstname;
        this.surname = surname;
        this.address = address;
        this.accounts = new ArrayList<>();
    }

    // Open account
    public void openAccount(Account account) {
        accounts.add(account);
        System.out.println(firstname + " " + surname + " opened a " + account.getAccountType());
    }

    // Deposit
    public void deposit(Account account, double amount) {
        account.deposit(amount);
    }

    // Withdraw
    public void withdraw(Account account, double amount) {
        account.withdraw(amount);
    }

    // View balance
    public void viewBalance(Account account) {
        System.out.println(account.getAccountType() + " (" + account.getAccountNumber() +
                "): BWP " + account.getBalance());
    }

    public List<Account> getAccounts() {
        return accounts;
    }
}


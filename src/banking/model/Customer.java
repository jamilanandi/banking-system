package banking.model;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private String customerId;
    private String firstName;
    private String surname;
    private String address;
    private String contactInfo;
    private boolean isCompany;
    private String companyName;
    private List<Account> accounts;

    public Customer(String customerId, String firstName, String surname, String address, String contactInfo) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.surname = surname;
        this.address = address;
        this.contactInfo = contactInfo;
        this.isCompany = false;
        this.accounts = new ArrayList<>();
    }

    public Customer(String customerId, String companyName, String address, String contactInfo) {
        this.customerId = customerId;
        this.companyName = companyName;
        this.address = address;
        this.contactInfo = contactInfo;
        this.isCompany = true;
        this.accounts = new ArrayList<>();
    }

    // Getters and setters
    public String getCustomerId() { return customerId; }
    public String getFirstName() { return firstName; }
    public String getSurname() { return surname; }
    public String getAddress() { return address; }
    public String getContactInfo() { return contactInfo; }
    public boolean isCompany() { return isCompany; }
    public String getCompanyName() { return companyName; }
    public List<Account> getAccounts() { return accounts; }

    public void addAccount(Account account) {
        this.accounts.add(account);
    }

    public boolean login(String inputPassword) {
        // Basic authentication logic - you can enhance this later
        return true; // Placeholder
    }

    public List<Account> viewAccounts() {
        return new ArrayList<>(accounts);
    }
}

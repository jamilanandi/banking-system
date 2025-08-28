public class Main {
    public static void main(String[] args) {
        // Create a customer
        Customer customer1 = new Customer("John", "Doe", "Gaborone");

        // Open a Savings Account
        SavingsAccount savings = new SavingsAccount("SAV123", 2000.0, "Main Branch");
        customer1.openAccount(savings);

        // Open an Investment Account (with minimum deposit of 500)
        InvestmentAccount investment = new InvestmentAccount("INV456", 1000.0, "Main Branch");
        customer1.openAccount(investment);

        // Open a Cheque Account
        ChequeAccount cheque = new ChequeAccount("CHQ789", 1500.0, "Main Branch",
                "XYZ Ltd", "Gaborone CBD");
        customer1.openAccount(cheque);

        // --- Demonstrations ---
        System.out.println("\n--- Initial Balances ---");
        for (Account acc : customer1.getAccounts()) {
            customer1.viewBalance(acc);
        }

        // Deposit into Savings
        System.out.println("\nDepositing 500 into Savings...");
        customer1.deposit(savings, 500);

        // Withdraw from Cheque
        System.out.println("\nWithdrawing 300 from Cheque...");
        customer1.withdraw(cheque, 300);

        // Try to Withdraw from Savings (should be blocked)
        System.out.println("\nTrying to withdraw 100 from Savings...");
        customer1.withdraw(savings, 100);

        // Calculate and show interest
        System.out.println("\n--- Interest Calculations ---");
        if (savings instanceof InterestBearing) {
            System.out.println("Savings Interest: " +
                    ((InterestBearing) savings).calculateInterest());
        }
        if (investment instanceof InterestBearing) {
            System.out.println("Investment Interest: " +
                    ((InterestBearing) investment).calculateInterest());
        }

        // Final balances
        System.out.println("\n--- Final Balances ---");
        for (Account acc : customer1.getAccounts()) {
            customer1.viewBalance(acc);
        }
    }
}


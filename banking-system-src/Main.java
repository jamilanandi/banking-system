public class Main {
    public static void main(String[] args) {
        Customer customer1 = new Customer("John", "Doe", "Gaborone");

        SavingsAccount savings = new SavingsAccount("SAV123", 2000.0, "Main Branch");
        customer1.openAccount(savings);

        InvestmentAccount investment = new InvestmentAccount("INV456", 1000.0, "Main Branch");
        customer1.openAccount(investment);

        ChequeAccount cheque = new ChequeAccount("CHQ789", 1500.0, "Main Branch",
                "XYZ Ltd", "Gaborone CBD");
        customer1.openAccount(cheque);

        System.out.println("\n--- Initial Balances ---");
        for (Account acc : customer1.getAccounts()) {
            customer1.viewBalance(acc);
        }

        System.out.println("\nDepositing 500 into Savings...");
        customer1.deposit(savings, 500);

        System.out.println("\nWithdrawing 300 from Cheque...");
        customer1.withdraw(cheque, 300);

        System.out.println("\nTrying to withdraw 100 from Savings...");
        customer1.withdraw(savings, 100);

        System.out.println("\n--- Interest Calculations ---");
        if (savings instanceof InterestBearing) {
            System.out.println("Savings Interest: " +
                    ((InterestBearing) savings).calculateInterest());
        }
        if (investment instanceof InterestBearing) {
            System.out.println("Investment Interest: " +
                    ((InterestBearing) investment).calculateInterest());
        }

        System.out.println("\n--- Final Balances ---");
        for (Account acc : customer1.getAccounts()) {
            customer1.viewBalance(acc);
        }
    }
}

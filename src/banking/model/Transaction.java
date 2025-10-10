package banking.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private String transactionId;
    private String type;
    private double amount;
    private String timestamp;
    private double finalBalance;

    public Transaction(String type, double amount, double finalBalance) {
        this.transactionId = generateTransactionId();
        this.type = type;
        this.amount = amount;
        this.finalBalance = finalBalance;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    private String generateTransactionId() {
        return "TXN" + System.currentTimeMillis() + (int)(Math.random() * 1000);
    }

    // Getters
    public String getTransactionId() { return transactionId; }
    public String getType() { return type; }
    public double getAmount() { return amount; }
    public String getTimestamp() { return timestamp; }
    public double getFinalBalance() { return finalBalance; }

    @Override
    public String toString() {
        return String.format("Transaction[%s]: %s - BWP %.2f | Balance: BWP %.2f | %s", 
                transactionId, type, amount, finalBalance, timestamp);
    }
}

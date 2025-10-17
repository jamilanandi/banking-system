package banking.view;

import banking.service.BankingController;
import banking.model.Account;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class DashboardView {
    private Stage primaryStage;
    private BankingController bankingController;
    private BorderPane view;
    private ListView<String> accountsList;
    private Label welcomeLabel;
    private Label balanceLabel;
    
    public DashboardView(Stage primaryStage, BankingController bankingController) {
        this.primaryStage = primaryStage;
        this.bankingController = bankingController;
        createView();
        updateAccountInfo();
    }
    
    private void createView() {
        // Create UI components
        welcomeLabel = new Label("Welcome");
        welcomeLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        
        balanceLabel = new Label("Total Balance: BWP 0.00");
        balanceLabel.setStyle("-fx-font-size: 16px;");
        
        accountsList = new ListView<>();
        accountsList.setPrefHeight(300);
        
        Button depositButton = new Button("Deposit");
        Button withdrawButton = new Button("Withdraw");
        Button transactionHistoryButton = new Button("Transaction History");
        Button logoutButton = new Button("Logout");
        
        // Apply consistent button styling
        String buttonStyle = "-fx-padding: 10px 20px; -fx-font-size: 14px;";
        depositButton.setStyle(buttonStyle);
        withdrawButton.setStyle(buttonStyle);
        transactionHistoryButton.setStyle(buttonStyle);
        logoutButton.setStyle(buttonStyle);
        
        // Layout
        HBox header = new HBox(20);
        header.setPadding(new Insets(20));
        header.getChildren().addAll(welcomeLabel, balanceLabel);
        
        VBox centerBox = new VBox(15);
        centerBox.setPadding(new Insets(20));
        centerBox.getChildren().addAll(
            new Label("Your Accounts:"),
            accountsList
        );
        
        HBox buttonBox = new HBox(15);
        buttonBox.setPadding(new Insets(20));
        buttonBox.getChildren().addAll(
            depositButton, withdrawButton, transactionHistoryButton, logoutButton
        );
        
        view = new BorderPane();
        view.setTop(header);
        view.setCenter(centerBox);
        view.setBottom(buttonBox);
        
        // Event handlers
        setupEventHandlers();
    }
    
    private void updateAccountInfo() {
        // Get real data from services
        String customerId = bankingController.getAccountService().getCurrentCustomer().getCustomerId();
        double totalBalance = bankingController.getAccountService().getTotalBalance();
        
        welcomeLabel.setText("Welcome, Customer " + customerId);
        balanceLabel.setText(String.format("Total Balance: BWP %.2f", totalBalance));
        
        // Update accounts list with real data
        accountsList.getItems().clear();
        for (Account account : bankingController.getAccountService().getCustomerAccounts()) {
            String accountInfo = String.format("%s - %s - BWP %.2f", 
                account.getAccountNumber(),
                account.getClass().getSimpleName().replace("Account", ""),
                account.getBalance());
            accountsList.getItems().add(accountInfo);
        }
    }
    
    private void setupEventHandlers() {
        // Deposit button - navigate to deposit view
        depositButton.setOnAction(e -> {
            AccountView accountView = new AccountView(primaryStage, bankingController, "deposit");
            primaryStage.getScene().setRoot(accountView.getView());
        });
        
        // Withdraw button - navigate to withdraw view  
        withdrawButton.setOnAction(e -> {
            AccountView accountView = new AccountView(primaryStage, bankingController, "withdraw");
            primaryStage.getScene().setRoot(accountView.getView());
        });
        
        // Transaction History button - navigate to transaction view
        transactionHistoryButton.setOnAction(e -> {
            TransactionView transactionView = new TransactionView(primaryStage, bankingController);
            primaryStage.getScene().setRoot(transactionView.getView());
        });
        
        // Logout button - return to login
        logoutButton.setOnAction(e -> {
            bankingController.logout();
            LoginView loginView = new LoginView(primaryStage);
            primaryStage.getScene().setRoot(loginView.getView());
        });
        
        // Account selection - show account details when selected
        accountsList.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                // Extract account number from the list item
                String accountNumber = newVal.split(" - ")[0];
                System.out.println("Selected account: " + accountNumber);
                
                // You could show a detailed view or enable/disable buttons based on account type
                String accountType = newVal.split(" - ")[1];
                withdrawButton.setDisable(accountType.equals("Savings")); // Disable withdraw for savings
            }
        });
    }
    
    public BorderPane getView() {
        return view;
    }
}

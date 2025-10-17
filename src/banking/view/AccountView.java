package banking.view;

import banking.service.BankingController;
import banking.model.Account;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class AccountView {
    private Stage primaryStage;
    private BankingController bankingController;
    private String operationType;
    private VBox view;
    private ComboBox<String> accountComboBox;
    private TextField amountField;
    private Label messageLabel;
    
    public AccountView(Stage primaryStage, BankingController bankingController, String operationType) {
        this.primaryStage = primaryStage;
        this.bankingController = bankingController;
        this.operationType = operationType;
        createView();
    }
    
    private void createView() {
        String title = operationType.equalsIgnoreCase("deposit") ? "Deposit Money" : "Withdraw Money";
        
        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        
        accountComboBox = new ComboBox<>();
        updateAccountComboBox();
        accountComboBox.setPromptText("Select Account");
        accountComboBox.setMaxWidth(300);
        
        amountField = new TextField();
        amountField.setPromptText("Enter amount in BWP");
        amountField.setMaxWidth(300);
        
        Button executeButton = new Button(title);
        Button backButton = new Button("Back to Dashboard");
        
        messageLabel = new Label();
        messageLabel.setStyle("-fx-text-fill: red;");
        
        // Layout
        VBox formBox = new VBox(15);
        formBox.setAlignment(javafx.geometry.Pos.CENTER);
        formBox.getChildren().addAll(
            titleLabel,
            new Label("Select Account:"),
            accountComboBox,
            new Label("Amount:"),
            amountField,
            executeButton,
            backButton,
            messageLabel
        );
        
        view = new VBox(20);
        view.setAlignment(javafx.geometry.Pos.CENTER);
        view.setPadding(new Insets(40));
        view.getChildren().addAll(formBox);
        
        // Event handlers
        setupEventHandlers();
    }
    
    private void updateAccountComboBox() {
        accountComboBox.getItems().clear();
        for (Account account : bankingController.getAccountService().getCustomerAccounts()) {
            String accountInfo = String.format("%s - %s - BWP %.2f", 
                account.getAccountNumber(),
                account.getClass().getSimpleName().replace("Account", ""),
                account.getBalance());
            accountComboBox.getItems().add(accountInfo);
        }
    }
    
    private void setupEventHandlers() {
        executeButton.setOnAction(e -> {
            String selectedItem = accountComboBox.getValue();
            String amountText = amountField.getText();
            
            // Input validation (UI logic only)
            if (selectedItem == null || selectedItem.isEmpty()) {
                messageLabel.setText("Please select an account");
                return;
            }
            
            if (amountText.isEmpty()) {
                messageLabel.setText("Please enter an amount");
                return;
            }
            
            try {
                double amount = Double.parseDouble(amountText);
                if (amount <= 0) {
                    messageLabel.setText("Amount must be positive");
                    return;
                }
                
                // Extract account number from selection
                String accountNumber = selectedItem.split(" - ")[0];
                
                // Use service for business operation
                boolean success = false;
                if ("deposit".equalsIgnoreCase(operationType)) {
                    success = bankingController.getAccountService().deposit(accountNumber, amount);
                } else if ("withdraw".equalsIgnoreCase(operationType)) {
                    success = bankingController.getAccountService().withdraw(accountNumber, amount);
                }
                
                if (success) {
                    messageLabel.setStyle("-fx-text-fill: green;");
                    messageLabel.setText(operationType + " of BWP " + 
                                       String.format("%.2f", amount) + " processed successfully!");
                    amountField.clear();
                    updateAccountComboBox(); // Refresh account balances
                } else {
                    messageLabel.setStyle("-fx-text-fill: red;");
                    messageLabel.setText(operationType + " failed. Please check account type and balance.");
                }
                
            } catch (NumberFormatException ex) {
                messageLabel.setText("Please enter a valid amount");
            }
        });
        
        backButton.setOnAction(e -> {
            DashboardView dashboardView = new DashboardView(primaryStage, bankingController);
            primaryStage.getScene().setRoot(dashboardView.getView());
        });
        
        // Enter key support
        amountField.setOnAction(e -> executeButton.fire());
    }
    
    public VBox getView() {
        return view;
    }
}

package banking.view;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class AccountView {
    private Stage primaryStage;
    private String operationType;
    private VBox view;
    
    public AccountView(Stage primaryStage, String operationType) {
        this.primaryStage = primaryStage;
        this.operationType = operationType;
        createView();
    }
    
    private void createView() {
        String title = operationType.equalsIgnoreCase("deposit") ? "Deposit Money" : "Withdraw Money";
        
        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        
        ComboBox<String> accountComboBox = new ComboBox<>();
        accountComboBox.getItems().addAll("SAV001 - Savings", "CHQ001 - Cheque", "INV001 - Investment");
        accountComboBox.setPromptText("Select Account");
        accountComboBox.setMaxWidth(300);
        
        TextField amountField = new TextField();
        amountField.setPromptText("Enter amount in BWP");
        amountField.setMaxWidth(300);
        
        Button executeButton = new Button(title);
        Button backButton = new Button("Back to Dashboard");
        
        Label messageLabel = new Label();
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
        executeButton.setOnAction(e -> {
            String selectedAccount = accountComboBox.getValue();
            String amountText = amountField.getText();
            
            // Input validation (UI logic only)
            if (selectedAccount == null || selectedAccount.isEmpty()) {
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
                
                // This would call a controller in real application
                messageLabel.setText(operationType + " of BWP " + amount + " processed successfully!");
                
            } catch (NumberFormatException ex) {
                messageLabel.setText("Please enter a valid amount");
            }
        });
        
        backButton.setOnAction(e -> {
            DashboardView dashboardView = new DashboardView(primaryStage, "CUST001");
            primaryStage.getScene().setRoot(dashboardView.getView());
        });
    }
    
    public VBox getView() {
        return view;
    }
}

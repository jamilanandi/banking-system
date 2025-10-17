package banking.view;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class DashboardView {
    private Stage primaryStage;
    private String customerId;
    private BorderPane view;
    private ListView<String> accountsList;
    private Label welcomeLabel;
    private Label balanceLabel;
    
    public DashboardView(Stage primaryStage, String customerId) {
        this.primaryStage = primaryStage;
        this.customerId = customerId;
        createView();
    }
    
    private void createView() {
        // Create UI components
        welcomeLabel = new Label("Welcome, Customer " + customerId);
        welcomeLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        
        balanceLabel = new Label("Total Balance: BWP 0.00");
        balanceLabel.setStyle("-fx-font-size: 16px;");
        
        accountsList = new ListView<>();
        accountsList.setPrefHeight(300);
        
        // Sample data - in real app, this would come from controller
        accountsList.getItems().addAll(
            "SAV001 - Savings Account - BWP 1,500.00",
            "CHQ001 - Cheque Account - BWP 2,300.50"
        );
        
        Button depositButton = new Button("Deposit");
        Button withdrawButton = new Button("Withdraw");
        Button transactionHistoryButton = new Button("Transaction History");
        Button logoutButton = new Button("Logout");
        
        // Layout
        HTop header = new HBox(20);
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
    
    private void setupEventHandlers() {
        // These would call controllers in a real application
        // For now, just demonstrate UI navigation
        
        depositButton.setOnAction(e -> {
            AccountView accountView = new AccountView(primaryStage, "deposit");
            primaryStage.getScene().setRoot(accountView.getView());
        });
        
        withdrawButton.setOnAction(e -> {
            AccountView accountView = new AccountView(primaryStage, "withdraw");
            primaryStage.getScene().setRoot(accountView.getView());
        });
        
        transactionHistoryButton.setOnAction(e -> {
            TransactionView transactionView = new TransactionView(primaryStage);
            primaryStage.getScene().setRoot(transactionView.getView());
        });
        
        logoutButton.setOnAction(e -> {
            LoginView loginView = new LoginView(primaryStage);
            primaryStage.getScene().setRoot(loginView.getView());
        });
        
        // Account selection
        accountsList.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                // Would notify controller of selection
                System.out.println("Selected account: " + newVal);
            }
        });
    }
    
    public BorderPane getView() {
        return view;
    }
}

package banking.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class LoginView {
    private Stage primaryStage;
    private VBox view;
    private TextField customerIdField;
    private PasswordField passwordField;
    private Button loginButton;
    private Label messageLabel;
    
    public LoginView(Stage primaryStage) {
        this.primaryStage = primaryStage;
        createView();
    }
    
    private void createView() {
        // Create UI components
        Label titleLabel = new Label("Banking System Login");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        
        customerIdField = new TextField();
        customerIdField.setPromptText("Enter Customer ID");
        customerIdField.setMaxWidth(250);
        
        passwordField = new PasswordField();
        passwordField.setPromptText("Enter Password");
        passwordField.setMaxWidth(250);
        
        loginButton = new Button("Login");
        loginButton.setDefaultButton(true);
        loginButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px 20px;");
        
        messageLabel = new Label();
        messageLabel.setStyle("-fx-text-fill: red;");
        
        // Layout
        VBox formBox = new VBox(15);
        formBox.setAlignment(Pos.CENTER);
        formBox.getChildren().addAll(
            titleLabel,
            new Label("Customer ID:"),
            customerIdField,
            new Label("Password:"),
            passwordField,
            loginButton,
            messageLabel
        );
        
        view = new VBox(20);
        view.setAlignment(Pos.CENTER);
        view.setPadding(new Insets(40));
        view.setStyle("-fx-background-color: #f5f5f5;");
        view.getChildren().addAll(formBox);
        
        // Event handlers (only UI logic, no business logic)
        setupEventHandlers();
    }
    
    private void setupEventHandlers() {
        loginButton.setOnAction(e -> {
            String customerId = customerIdField.getText();
            String password = passwordField.getText();
            
            // Input validation (UI logic only)
            if (customerId.isEmpty() || password.isEmpty()) {
                messageLabel.setText("Please enter both Customer ID and Password");
                return;
            }
            
            // This would call a controller in a real application
            // For now, we'll simulate successful login
            messageLabel.setText("Logging in...");
            
            // Navigate to dashboard (UI navigation only)
            DashboardView dashboardView = new DashboardView(primaryStage, customerId);
            primaryStage.getScene().setRoot(dashboardView.getView());
        });
    }
    
    public VBox getView() {
        return view;
    }
}

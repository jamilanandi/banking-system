package banking.view;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class TransactionView {
    private Stage primaryStage;
    private TableView<String> transactionTable;
    private BorderPane view;
    
    public TransactionView(Stage primaryStage) {
        this.primaryStage = primaryStage;
        createView();
    }
    
    private void createView() {
        Label titleLabel = new Label("Transaction History");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        
        // Create transaction table
        transactionTable = new TableView<>();
        
        TableColumn<String, String> dateColumn = new TableColumn<>("Date");
        TableColumn<String, String> typeColumn = new TableColumn<>("Type");
        TableColumn<String, String> amountColumn = new TableColumn<>("Amount");
        TableColumn<String, String> balanceColumn = new TableColumn<>("Balance");
        
        transactionTable.getColumns().addAll(dateColumn, typeColumn, amountColumn, balanceColumn);
        
        // Sample data
        transactionTable.getItems().addAll(
            "2024-01-15 | DEPOSIT | BWP 500.00 | BWP 1,500.00",
            "2024-01-10 | INTEREST | BWP 0.38 | BWP 1,000.38",
            "2024-01-01 | OPENING | BWP 1,000.00 | BWP 1,000.00"
        );
        
        ComboBox<String> accountFilter = new ComboBox<>();
        accountFilter.getItems().addAll("All Accounts", "SAV001 - Savings", "CHQ001 - Cheque");
        accountFilter.setValue("All Accounts");
        
        Button backButton = new Button("Back to Dashboard");
        
        // Layout
        VBox centerBox = new VBox(15);
        centerBox.setPadding(new Insets(20));
        centerBox.getChildren().addAll(
            new Label("Filter by Account:"),
            accountFilter,
            transactionTable
        );
        
        HBox bottomBox = new HBox();
        bottomBox.setPadding(new Insets(20));
        bottomBox.getChildren().add(backButton);
        
        view = new BorderPane();
        view.setTop(titleLabel);
        view.setCenter(centerBox);
        view.setBottom(bottomBox);
        BorderPane.setAlignment(titleLabel, javafx.geometry.Pos.CENTER);
        BorderPane.setMargin(titleLabel, new Insets(20));
        
        // Event handlers
        backButton.setOnAction(e -> {
            DashboardView dashboardView = new DashboardView(primaryStage, "CUST001");
            primaryStage.getScene().setRoot(dashboardView.getView());
        });
        
        accountFilter.setOnAction(e -> {
            // This would filter transactions by selected account
            String selectedAccount = accountFilter.getValue();
            System.out.println("Filtering by: " + selectedAccount);
        });
    }
    
    public BorderPane getView() {
        return view;
    }
}

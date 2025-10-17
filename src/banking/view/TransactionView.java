package banking.view;

import banking.service.BankingController;
import banking.model.Transaction;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TransactionView {
    private Stage primaryStage;
    private BankingController bankingController;
    private BorderPane view;
    private TableView<Transaction> transactionTable;
    private ObservableList<Transaction> transactionData;
    
    public TransactionView(Stage primaryStage, BankingController bankingController) {
        this.primaryStage = primaryStage;
        this.bankingController = bankingController;
        createView();
        loadTransactionData();
    }
    
    private void createView() {
        Label titleLabel = new Label("Transaction History");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        
        // Create transaction table
        transactionTable = new TableView<>();
        transactionData = FXCollections.observableArrayList();
        transactionTable.setItems(transactionData);
        
        TableColumn<Transaction, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTimestamp()));
        dateColumn.setPrefWidth(150);
        
        TableColumn<Transaction, String> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getType()));
        typeColumn.setPrefWidth(120);
        
        TableColumn<Transaction, String> amountColumn = new TableColumn<>("Amount");
        amountColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
            String.format("BWP %.2f", cellData.getValue().getAmount())));
        amountColumn.setPrefWidth(120);
        
        TableColumn<Transaction, String> balanceColumn = new TableColumn<>("Balance");
        balanceColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
            String.format("BWP %.2f", cellData.getValue().getFinalBalance())));
        balanceColumn.setPrefWidth(120);
        
        TableColumn<Transaction, String> idColumn = new TableColumn<>("Transaction ID");
        idColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTransactionId()));
        idColumn.setPrefWidth(150);
        
        transactionTable.getColumns().addAll(dateColumn, typeColumn, amountColumn, balanceColumn, idColumn);
        transactionTable.setPrefHeight(400);
        
        ComboBox<String> accountFilter = new ComboBox<>();
        accountFilter.getItems().add("All Accounts");
        // Add customer accounts to filter
        bankingController.getAccountService().getCustomerAccounts().forEach(account -> {
            accountFilter.getItems().add(account.getAccountNumber() + " - " + 
                account.getClass().getSimpleName().replace("Account", ""));
        });
        accountFilter.setValue("All Accounts");
        
        Label summaryLabel = new Label();
        updateSummaryLabel(summaryLabel);
        
        Button backButton = new Button("Back to Dashboard");
        Button refreshButton = new Button("Refresh");
        
        // Layout
        HBox filterBox = new HBox(10);
        filterBox.setPadding(new Insets(10));
        filterBox.getChildren().addAll(new Label("Filter by Account:"), accountFilter, refreshButton);
        
        VBox centerBox = new VBox(15);
        centerBox.setPadding(new Insets(20));
        centerBox.getChildren().addAll(
            filterBox,
            transactionTable,
            summaryLabel
        );
        
        HBox bottomBox = new HBox(15);
        bottomBox.setPadding(new Insets(20));
        bottomBox.getChildren().addAll(backButton);
        
        view = new BorderPane();
        view.setTop(titleLabel);
        view.setCenter(centerBox);
        view.setBottom(bottomBox);
        BorderPane.setAlignment(titleLabel, javafx.geometry.Pos.CENTER);
        BorderPane.setMargin(titleLabel, new Insets(20));
        
        // Event handlers
        setupEventHandlers(accountFilter, summaryLabel);
    }
    
    private void loadTransactionData() {
        transactionData.clear();
        transactionData.addAll(bankingController.getTransactionService().getAllCustomerTransactions());
    }
    
    private void updateSummaryLabel(Label summaryLabel) {
        double totalDeposits = bankingController.getTransactionService().getTotalDeposits();
        double totalWithdrawals = bankingController.getTransactionService().getTotalWithdrawals();
        double totalInterest = bankingController.getInterestService().getTotalInterestEarned();
        
        String summaryText = String.format(
            "Summary: Deposits: BWP %.2f | Withdrawals: BWP %.2f | Interest Earned: BWP %.2f",
            totalDeposits, totalWithdrawals, totalInterest
        );
        summaryLabel.setText(summaryText);
        summaryLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
    }
    
    private void setupEventHandlers(ComboBox<String> accountFilter, Label summaryLabel) {
        backButton.setOnAction(e -> {
            DashboardView dashboardView = new DashboardView(primaryStage, bankingController);
            primaryStage.getScene().setRoot(dashboardView.getView());
        });
        
        refreshButton.setOnAction(e -> {
            loadTransactionData();
            updateSummaryLabel(summaryLabel);
        });
        
        accountFilter.setOnAction(e -> {
            String selectedAccount = accountFilter.getValue();
            if ("All Accounts".equals(selectedAccount)) {
                loadTransactionData();
            } else {
                // Filter by specific account
                String accountNumber = selectedAccount.split(" - ")[0];
                transactionData.clear();
                transactionData.addAll(bankingController.getTransactionService().getAccountTransactions(accountNumber));
            }
            updateSummaryLabel(summaryLabel);
        });
    }
    
    public BorderPane getView() {
        return view;
    }
}

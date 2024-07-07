package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.*;

public class ATMInterface extends Application {

    private String dbUrl;
    private String dbUser;
    private String dbPassword;
    private String userId;

    private Label balanceLabel;
    private TextField amountField;
    private Button transactionHistoryButton;

    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet rs;

    public ATMInterface(String dbUrl, String dbUser, String dbPassword, String userId) {
        this.dbUrl = dbUrl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
        this.userId = userId;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("ATM Machine");

        Label amountLabel = new Label("Enter Amount:");
        amountField = new TextField();
        Button withdrawButton = new Button("Withdraw");
        Button depositButton = new Button("Deposit");
        Button balanceButton = new Button("Check Balance");
        transactionHistoryButton = new Button("Transaction History");
        balanceLabel = new Label();

        withdrawButton.setOnAction(e -> withdraw());
        depositButton.setOnAction(e -> deposit());
        balanceButton.setOnAction(e -> checkBalance());
        transactionHistoryButton.setOnAction(e -> showTransactionHistory());

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        gridPane.add(amountLabel, 0, 0);
        gridPane.add(amountField, 1, 0);
        gridPane.add(withdrawButton, 0, 1);
        gridPane.add(depositButton, 1, 1);
        gridPane.add(balanceButton, 0, 2);
        gridPane.add(transactionHistoryButton, 1, 2);
        gridPane.add(balanceLabel, 0, 3, 2, 1);

        Scene scene = new Scene(gridPane, 400, 200);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();

        connectDB();
    }

    private void connectDB() {
        try {
            conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void withdraw() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            if (amount <= 0) {
                showAlert("Invalid amount. Please enter a valid amount.");
                return;
            }

            String checkBalanceSQL = "SELECT balance FROM accounts WHERE user_id = ?";
            stmt = conn.prepareStatement(checkBalanceSQL);
            stmt.setInt(1, Integer.parseInt(userId));
            rs = stmt.executeQuery();

            if (rs.next()) {
                double currentBalance = rs.getDouble("balance");
                if (amount > currentBalance) {
                    showAlert("Insufficient balance. You cannot withdraw more than your current balance.");
                    return;
                }

                String updateBalanceSQL = "UPDATE accounts SET balance = balance - ? WHERE user_id = ? AND balance >= ?";
                stmt = conn.prepareStatement(updateBalanceSQL);
                stmt.setDouble(1, amount);
                stmt.setInt(2, Integer.parseInt(userId));
                stmt.setDouble(3, amount);

                int affectedRows = stmt.executeUpdate();
                if (affectedRows > 0) {
                    showAlert("Withdrawal successful.");
                    amountField.clear();
                } else {
                    showAlert("Withdrawal failed. Please check your balance.");
                }
            } else {
                showAlert("Failed to retrieve balance.");
            }

        } catch (NumberFormatException | SQLException e) {
            showAlert("Error: " + e.getMessage());
        }
    }

    private void deposit() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            if (amount <= 0) {
                showAlert("Invalid amount. Please enter a valid amount.");
                return;
            }

            String sql = "UPDATE accounts SET balance = balance + ? WHERE user_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setDouble(1, amount);
            stmt.setInt(2, Integer.parseInt(userId));

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                showAlert("Deposit successful.");
                amountField.clear();
            } else {
                showAlert("Deposit failed. Please try again.");
            }

        } catch (NumberFormatException | SQLException e) {
            showAlert("Error: " + e.getMessage());
        }
    }

    private void checkBalance() {
        try {
            String sql = "SELECT balance FROM accounts WHERE user_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(userId));

            rs = stmt.executeQuery();
            if (rs.next()) {
                double balance = rs.getDouble("balance");
                balanceLabel.setText("Current Balance: $" + balance);
            } else {
                showAlert("Failed to retrieve balance.");
            }

        } catch (SQLException e) {
            showAlert("Error: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
	private void showTransactionHistory() {
        Stage transactionStage = new Stage();
        transactionStage.setTitle("Transaction History");

        TableView<Transaction> transactionTable = new TableView<>();
        transactionTable.setMinWidth(400);

        TableColumn<Transaction, Integer> transactionIdCol = new TableColumn<>("Transaction ID");
        transactionIdCol.setCellValueFactory(new PropertyValueFactory<>("transactionId"));

        TableColumn<Transaction, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<Transaction, Double> amountCol = new TableColumn<>("Amount");
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));

        TableColumn<Transaction, Timestamp> timestampCol = new TableColumn<>("Timestamp");
        timestampCol.setCellValueFactory(new PropertyValueFactory<>("timestamp"));

        transactionTable.getColumns().addAll(transactionIdCol, typeCol, amountCol, timestampCol);

        try {
            String sql = "SELECT * FROM transactions WHERE account_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(userId));

            rs = stmt.executeQuery();

            while (rs.next()) {
                int transactionId = rs.getInt("transaction_id");
                String type = rs.getString("type");
                double amount = rs.getDouble("amount");
                Timestamp timestamp = rs.getTimestamp("timestamp");

                Transaction transaction = new Transaction(transactionId, type, amount, timestamp);
                transactionTable.getItems().add(transaction);
            }

            Scene scene = new Scene(new ScrollPane(transactionTable), 600, 400);
            transactionStage.setScene(scene);
            transactionStage.show();

        } catch (SQLException e) {
            showAlert("Error: " + e.getMessage());
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("ATM Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() throws Exception {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

package application;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class TransactionHistoryPage {

    private Account account;
    private DBHelper dbHelper;

    // Constructor accepting Account object
    public TransactionHistoryPage(Account account) {
        this.account = account;
        this.dbHelper = new DBHelper();
    }

    // Method to display the transaction history page
    public void showTransactionHistoryPage(Stage primaryStage) {
        VBox layout = new VBox(10);

        // Fetching the transaction history from DBHelper
        List<Transaction> transactions = dbHelper.getTransactionHistory(account.getAccountId());

        // If no transactions exist, show an alert
        if (transactions.isEmpty()) {
            showAlert(AlertType.INFORMATION, "No Transactions", "No transactions available.");
        } else {
            // Display each transaction as a label instead of button for better information view
            for (Transaction transaction : transactions) {
                // You could format this more effectively as per your needs
                layout.getChildren().add(new Label(transaction.toString()));
            }
        }

        // Back button to go back to the ATMPage
        Button backButton = new Button("Back to ATM");
        backButton.setOnAction(e -> {
            ATMPage atmPage = new ATMPage(account);
            atmPage.showATMPage(primaryStage);
        });

        layout.getChildren().add(backButton);

        // Scene setup
        Scene scene = new Scene(layout, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Transaction History");
        primaryStage.show();
    }

    // Helper method to show alerts
    private void showAlert(AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

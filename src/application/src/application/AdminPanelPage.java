package application;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AdminPanelPage {

    public void showAdminPanel(Stage primaryStage) {
        VBox layout = new VBox(10);

        Button viewAccountsButton = new Button("View Accounts");
        viewAccountsButton.setOnAction(e -> viewAllAccounts());

        Button manageTransactionsButton = new Button("Manage Transactions");
        manageTransactionsButton.setOnAction(e -> manageTransactions());

        layout.getChildren().addAll(viewAccountsButton, manageTransactionsButton);

        Scene scene = new Scene(layout, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Admin Panel");
        primaryStage.show();
    }

    private void viewAllAccounts() {
        // View all accounts logic here
        System.out.println("Viewing all accounts...");
    }

    private void manageTransactions() {
        // Manage transactions logic here
        System.out.println("Managing transactions...");
    }
}

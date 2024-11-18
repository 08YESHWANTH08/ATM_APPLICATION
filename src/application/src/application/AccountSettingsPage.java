package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class AccountSettingsPage extends Application {

    private ATMService atmService;

    public AccountSettingsPage() {
        atmService = new ATMService();
    }

    @Override
    public void start(Stage primaryStage) {
        // Layout setup
        VBox layout = new VBox(10);

        // Labels, TextFields, and Buttons
        Label accountHolderLabel = new Label("Account Holder:");
        Label accountBalanceLabel = new Label("Account Balance:");
        Label accountTypeLabel = new Label("Account Type:");
        Label accountIdLabel = new Label("Account ID:");

        // Get the account details (simulating account id 1 for now)
        Account account = atmService.getAccountBalance(1);
        
        if (account != null) {
            accountHolderLabel.setText("Account Holder: " + account.getAccountHolderName());
            accountBalanceLabel.setText("Account Balance: $" + account.getBalance());
            accountTypeLabel.setText("Account Type: " + account.getAccountType());
            accountIdLabel.setText("Account ID: " + account.getAccountId());
        }

        // Update PIN button
        Button changePinButton = new Button("Change PIN");
        changePinButton.setOnAction(event -> {
            ChangePINPage changePINPage = new ChangePINPage();
            changePINPage.start(new Stage());
            primaryStage.close();
        });

        // Layout for account settings page
        layout.getChildren().addAll(accountHolderLabel, accountBalanceLabel, accountTypeLabel, accountIdLabel, changePinButton);

        // Scene setup
        Scene scene = new Scene(layout, 300, 250);
        primaryStage.setTitle("Account Settings");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

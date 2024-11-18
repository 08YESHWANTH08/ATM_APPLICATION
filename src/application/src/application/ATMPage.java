package application;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ATMPage {

    private Account account;

    public ATMPage(Account account) {
        this.account = account;
    }

    public void showATMPage(Stage primaryStage) {
        VBox layout = new VBox(10);

        Button checkBalanceButton = new Button("Check Balance");
        checkBalanceButton.setOnAction(e -> checkBalance());

        Button withdrawButton = new Button("Withdraw");
        withdrawButton.setOnAction(e -> withdrawAmount());

        Button depositButton = new Button("Deposit");
        depositButton.setOnAction(e -> depositAmount());

        Button changePinButton = new Button("Change PIN");
        changePinButton.setOnAction(e -> changePIN());

        layout.getChildren().addAll(checkBalanceButton, withdrawButton, depositButton, changePinButton);

        Scene scene = new Scene(layout, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.setTitle("ATM Page");
        primaryStage.show();
    }

    private void checkBalance() {
        // Logic to check balance
        System.out.println("Your balance is: " + account.getBalance());
    }

    private void withdrawAmount() {
        // Logic to withdraw money
        System.out.println("Withdraw functionality here");
    }

    private void depositAmount() {
        // Logic to deposit money
        System.out.println("Deposit functionality here");
    }

    private void changePIN() {
        // Logic to change PIN
        ChangePINPage changePINPage = new ChangePINPage(account);
        Stage stage = new Stage();
        changePINPage.showChangePINPage(stage);
    }
}

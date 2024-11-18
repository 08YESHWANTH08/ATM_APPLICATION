package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class LoginPage extends Application {

    private ATMService atmService;

    public LoginPage() {
        atmService = new ATMService();
    }

    @Override
    public void start(Stage primaryStage) {
        // Layout setup
        VBox layout = new VBox(10);

        // Labels, TextFields, and Buttons
        Label accountIdLabel = new Label("Account ID:");
        TextField accountIdField = new TextField();
        Label pinLabel = new Label("PIN:");
        PasswordField pinField = new PasswordField();
        Button loginButton = new Button("Login");

        // Action when the login button is clicked
        loginButton.setOnAction(event -> {
            String accountIdText = accountIdField.getText();
            String pin = pinField.getText();

            if (accountIdText.isEmpty() || pin.isEmpty()) {
                showError("Please enter both Account ID and PIN.");
            } else {
                int accountId = Integer.parseInt(accountIdText);
                Account account = atmService.getAccount(accountId);

                if (account != null && account.getPin().equals(pin)) {
                    // Login successful, show account settings page
                    AccountSettingsPage accountSettingsPage = new AccountSettingsPage();
                    accountSettingsPage.start(new Stage());
                    primaryStage.close();
                } else {
                    showError("Invalid Account ID or PIN.");
                }
            }
        });

        // Layout for login page
        layout.getChildren().addAll(accountIdLabel, accountIdField, pinLabel, pinField, loginButton);

        // Scene setup
        Scene scene = new Scene(layout, 300, 200);
        primaryStage.setTitle("Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Login Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

// ChangePINPage.java
package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class ChangePINPage extends Application {

    private ATMService atmService;

    public ChangePINPage() {
        atmService = new ATMService();
    }

    @Override
    public void start(Stage primaryStage) {
        // Layout setup
        VBox layout = new VBox(10);

        // Labels, TextFields, and Buttons
        Label currentPinLabel = new Label("Enter Current PIN:");
        PasswordField currentPinField = new PasswordField();
        Label newPinLabel = new Label("Enter New PIN:");
        PasswordField newPinField = new PasswordField();
        Button changePinButton = new Button("Change PIN");

        // Action when button is clicked
        changePinButton.setOnAction(event -> {
            String currentPin = currentPinField.getText();
            String newPin = newPinField.getText();

            if (currentPin.isEmpty() || newPin.isEmpty()) {
                showError("Please enter both current and new PIN.");
            } else {
                boolean isUpdated = atmService.changePin(1, newPin); // Assume account ID 1
                if (isUpdated) {
                    showMessage("PIN changed successfully!");
                } else {
                    showError("Failed to change PIN.");
                }
            }
        });

        // Adding elements to the layout
        layout.getChildren().addAll(currentPinLabel, currentPinField, newPinLabel, newPinField, changePinButton);

        // Scene setup
        Scene scene = new Scene(layout, 300, 200);
        primaryStage.setTitle("Change PIN");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

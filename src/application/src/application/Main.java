package application;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Initialize LoginPage and show it
        LoginPage loginPage = new LoginPage();
        loginPage.start(primaryStage); // Start the LoginPage using the same Stage
    }

    public static void main(String[] args) {
        launch(args);  // Launch the JavaFX application
    }
}

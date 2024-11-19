package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.*;

public class Main extends Application {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/atmdb";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Ye$hw@nth8";

    private TextField userIdField;
    private PasswordField passwordField;
    private Button loginButton;

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("ATM Application");

        Label userIdLabel = new Label("User ID:");
        userIdField = new TextField();

        Label passwordLabel = new Label("Password:");
        passwordField = new PasswordField();

        loginButton = new Button("Login");
        loginButton.setOnAction(e -> login());

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        gridPane.add(userIdLabel, 0, 0);
        gridPane.add(userIdField, 1, 0);
        gridPane.add(passwordLabel, 0, 1);
        gridPane.add(passwordField, 1, 1);
        gridPane.add(loginButton, 1, 2);

        Scene scene = new Scene(gridPane, 400, 200);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private void login() {
        String userId = userIdField.getText().trim();
        String password = passwordField.getText();

        if (authenticateUser(userId, password)) {
            openATMInterface(userId);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Error");
            alert.setHeaderText(null);
            alert.setContentText("Invalid username or password. Please try again.");
            alert.showAndWait();
        }
    }

    private boolean authenticateUser(String userId, String password) {
        boolean isAuthenticated = false;
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT * FROM users WHERE user_id = ? AND password = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, Integer.parseInt(userId));
                stmt.setString(2, password);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        isAuthenticated = true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isAuthenticated;
    }

    private void openATMInterface(String userId) {
        ATMInterface atmInterface = new ATMInterface(DB_URL, DB_USER, DB_PASSWORD, userId);
        atmInterface.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

// DBHelper.java
package application;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DBHelper {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/atmdb";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "Ye$hw@nth8";

    public DBHelper() {
        try {
            // Initialization code (optional)
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Account createAccount(int accountId, String accountHolderName, String pin, double balance, String accountType) {
        Date dateCreated = new Date();  // Get the current date for account creation
        return new Account(accountId, accountHolderName, pin, balance, accountType, dateCreated);
    }

    public Account getAccount(int accountId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            String query = "SELECT * FROM accounts WHERE account_id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, accountId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("account_id");
                String holderName = rs.getString("account_holder_name");
                String pin = rs.getString("pin");
                double balance = rs.getDouble("balance");
                String accountType = rs.getString("account_type");
                Date dateCreated = rs.getDate("date_created");

                return new Account(id, holderName, pin, balance, accountType, dateCreated);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public boolean updateBalance(int accountId, double newBalance) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            String query = "UPDATE accounts SET balance = ? WHERE account_id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setDouble(1, newBalance);
            stmt.setInt(2, accountId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public List<Transaction> getTransactionHistory(int accountId) {
        List<Transaction> transactions = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            String query = "SELECT * FROM transactions WHERE account_id = ? ORDER BY transaction_date DESC";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, accountId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int transactionId = rs.getInt("transaction_id");
                double amount = rs.getDouble("amount");
                String type = rs.getString("transaction_type");
                Date transactionDate = rs.getDate("transaction_date");

                transactions.add(new Transaction(transactionId, accountId, amount, type, transactionDate));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return transactions;
    }

    public boolean updatePin(int accountId, String newPin) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            String query = "UPDATE accounts SET pin = ? WHERE account_id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, newPin);
            stmt.setInt(2, accountId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}

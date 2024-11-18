package application;

import java.util.HashMap;
import java.util.Map;

public class ATMService {

    // A map to simulate a database of accounts (AccountID -> Account object)
    private Map<Integer, Account> accountDatabase;

    // Constructor to initialize the ATMService with some sample accounts
    public ATMService() {
        accountDatabase = new HashMap<>();
        // Sample account data
        accountDatabase.put(1, new Account(1, "John Doe", "1234", 1000.0, "Savings", null));
        accountDatabase.put(2, new Account(2, "Jane Smith", "5678", 500.0, "Checking", null));
    }

    // Method to get account details by account ID
    public Account getAccount(int accountId) {
        return accountDatabase.get(accountId);
    }

    // Method to deposit an amount into an account
    public boolean deposit(int accountId, double amount) {
        Account account = accountDatabase.get(accountId);
        if (account != null && amount > 0) {
            account.setBalance(account.getBalance() + amount);  // Update balance
            return true;  // Deposit successful
        }
        return false;  // Account not found or invalid amount
    }

    // Method to withdraw an amount from an account
    public boolean withdraw(int accountId, double amount) {
        Account account = accountDatabase.get(accountId);
        if (account != null && amount > 0 && account.getBalance() >= amount) {
            account.setBalance(account.getBalance() - amount);  // Update balance
            return true;  // Withdrawal successful
        }
        return false;  // Insufficient balance or invalid amount
    }

    // Method to check the balance of an account
    public double checkBalance(int accountId) {
        Account account = accountDatabase.get(accountId);
        if (account != null) {
            return account.getBalance();  // Return balance
        }
        return -1;  // Account not found
    }

    // Method to change the PIN of an account
    public boolean changePin(int accountId, String newPin) {
        Account account = accountDatabase.get(accountId);
        if (account != null && newPin != null && !newPin.isEmpty()) {
            account.setPin(newPin);  // Update PIN
            return true;  // Successfully updated the PIN
        }
        return false;  // Account not found or invalid PIN
    }

    // Method to transfer money between two accounts
    public boolean transferMoney(int fromAccountId, int toAccountId, double amount) {
        Account fromAccount = accountDatabase.get(fromAccountId);
        Account toAccount = accountDatabase.get(toAccountId);
        if (fromAccount != null && toAccount != null && amount > 0 && fromAccount.getBalance() >= amount) {
            // Withdraw from sender account
            fromAccount.setBalance(fromAccount.getBalance() - amount);
            // Deposit into recipient account
            toAccount.setBalance(toAccount.getBalance() + amount);
            return true;  // Transfer successful
        }
        return false;  // Invalid accounts, insufficient balance, or invalid amount
    }

    // Method to check the account holder's information
    public String getAccountInfo(int accountId) {
        Account account = accountDatabase.get(accountId);
        if (account != null) {
            return String.format("Account Holder: %s\nAccount Type: %s\nBalance: %.2f", 
                account.getAccountHolderName(), account.getAccountType(), account.getBalance());
        }
        return "Account not found.";  // Account not found
    }

    // Method to simulate ATM login by verifying PIN
    public boolean login(int accountId, String pin) {
        Account account = accountDatabase.get(accountId);
        return account != null && account.getPin().equals(pin);  // Check if the PIN matches
    }

    // Method to simulate logging out (you can add more logic here if needed)
    public void logout() {
        // Logic for logout (could be just clearing session or other operations)
        System.out.println("Logged out successfully.");
    }
}

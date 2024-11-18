// Account.java
package application;

import java.util.Date;

public class Account {

    private int accountId;
    private String accountHolderName;
    private String pin;
    private double balance;
    private String accountType;
    private Date dateCreated;

    public Account(int accountId, String accountHolderName, String pin, double balance, String accountType, Date dateCreated) {
        this.accountId = accountId;
        this.accountHolderName = accountHolderName;
        this.pin = pin;
        this.balance = balance;
        this.accountType = accountType;
        this.dateCreated = dateCreated;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public String toString() {
        return "Account[accountId=" + accountId + ", holder=" + accountHolderName + ", balance=" + balance + "]";
    }
}

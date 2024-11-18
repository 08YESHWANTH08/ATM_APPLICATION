-- Step 1: Create the database
CREATE DATABASE IF NOT EXISTS atm_db;

-- Step 2: Use the created database
USE atm_db;

-- Step 3: Create the accounts table to store account information
CREATE TABLE IF NOT EXISTS accounts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    account_number VARCHAR(20) UNIQUE NOT NULL,
    pin VARCHAR(4) NOT NULL,
    balance DOUBLE NOT NULL DEFAULT 0.0,
    account_type ENUM('Savings', 'Current') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Step 4: Create the transactions table to store transaction history
CREATE TABLE IF NOT EXISTS transactions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    account_number VARCHAR(20) NOT NULL,
    amount DOUBLE NOT NULL,
    transaction_type ENUM('Deposit', 'Withdraw') NOT NULL,
    transaction_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (account_number) REFERENCES accounts(account_number)
);

-- Step 5: Insert sample data into the accounts table
INSERT INTO accounts (account_number, pin, balance, account_type)
VALUES
    ('1234567890', '1234', 5000.0, 'Savings'),
    ('9876543210', '5678', 10000.0, 'Current'),
    ('1122334455', '0000', 7500.0, 'Savings'),
    ('6677889900', '4321', 2500.0, 'Current');

-- Step 6: Optionally, you can insert some sample transactions into the transactions table
INSERT INTO transactions (account_number, amount, transaction_type)
VALUES
    ('1234567890', 1000.0, 'Deposit'),
    ('1234567890', 500.0, 'Withdraw'),
    ('9876543210', 2000.0, 'Deposit'),
    ('9876543210', 1000.0, 'Withdraw'),
    ('1122334455', 1500.0, 'Deposit'),
    ('6677889900', 200.0, 'Withdraw');

-- Step 7: Verify the creation of tables and inserted data
-- Select all accounts to verify data
SELECT * FROM accounts;

-- Select all transactions to verify data
SELECT * FROM transactions;

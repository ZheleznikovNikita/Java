package bank.models;

import javax.swing.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;

public class Transaction {
    private long transactionId;
    private Account fromAccount;
    private Account toAccount;
    private double amount;
    private LocalDateTime date;

    public Transaction(Account fromAccount, Account toAccount, double amount) throws Exception {
        setTransactionId();
        setFromAccount(fromAccount);
        setToAccount(toAccount);
        setAmount(amount);
        this.fromAccount = fromAccount;
        this.amount = amount;
        setDate();
    }

    // Геттеры
    public long getTransactionId() { return transactionId; }
    public Account getFromAccount() { return  fromAccount; }
    public Account getToAccount() { return  toAccount; }
    public double getAmount() { return amount; }
    public LocalDateTime getDate() { return date; }
    // Сеттеры
    private void setTransactionId() { transactionId = System.currentTimeMillis() + new Random().nextLong() + 3; }
    public void setFromAccount(Account fromAccount) throws Exception {
        if (fromAccount == null)
            throw new Exception("Передано пустое значение");
        this.fromAccount = fromAccount;
    }
    public void setToAccount(Account toAccount) throws Exception {
        if (toAccount == null)
            throw new Exception("Передано пустое значение");
        this.toAccount = toAccount;
    }
    public void setAmount(double amount) throws Exception {
        if (amount <= 0)
            throw new Exception("Число должно быть положительным");
        this.amount = amount;
    }
    private void setDate() { date = LocalDateTime.now(); }

    public static void logTransaction(Transaction transaction) {
        String dateTime = transaction.date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
        System.out.println("Транзакция " + transaction.transactionId +
                "\nСумма: " + transaction.amount + " " + transaction.fromAccount.getCurrency() +
                "\nС: " + transaction.fromAccount.getAccountNumber() +
                "\nНА: " + transaction.toAccount.getAccountNumber() +
                "\nВремя транзакции: " + dateTime);
    }
}

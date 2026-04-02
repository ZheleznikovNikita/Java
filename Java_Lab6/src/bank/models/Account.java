package bank.models;

import bank.utils.IdGenerator;

import java.util.Objects;

public class Account {
    private long accountNumber;
    private Customer customer;
    private double balance;
    private String currency;

    private static int totalAccounts = 0;

    public Account(Customer customer, String currency) throws Exception {
        generateAccountNumber();
        setCustomer(customer);
        balance = 0.0;
        setCurrency(currency);
        totalAccounts++;
        customer.addAccount(this);
    }

    // Геттеры
    public long getAccountNumber() { return accountNumber; }
    public Customer getCustomer() { return  customer; }
    public double getBalance() { return balance; }
    public String getCurrency() { return currency; }
    public static int getTotalAccounts() { return totalAccounts; }
    // Сеттеры
    private void generateAccountNumber() { accountNumber = IdGenerator.generateId(); }
    public void setCustomer(Customer customer) throws Exception {
        if (customer == null)
            throw new Exception("Передано пустое значение");
        this.customer = customer;
    }
    public void setBalance(double balance) throws Exception {
        check_amount(balance);
        this.balance = balance;
    }
    public void setCurrency(String currency) throws Exception {
        if (currency == null || currency.isEmpty())
            throw new Exception("Передана пустая строка");
        this.currency = currency;
    }

    public void deposit(double amount) throws Exception {
        check_amount(amount);
        balance += amount;
        System.out.println("Баланс пополнен на " + amount + " " + currency);
    }

    public boolean withdraw(double amount) throws Exception {
        check_amount(amount);
        if (amount <= balance){
            balance -= amount;
            System.out.println("Снято " + amount + " " + currency);
            return true;
        }
        System.out.println("Недостаточно средств");
        return false;
    }

    public void transfer(Account to, double amount) throws Exception {
        if (!Objects.equals(getCurrency(), to.getCurrency())) {
            System.out.println("Валюты не совпадают, перевод невозможен");
            return;
        }
        if (withdraw(amount)) {
            to.setBalance(to.getBalance() + amount);
            System.out.println("Переведено средств: " + amount + " " + currency);
        }
        else
            System.out.println("Не удалось перевести средства");
    }

    public void check_amount(double amount) throws Exception {
        if (amount <= 0.0)
            throw new Exception("Недопустимое число");
    }

}

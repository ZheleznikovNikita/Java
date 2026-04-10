package models;

import utils.ValidationUtils;

public class BankAccount {
    // Поля
    private String accountNumber;
    private String ownerName;
    private double balance;
    private double interestRate;
    private boolean isActive;

    // Конструктор
    public BankAccount(String accountNumber, String ownerName, double initialBalance, double interestRate) throws Exception {
        setAccountNumber(accountNumber);
        setOwnerName(ownerName);
        setInterestRate(interestRate);
        deposit(initialBalance);
        isActive = true;
    }

    // Геттеры
    public String getAccountNumber() { return accountNumber; }
    public String getOwnerName() { return ownerName; }
    public double getBalance() { return balance; }
    public double getInterestRate() { return interestRate; }
    public  boolean getIsActive() { return isActive; }
    // Сеттеры
    public void setAccountNumber(String accountNumber) throws Exception {
        ValidationUtils.check_name(accountNumber, "Пустой номер аккаунта");
        this.accountNumber = accountNumber;
    }
    public void setOwnerName(String ownerName) throws Exception {
        ValidationUtils.check_name(ownerName, "Пустое имя пользователя");
        this.ownerName = ownerName;
    }
    public void setInterestRate(double interestRate) throws Exception {
        ValidationUtils.check_number_less_or_equal(interestRate, "Процентная ставка должна быть положительной");
        this.interestRate = interestRate;
    }
    // Пополнение карты
    public void deposit(double amount) throws Exception {
        ValidationUtils.check_number_less_or_equal(amount, "Пополнять можно только на положительную сумму");
        if (getIsActive())
            balance += amount;
        else
            System.out.println("Карта закрыта, пополнение невозможно");
    }
    // Снятие баланса
    public void withdraw(double amount) throws Exception {
        if (amount <= 0 || amount > getBalance())
            throw new Exception("Нельзя перевести сумму большую баланса или неположительную");
        if (getIsActive())
            balance -= amount;
        else
            System.out.println("Карта закрыта, снятие невозможно");
    }
    // Перевод
    public void transfer(BankAccount to, double amount) throws Exception {
        if (!getIsActive()) {
            System.out.println("Ваша карта неактивна, перевод невозможен");
            return;
        }
        if (!to.getIsActive()){
            System.out.println("Карта пользователя неактивна, перевод невозможен");
            return;
        }
        withdraw(amount);
        to.deposit(amount);
    }
    // Начисление процентов
    public void applyInterest() throws Exception {
        if (!getIsActive())
            throw new Exception("Карта неактивна");
        balance += getBalance() * getInterestRate() / 100;
    }
    // Закрытие аккаунта
    public void closeAccount() {
        if (getIsActive())
            isActive = false;
        else
            System.out.println("Карта уже закрыта");
    }
    // Вывод информации
    public void printInfo() {
        System.out.println("Номер счёта: " + getAccountNumber());
        System.out.println("Владелец: " + getOwnerName());
        System.out.println("Баланс: " + getBalance());
        System.out.println("Процентная ставка: " + getInterestRate());
        System.out.println("Счёт " + (getIsActive() ? "активен" : "закрыт"));
    }
}

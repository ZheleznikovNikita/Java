package bank.models;

import java.security.spec.ECField;
import java.util.ArrayList;
import java.util.Random;

public class Customer {
    private long customerId;
    private String fullName;
    private ArrayList<Account> accounts;

    private static int totalCustomers;

    public Customer(String fullName) throws Exception {
        setCustomerId();
        setFullName(fullName);
        accounts = new ArrayList<>();
        totalCustomers++;
    }

    // Геттеры
    public long getCustomerId() { return customerId; }
    public String getFullName() { return fullName; }
    public ArrayList<Account> getAccounts() { return accounts; }
    public static int getTotalCustomers() { return totalCustomers; }
    public int getAccountCount() { return accounts.size(); }
    // Сеттеры
    private void setCustomerId() { customerId = System.currentTimeMillis() + new Random().nextLong() + 1; }
    public void setFullName(String fullName) throws Exception {
        if (fullName == null || fullName.isEmpty())
            throw new Exception("Передано пустое имя");
        this.fullName = fullName;
    }

    public void addAccount(Account account) throws Exception {
        if (account == null)
            throw new Exception("Передано пустое значение");
        accounts.add(account);
    }

    public double getTotalBalance() {
        double sum = 0.0;
        for (var account : accounts)
            sum += account.getBalance();
        return sum;
    }
}

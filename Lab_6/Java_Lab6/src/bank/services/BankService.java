package bank.services;

import bank.models.Customer;
import bank.models.Account;

public class BankService {
    public static void printCustomerSummary(Customer customer) {
        System.out.println("Клиент: " + customer.getFullName() + " (ID: " + customer.getCustomerId() + ")");
        System.out.println("Всего счетов: " + customer.getAccountCount());
        System.out.println("Общий баланс: " + String.format("%.2f", customer.getTotalBalance()));
    }
}

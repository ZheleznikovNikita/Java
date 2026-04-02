package bank.main;

import bank.models.*;
import bank.services.BankService;

public class Main {
    static void main() {
        try {
            Customer client1 = new Customer("Иван Иванов");
            Customer client2 = new Customer("Пётр Петров");

            Account acc1 = new Account(client1, "USD");
            Account acc2 = new Account(client1, "EUR");
            Account acc3 = new Account(client2, "USD");

            System.out.println("Пополнение");
            acc1.deposit(1500.0);
            acc2.deposit(500.0);
            acc3.deposit(800.0);

            System.out.println("\nПереводы");
            acc1.transfer(acc3, 300.0);
            acc1.transfer(acc2, 100.0);
            acc3.transfer(acc1, 500.0);

            System.out.println("\nСтатистика");
            System.out.println("Всего клиентов: " + Customer.getTotalCustomers());
            System.out.println("Всего открытых счетов: " + Account.getTotalAccounts());

            System.out.println("\nСводка по клиентам");
            BankService.printCustomerSummary(client1);
            System.out.println();
            BankService.printCustomerSummary(client2);
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}

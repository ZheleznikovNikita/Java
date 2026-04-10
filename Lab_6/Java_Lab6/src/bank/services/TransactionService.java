package bank.services;

import bank.models.Transaction;

public class TransactionService {
    public static void printTransactionHistory(Transaction... transactions) {
        System.out.println("История транзакций:");
        for (Transaction t : transactions)
            Transaction.logTransaction(t);
    }
}

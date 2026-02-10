package services;

import entities.Transaction;

import java.util.List;

public interface TransactionService {
    void addTransaction(Transaction transaction);
    void getAvailableBalance(Transaction transaction) ;
    List<Transaction> getTransactions();
}

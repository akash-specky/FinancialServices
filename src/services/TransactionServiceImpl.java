package services;

import entities.Transaction;
import entities.TransactionType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TransactionServiceImpl implements TransactionService {

    private final List<Transaction> transactions = new ArrayList<>();

    private BudgetService budgetService;

    public void setBudgetService(BudgetService budgetService) {
        this.budgetService = budgetService;
    }


    @Override
    public void addTransaction(Transaction transaction) {

        getAvailableBalance(transaction);

        transactions.add(transaction);

        if (budgetService != null) {
            budgetService.evaluateBudget(transaction);
        }
    }

    @Override
    public void getAvailableBalance(Transaction transaction) {
        if (transaction.getType() == TransactionType.EXPENSE) {

            double totalIncome = transactions.stream()
                    .filter(t -> t.getType() == TransactionType.INCOME)
                    .mapToDouble(Transaction::getAmount)
                    .sum();

            double totalExpense = transactions.stream()
                    .filter(t -> t.getType() == TransactionType.EXPENSE)
                    .mapToDouble(Transaction::getAmount)
                    .sum();

            double balance = totalIncome - totalExpense;

            if (balance < transaction.getAmount()) {
                throw new IllegalArgumentException(
                        "Insufficient balance. Available: " + balance);
            }
        }
    }


    @Override
    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }
}

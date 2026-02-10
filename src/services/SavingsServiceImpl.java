package services;

import entities.Category;
import entities.SavingsGoal;
import entities.Transaction;
import entities.TransactionType;
import factory.TransactionFactory;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SavingsServiceImpl implements SavingsService {

    private final Map<String, SavingsGoal> goals = new HashMap<>();

    @Override
    public void addGoal(SavingsGoal goal) {
        goals.put(goal.getGoalName(), goal);
    }

    @Override
    public void addSavings(String goalName, double amount, TransactionService transactionService) {
        SavingsGoal goal = goals.get(goalName);

        if (Objects.isNull(goal)) {
            throw new IllegalArgumentException("Savings goal not found");
        }
        double totalIncome = transactionService.getTransactions().stream()
                .filter(t -> t.getType() == TransactionType.INCOME)
                .mapToDouble(Transaction::getAmount)
                .sum();
        if (totalIncome < amount) {
            throw new IllegalArgumentException("Insufficient funds, Available balance" + totalIncome);
        }
        goal.addSavings(amount);
        Category category = new Category(goalName);
        Transaction expense = TransactionFactory.createExpense(
                amount,
                category,
                LocalDate.now(),
                TransactionType.SAVINGS.toString(),
                TransactionType.SAVINGS
        );

        transactionService.addTransaction(expense);
        System.out.println("Savings Progress: " +
                goal.getProgressPercentage() + "%");

        if (goal.isCompleted()) {
            System.out.println("Goal achieved: " + goalName);
        }
    }
}

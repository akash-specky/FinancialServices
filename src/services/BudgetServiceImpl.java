package services;

import entities.Budget;
import entities.Transaction;
import entities.TransactionType;

import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;

public class BudgetServiceImpl implements BudgetService {

    private final Map<String, Budget> budgetByCategory = new HashMap<>();
    private final TransactionService transactionService;

    public BudgetServiceImpl(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Override
    public void addBudget(Budget budget) {
        budgetByCategory.put(budget.getCategory().getName(), budget);
    }


    @Override
    public void evaluateBudget(Transaction transaction) {

        if (transaction.getType() != TransactionType.EXPENSE) {
            return;
        }

        String categoryName = transaction.getCategory().getName();
        Budget budget = budgetByCategory.get(categoryName);

        if (budget == null) {
            return;
        }

        YearMonth currentMonth = YearMonth.from(transaction.getDate());

        double monthlyExpense = transactionService.getTransactions().stream()
                .filter(t -> t.getType() == TransactionType.EXPENSE)
                .filter(t -> t.getCategory().getName().equals(categoryName))
                .filter(t -> YearMonth.from(t.getDate()).equals(currentMonth))
                .mapToDouble(Transaction::getAmount)
                .sum();

        double limit = budget.getMonthlyLimit();

        if (monthlyExpense > limit) {
            System.out.println("Budget EXCEEDED for category: " + categoryName);
            System.out.println("Spent: " + monthlyExpense + "  Limit: " + limit);
        }
        else if (monthlyExpense == limit) {
            System.out.println("⚠Budget LIMIT REACHED for category: " + categoryName);
        }
        else if (monthlyExpense >= limit * 0.8) {
            System.out.println(" Warning: 80% of budget used for category: " + categoryName);
        }
    }
}

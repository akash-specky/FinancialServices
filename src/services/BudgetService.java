package services;

import entities.Budget;
import entities.Transaction;

public interface BudgetService {
    void addBudget(Budget budget);
    void evaluateBudget(Transaction transaction);
}


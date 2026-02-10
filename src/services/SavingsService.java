package services;

import entities.SavingsGoal;

public interface SavingsService {
    void addGoal(SavingsGoal goal);
    void addSavings(String goalName, double amount);
}

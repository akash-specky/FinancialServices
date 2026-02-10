package services;

import entities.SavingsGoal;

import java.util.HashMap;
import java.util.Map;

public class SavingsServiceImpl implements SavingsService {

    private final Map<String, SavingsGoal> goals = new HashMap<>();

    @Override
    public void addGoal(SavingsGoal goal) {
        goals.put(goal.getGoalName(), goal);
    }

    @Override
    public void addSavings(String goalName, double amount) {
        SavingsGoal goal = goals.get(goalName);

        if (goal == null) {
            throw new IllegalArgumentException("Savings goal not found");
        }

        goal.addSavings(amount);

        System.out.println("Savings Progress: " +
                goal.getProgressPercentage() + "%");

        if (goal.isCompleted()) {
            System.out.println("Goal achieved: " + goalName);
        }
    }
}

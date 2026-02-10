package entities;

public class SavingsGoal {

    private final String goalName;
    private final double targetAmount;
    private double savedAmount;

    public SavingsGoal(String goalName, double targetAmount) {
        if (targetAmount <= 0) {
            throw new IllegalArgumentException("Target amount must be positive");
        }
        this.goalName = goalName;
        this.targetAmount = targetAmount;
    }

    public void addSavings(double amount) {
        this.savedAmount += amount;
    }

    public boolean isCompleted() {
        return savedAmount >= targetAmount;
    }

    public double getProgressPercentage() {
        return (savedAmount / targetAmount) * 100;
    }

    public String getGoalName() {
        return goalName;
    }
}

package entities;


public class Budget {

    private final Category category;
    private final double monthlyLimit;

    public Budget(Category category, double monthlyLimit) {
        if (monthlyLimit <= 0) {
            throw new IllegalArgumentException("Budget limit must be positive");
        }
        this.category = category;
        this.monthlyLimit = monthlyLimit;
    }

    public Category getCategory() {
        return category;
    }

    public double getMonthlyLimit() {
        return monthlyLimit;
    }
}

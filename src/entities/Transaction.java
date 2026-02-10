package entities;

import java.time.LocalDate;

public class Transaction {
    private final String id;
    private final double amount;
    private final TransactionType type;
    private final Category category;
    private final LocalDate date;
    private final String description;

    public Transaction(String id, double amount, TransactionType type,
                       Category category, LocalDate date, String description) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Transaction amount must be positive");
        }
        this.id = id;
        this.amount = amount;
        this.type = type;
        this.category = category;
        this.date = date;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public TransactionType getType() {
        return type;
    }

    public Category getCategory() {
        return category;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }
}


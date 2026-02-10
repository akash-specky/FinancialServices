package entities;

import java.time.LocalDate;

public class BillReminder {

    private final String billName;
    private final LocalDate dueDate;
    private final double amount;

    public BillReminder(String billName, LocalDate dueDate, double amount) {
        this.billName = billName;
        this.dueDate = dueDate;
        this.amount = amount;
    }

    public boolean isDueToday() {
        return !dueDate.isAfter(LocalDate.now());
    }

    public String getBillName() {
        return billName;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }
}

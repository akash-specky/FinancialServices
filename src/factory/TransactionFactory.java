package factory;

import entities.Category;
import entities.Transaction;
import entities.TransactionType;
import services.TransactionService;

import java.time.LocalDate;
import java.util.UUID;

public class TransactionFactory {

    public static Transaction createIncome(double amount, Category category,
                                           LocalDate date, String description) {


        return new Transaction(
                UUID.randomUUID().toString(),
                amount,
                TransactionType.INCOME,
                category,
                date,
                description
        );
    }

    public static Transaction createExpense(double amount, Category category,
                                            LocalDate date, String description,TransactionType type ) {

        return new Transaction(
                UUID.randomUUID().toString(),
                amount,
                type,
                category,
                date,
                description
        );
    }
}

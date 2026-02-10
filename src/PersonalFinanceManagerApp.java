import entities.*;
import factory.TransactionFactory;
import services.*;

import java.time.LocalDate;
import java.util.Scanner;



    private static final Scanner scanner = new Scanner(System.in);
    private static final double BACK_OPTION = 10;

      void main() {

        TransactionServiceImpl transactionService = new TransactionServiceImpl();
        BudgetServiceImpl budgetService = new BudgetServiceImpl(transactionService);
        SavingsService savingsService = new SavingsServiceImpl();
        ReminderService reminderService = new ReminderServiceImpl();


        transactionService.setBudgetService(budgetService);

        System.out.println("===== Personal Finance Manager =====");

        boolean isRunning = true;

        while (isRunning) {
            try {

                reminderService.checkDueReminders();

                displayMenu();
                int choice = readChoice();

                if (choice == BACK_OPTION) {
                    return;
                }

                switch (choice) {
                    case 1:
                        addIncome(transactionService);
                        break;
                    case 2:
                        addExpense(transactionService);
                        break;
                    case 3:
                        setBudget(budgetService);
                        break;
                    case 4:
                        addSavingsGoal(savingsService);
                        break;
                    case 5:
                        addSavingsAmount(savingsService,transactionService);
                        break;
                    case 6:
                        addBillReminder(reminderService);
                        break;
                    case 7:
                        showTransactions(transactionService);
                        break;
                    case 8:
                        generateReport(transactionService);
                        break;
                    case 9:
                        isRunning = false;
                        break;
                    default:
                        System.out.println("Invalid option. Try again.");
                }

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        System.out.println("Application closed.");
    }

    private static void displayMenu() {
        System.out.println("\nChoose an option:");
        System.out.println("1. Add Income");
        System.out.println("2. Add Expense");
        System.out.println("3. Set Budget");
        System.out.println("4. Add Savings Goal");
        System.out.println("5. Add Savings Amount");
        System.out.println("6. Add Bill Reminder");
        System.out.println("7. View Transactions");
        System.out.println("8. Generate Report");
        System.out.println("9. Exit");
    }

    private static int readChoice() {
        System.out.print("Enter choice: ");
        return scanner.nextInt();
    }

    private static void addIncome(TransactionService transactionService) {
        System.out.print("Enter income amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Enter category: ");
        String categoryName = scanner.nextLine();

        System.out.print("Enter description: ");
        String description = scanner.nextLine();

        Category category = new Category(categoryName);
        Transaction income = TransactionFactory.createIncome(
                amount,
                category,
                LocalDate.now(),
                description);

        transactionService.addTransaction(income);
        System.out.println("Income added successfully.");
    }

    private static void addExpense(TransactionService transactionService) {
        System.out.print("Enter expense amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Enter category: ");
        String categoryName = scanner.nextLine();

        System.out.print("Enter description: ");
        String description = scanner.nextLine();

        Category category = new Category(categoryName);

        Transaction expense = TransactionFactory.createExpense(
                amount,
                category,
                LocalDate.now(),
                description,
                TransactionType.EXPENSE
        );

        transactionService.addTransaction(expense);
        System.out.println("Expense added successfully.");
    }

    private static void setBudget(BudgetService budgetService) {
        scanner.nextLine();
        System.out.print("Enter category: ");
        String categoryName = scanner.nextLine();

        System.out.print("Enter monthly limit: ");
        double limit = scanner.nextDouble();

        Budget budget = new Budget(new Category(categoryName), limit);
        budgetService.addBudget(budget);

        System.out.println("Budget set successfully.");
    }

    private static void addSavingsGoal(SavingsService savingsService) {
        scanner.nextLine();
        System.out.print("Enter goal name: ");
        String goalName = scanner.nextLine();

        System.out.print("Enter target amount: ");
        double target = scanner.nextDouble();

        savingsService.addGoal(new SavingsGoal(goalName, target));
        System.out.println("Savings goal added.");
    }

    private static void addSavingsAmount(SavingsService savingsService,TransactionService transactionService) {
        scanner.nextLine();
        System.out.print("Enter goal name: ");
        String goalName = scanner.nextLine();

        System.out.print("Enter amount: ");
        double amount = scanner.nextDouble();

        savingsService.addSavings(goalName, amount,transactionService);
    }

    private static void addBillReminder(ReminderService reminderService) {
        scanner.nextLine();

        System.out.print("Enter bill name: ");
        String billName = scanner.nextLine();

        System.out.print("Enter amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Enter due date (YYYY-MM-DD): ");
        String dateInput = scanner.nextLine();

        LocalDate dueDate = LocalDate.parse(dateInput);

        reminderService.addReminder(
                new BillReminder(billName, dueDate, amount)
        );

        System.out.println("Bill reminder added.");
    }


    private static void showTransactions(TransactionService transactionService) {
        if (transactionService.getTransactions().isEmpty()) {
            System.out.println("No Transaction");
            return;
        }
        transactionService.getTransactions().forEach(t ->
                System.out.println(
                        t.getDate() + " | " +
                                t.getType() + " | " +
                                t.getCategory().getName() + " | " +
                                t.getAmount() + " | " +
                                t.getDescription() + " | Available Balance: "+t.getAmount()
                )
        );
    }

    private static void generateReport(TransactionService transactionService) {

        System.out.println("Select report type:");
        System.out.println("1. Weekly");
        System.out.println("2. Monthly");
        System.out.println("3. Yearly");
        System.out.println("10. Back to Main Menu");

        int choice = scanner.nextInt();

        if (choice == BACK_OPTION) {
            return;
        }
        LocalDate now = LocalDate.now();
        LocalDate startDate;

        switch (choice) {
            case 1:
                startDate = now.minusDays(7);
                break;
            case 2:
                startDate = now.minusMonths(1);
                break;
            case 3:
                startDate = now.minusYears(1);
                break;
            default:
                System.out.println("Invalid choice");
                return;
        }

        double income = transactionService.getTransactions().stream()
                .filter(t -> !t.getDate().isBefore(startDate))
                .filter(t -> t.getType().equals(TransactionType.INCOME) )
                .mapToDouble(Transaction::getAmount)
                .sum();

        double expense = transactionService.getTransactions().stream()
                .filter(t -> !t.getDate().isBefore(startDate))
                .filter(t -> t.getType().equals(TransactionType.EXPENSE))
                .mapToDouble(Transaction::getAmount)
                .sum();

        FinancialReport report = new FinancialReport.Builder()
                .setTotalIncome(income)
                .setTotalExpense(expense)
                .build();

        System.out.println("\n--- Financial Report ---");
        System.out.println("Income : " + report.getTotalIncome());
        System.out.println("Expense: " + report.getTotalExpense());
        System.out.println("Savings: " + report.getSavings());
    }



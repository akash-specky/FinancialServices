package factory;

import services.TransactionService;
import services.TransactionServiceImpl;

public class FinanceManager {
    private static FinanceManager instance;

    private final TransactionService transactionService;

    private FinanceManager() {
        this.transactionService = new TransactionServiceImpl();
    }
    public static FinanceManager getInstance() {
        if (instance == null) {
            synchronized (FinanceManager.class) {
                    instance = new FinanceManager();
            }
        }
        return instance;
    }
    public TransactionService getTransactionService() {
        return transactionService;
    }
}

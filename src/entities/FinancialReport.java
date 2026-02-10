package entities;

public class FinancialReport {
    private double totalIncome;
    private double totalExpense;
    private double savings;

    private FinancialReport(){

    }

    static public class Builder {
        private double totalIncome;
        private double totalExpense;


        public Builder setTotalIncome(double totalIncome) {
            this.totalIncome = totalIncome;
            return this;
        }

        public Builder setTotalExpense(double totalExpense) {
            this.totalExpense = totalExpense;
            return this;
        }
        public FinancialReport build(){
            FinancialReport report = new FinancialReport();
            report.totalIncome = this.totalIncome;
            report.totalExpense = this.totalExpense;
            report.savings = totalIncome - totalExpense;
            return report;
        }
    }
}

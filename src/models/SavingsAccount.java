package models;

public class SavingsAccount extends Account {

    public SavingsAccount(String accountNumber, double balance, int customerId) {
        super(accountNumber, balance, customerId);
    }

    @Override
    public String getAccountType() {
        return "SAVINGS";
    }
}
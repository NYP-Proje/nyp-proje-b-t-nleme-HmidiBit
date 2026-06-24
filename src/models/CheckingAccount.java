package models;

public class CheckingAccount extends Account {

    public CheckingAccount(String accountNumber, double balance, int customerId) {
        super(accountNumber, balance, customerId);
    }

    @Override
    public String getAccountType() {
        return "CHECKING";
    }
}
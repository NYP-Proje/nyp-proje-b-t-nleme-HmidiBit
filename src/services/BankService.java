package services;

import models.Account;
import models.CheckingAccount;
import models.Customer;
import models.SavingsAccount;
import repo.AccountRepository;
import repo.CustomerRepository;

import java.sql.SQLException;
import java.util.List;

public class BankService {
    private final CustomerRepository customerRepo = new CustomerRepository();
    private final AccountRepository accountRepo = new AccountRepository();

    public void registerCustomer(String firstName, String lastName, String tcNo, String email) throws Exception {
        if (tcNo == null || tcNo.length() != 11) {
            throw new IllegalArgumentException("Hata: T.C. Kimlik Numarası mutlaka 11 haneli olmalıdır!");
        }
        if (firstName == null || firstName.trim().isEmpty() || lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("Hata: İsim ve soyisim alanları boş bırakılamaz!");
        }

        Customer customer = new Customer(firstName, lastName, tcNo, email);
        customerRepo.create(customer);
    }

    public List<Customer> getAllCustomers() throws SQLException {
        return customerRepo.findAll();
    }

    public void openAccount(String accNumber, double initialBalance, String type, int customerId) throws Exception {
        if (initialBalance < 0) {
            throw new IllegalArgumentException("Hata: Başlangıç bakiyesi sıfırdan küçük olamaz!");
        }
        if (customerRepo.findById(customerId) == null) {
            throw new IllegalArgumentException("Hata: Belirtilen ID'ye sahip bir müşteri bulunamadı!");
        }

        Account account;
        if ("SAVINGS".equalsIgnoreCase(type)) {
            account = new SavingsAccount(accNumber, initialBalance, customerId);
        } else {
            account = new CheckingAccount(accNumber, initialBalance, customerId);
        }
        accountRepo.create(account);
    }

    public void deposit(String accountNumber, double amount) throws Exception {
        Account acc = accountRepo.findByAccountNumber(accountNumber);
        if (acc == null) {
            throw new IllegalArgumentException("Hata: Hesap bulunamadı!");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Hata: Yatırılacak miktar sıfırdan büyük olmalıdır!");
        }

        double newBalance = acc.getBalance() + amount;
        accountRepo.updateBalance(acc.getId(), newBalance);
    }

    public void withdraw(String accountNumber, double amount) throws Exception {
        Account acc = accountRepo.findByAccountNumber(accountNumber);
        if (acc == null) {
            throw new IllegalArgumentException("Hata: Hesap bulunamadı!");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Hata: Çekilecek miktar sıfırdan büyük olmalıdır!");
        }
        if (acc.getBalance() < amount) {
            throw new IllegalArgumentException("Hata: Yetersiz bakiye! Hesabınızda sadece " + acc.getBalance() + " TL var.");
        }

        double newBalance = acc.getBalance() - amount;
        accountRepo.updateBalance(acc.getId(), newBalance);
    }

    public void transfer(String fromAccNum, String toAccNum, double amount) throws Exception {
        Account fromAcc = accountRepo.findByAccountNumber(fromAccNum);
        Account toAcc = accountRepo.findByAccountNumber(toAccNum);

        if (fromAcc == null || toAcc == null) {
            throw new IllegalArgumentException("Hata: Kaynak veya hedef hesap bulunamadı!");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Hata: Transfer miktarı sıfırdan büyük olmalıdır!");
        }
        if (fromAcc.getBalance() < amount) {
            throw new IllegalArgumentException("Hata: Transfer başarısız! Kaynak hesapta yetersiz bakiye.");
        }

        accountRepo.updateBalance(fromAcc.getId(), fromAcc.getBalance() - amount);
        accountRepo.updateBalance(toAcc.getId(), toAcc.getBalance() + amount);
    }

    public List<Account> getCustomerAccounts(int customerId) throws SQLException {
        return accountRepo.findByCustomerId(customerId);
    }
}
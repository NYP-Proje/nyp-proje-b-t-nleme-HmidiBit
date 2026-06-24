package repo;

import models.Account;
import models.CheckingAccount;
import models.SavingsAccount;
import util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountRepository {
    private final Connection conn = DBConnection.getConnection();

    public void create(Account account) throws SQLException {
        String sql = "INSERT INTO accounts (account_number, balance, account_type, customer_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, account.getAccountNumber());
            stmt.setDouble(2, account.getBalance());
            stmt.setString(3, account.getAccountType());
            stmt.setInt(4, account.getCustomerId());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    account.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    public Account findByAccountNumber(String accNum) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE account_number = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, accNum);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapAccount(rs);
                }
            }
        }
        return null;
    }

    public List<Account> findByCustomerId(int customerId) throws SQLException {
        List<Account> list = new ArrayList<>();
        String sql = "SELECT * FROM accounts WHERE customer_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapAccount(rs));
                }
            }
        }
        return list;
    }

    public void updateBalance(int id, double newBalance) throws SQLException {
        String sql = "UPDATE accounts SET balance = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, newBalance);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        }
    }

    private Account mapAccount(ResultSet rs) throws SQLException {
        String type = rs.getString("account_type");
        Account acc;

        if ("SAVINGS".equals(type)) {
            acc = new SavingsAccount(rs.getString("account_number"), rs.getDouble("balance"), rs.getInt("customer_id"));
        } else {
            acc = new CheckingAccount(rs.getString("account_number"), rs.getDouble("balance"), rs.getInt("customer_id"));
        }
        acc.setId(rs.getInt("id"));
        return acc;
    }
}
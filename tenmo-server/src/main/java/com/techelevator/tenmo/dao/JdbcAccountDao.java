package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exception.DaoException;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
@Component
public class JdbcAccountDao implements AccountDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Account updateAccountBalance(Account accountToUpdate) {
        int accountId = accountToUpdate.getAccount_id();
        String sql = "UPDATE account SET balance = ? WHERE account_id = ?";
        try {
            jdbcTemplate.update(sql, accountToUpdate.getBalance(), accountId);
        } catch (CannotGetJdbcConnectionException | DataIntegrityViolationException e) {
            throw new DaoException("Unable to update account balance", e);
        }
        return accountToUpdate;
    }

    @Override
    public Account getAccountById(int accountId) {
        Account account = null;
        String sql = "SELECT * from account " +
                "WHERE account_id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountId);
            if (results.next()) {
                account = mapToAccount(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return account;
    }

    @Override
    public Account getAccountByUserId(int userId) {
        Account account = null;
        String sql = "select account_id, user_id, balance from account where user_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
            while (results.next()) {
                account = mapToAccount(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return account;
    }
    @Override
    public BigDecimal getAccountBalance(int userId) {
        BigDecimal balance = null;
        String sql = "SELECT balance from account " +
                "WHERE user_id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
            if (results.next()) {
                balance = results.getBigDecimal("balance");
            }

        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return balance;

    }


    private Account mapToAccount(SqlRowSet sqlRowSet) {
        Account account = new Account();
        account.setAccount_id(sqlRowSet.getInt("account_id"));
        account.setBalance(sqlRowSet.getBigDecimal("balance"));
        account.setUser_id(sqlRowSet.getInt("user_id"));
        return account;
    }
}

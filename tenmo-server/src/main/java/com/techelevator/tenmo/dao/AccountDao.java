package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.math.BigDecimal;

public interface AccountDao {
    Account updateAccountBalance(Account accountToUpdate);

    Account getAccountById(int id);

    Account getAccountByUserId(int userId);
    BigDecimal getAccountBalance(int userId);
}

package com.techelevator.dao;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.model.Account;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MemoryAccountDao implements AccountDao {
    private List<Account> accounts = new ArrayList<>();

    @Override
    public Account updateAccountBalance(Account accountToUpdate) {
        return null;
    }

    @Override
    public Account getAccountById(int id) {
        return null;
    }

    @Override
    public Account getAccountByUserId(int userId) {
        return null;
    }

    @Override
    public BigDecimal getAccountBalance(int userId) {
        return null;
    }

    private void setAccounts() {
        accounts.add(new Account(2001,
                1001,
                BigDecimal.valueOf(120.00)));
        accounts.add(new Account(2002,
                1002,
                BigDecimal.valueOf(1000.00)));
    }
}

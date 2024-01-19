package com.techelevator.dao;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.model.Account;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import java.math.BigDecimal;
import java.util.List;

public class JdbcAccountDaoTests extends BaseDaoTests {

    protected static final Account ACCOUNT_1 = new Account(2004, 1001, BigDecimal.valueOf(1500));
    protected static final Account ACCOUNT_2 = new Account(2002, 1002, BigDecimal.valueOf(450));
    private static final Account ACCOUNT_3 = new Account(2003, 1003, BigDecimal.valueOf(10));
    private AccountDao sut;

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcAccountDao(jdbcTemplate);
    }

    @Test
    public void getAccountById_given_invalid_id_returns_null() {
        Account account = sut.getAccountById(1000);
        Assert.assertNull(account);
    }

    @Test
    public void getAccountById_given_correct_id_returns_account() {
        Account account = sut.getAccountById(ACCOUNT_3.getAccount_id());
        Assert.assertEquals(ACCOUNT_3, account);
    }

    @Test
    public void getAccountByUserId_given_invalid_id_returns_null() {
        Account account = sut.getAccountByUserId(2000);
        Assert.assertNull(account);
    }

    @Test
    public void getAccountByUserId_given_correct_id_returns_account() {
        Account account = sut.getAccountByUserId(ACCOUNT_1.getUser_id());
        Assert.assertEquals(ACCOUNT_1,account);
    }

    @Test
    public void getAccountBalance_given_invalid_id_returns_null(){
        BigDecimal accountBalance = sut.getAccountBalance(2002);
        Assert.assertNull(accountBalance);
    }

    @Test
    public void getAccountBalance_given_correct_id_returns_balance() {
        BigDecimal accountBalance = sut.getAccountBalance(ACCOUNT_2.getUser_id());
        int comparisonResult = ACCOUNT_2.getBalance().compareTo(accountBalance);
        Assert.assertEquals(0, comparisonResult);
    }



//    @Test
//    public void updateAccountBalance_CorrectBalance() {
//        sut.updateAccountBalance();
//    }

}

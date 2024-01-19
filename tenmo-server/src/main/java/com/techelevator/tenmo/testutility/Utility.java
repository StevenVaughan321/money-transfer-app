package com.techelevator.tenmo.testutility;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class Utility {
    public static void main(String[] args) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/tenmo");

        // Set connection credentials
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres1");
        JdbcTemplate template = new JdbcTemplate(dataSource);

        AccountDao accountDao = new JdbcAccountDao(template);
        Account testAccount = accountDao.getAccountByUserId(1001);
        System.out.println(testAccount.getBalance());
    }
}

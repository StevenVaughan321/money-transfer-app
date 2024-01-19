package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {
    private final UserDao userDao;
    private final AccountDao accountDao;
    private final TransferDao transferDao;

    public AccountController(UserDao userDao, AccountDao accountDao, TransferDao transferDao) {
        this.userDao = userDao;
        this.accountDao = accountDao;
        this.transferDao = transferDao;
    }
    @RequestMapping(path = "/balance", method = RequestMethod.GET)
    public BigDecimal getAccountBalance(Principal principal){
        User loggedInUser = userDao.getUserByUsername(principal.getName());
        return accountDao.getAccountBalance(loggedInUser.getId());
    }
    @RequestMapping(path = "/transfers", method = RequestMethod.GET)
    public List<Transfer> getTransfers(Principal principal) {
        User loggedInUser = userDao.getUserByUsername(principal.getName());
        return transferDao.getTransfersForUser(loggedInUser.getId());
    }

}

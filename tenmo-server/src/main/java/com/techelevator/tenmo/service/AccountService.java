package com.techelevator.tenmo.service;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.stereotype.Component;

public interface AccountService {
    public void transferFunds(Transfer transfer);
}

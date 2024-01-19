package com.techelevator.tenmo.service;

import com.techelevator.tenmo.dto.NewTransferDto;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;

public interface TransferService {
    Transfer initializeTransfer(User loggedInUser, NewTransferDto newTransferDto);
    Transfer acceptTransfer(User loggedInUser, Long transferId);
    Transfer rejectTransfer(User loggedInUser, Long transferId);
}


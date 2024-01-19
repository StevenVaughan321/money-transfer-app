package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.dto.TransferDto;
import com.techelevator.tenmo.dto.TransferStatusUpdateDto;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;

public class TransferService extends AuthTokenService{
    public TransferService(String baseUrl) {
        this.baseUrl = baseUrl + "transfers/";
    }
    public Transfer createTransfer(TransferDto dto) {
       Transfer transfer = null;
       try {
           ResponseEntity<Transfer> response =
                   restTemplate.exchange(baseUrl, HttpMethod.POST, makeTransferDtoEntity(dto),  Transfer.class);
           transfer = response.getBody();
       } catch (RestClientResponseException e) {
           BasicLogger.log(e.getMessage());
       } catch (ResourceAccessException e) {
           BasicLogger.log(e.getMessage());
       } return transfer;
    }

    public Transfer updatePendingTransferStatus(int transferId, int status) {
        Transfer transfer = null;
        try {
            ResponseEntity<Transfer> response = restTemplate.exchange(baseUrl + transferId, HttpMethod.PUT, makeTransferStatusEntity(status), Transfer.class);
            transfer = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            e.getMessage();
        }
        return transfer;
    }
    public Transfer getTransferDetails(int transferId) {
        Transfer transfer = null;
        try {
            ResponseEntity<Transfer> response = restTemplate.exchange(baseUrl + transferId, HttpMethod.GET, makeAuthEntity(), Transfer.class);
            transfer = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            e.getMessage();
        }
        return transfer;
    }

    //Originally used TransferStatusUpdateDto, could not make it work. Integer works so ¯\_(ツ)_/¯
    private HttpEntity<Integer> makeTransferStatusEntity(Integer status) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(status, headers);
    }

    private HttpEntity<TransferDto> makeTransferDtoEntity(TransferDto transfer) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(transfer, headers);
    }

    public Transfer[] retrievePendingTransfers(){
        Transfer[] transfers = null;
        try {
            ResponseEntity<Transfer[]> response = restTemplate.exchange(baseUrl + "pending",
                    HttpMethod.GET, makeAuthEntity(), Transfer[].class);
            transfers = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            e.getMessage();
        }
        return transfers;
    }
}

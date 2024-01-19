package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;

import java.math.BigDecimal;

public class AccountService extends AuthTokenService{
    public AccountService(String baseUrl) {
        this.baseUrl = baseUrl + "account/";
    }
    public BigDecimal getBalance() {
        BigDecimal balance = null;
        try {
            ResponseEntity<BigDecimal> response = restTemplate.exchange(baseUrl + "balance",
                    HttpMethod.GET, makeAuthEntity(), BigDecimal.class );
            balance = response.getBody();
        } catch (RestClientResponseException e) {
            e.getMessage();
        } catch (ResourceAccessException e) {
            e.getMessage();
        } return balance;
    }

    public Transfer[] retrieveAllTransfers() {
        Transfer[] transfers = null;
        try {
            ResponseEntity<Transfer[]> response = restTemplate.exchange(baseUrl + "transfers",
                    HttpMethod.GET, makeAuthEntity(), Transfer[].class);
            transfers = response.getBody();
        }  catch (RestClientResponseException | ResourceAccessException e) {
            e.getMessage();
        }
        return transfers;
    }
}

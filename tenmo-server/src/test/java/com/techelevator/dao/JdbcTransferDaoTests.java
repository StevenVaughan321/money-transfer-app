package com.techelevator.dao;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.model.Transfer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.util.List;

public class JdbcTransferDaoTests extends BaseDaoTests{
    //user id 1001 = account 2001
    //user id 1002 = account 2002 ect
    protected static final Transfer TRANSFER_1 = new Transfer(1001, 1, 1, 2004, 2002,  BigDecimal.valueOf(500));
    protected static final Transfer TRANSFER_2 = new Transfer(1002, 1, 1,  2002, 2004,  BigDecimal.valueOf(500));
    protected static final Transfer TRANSFER_3 = new Transfer(1003, 1, 2,  2002, 2004,  BigDecimal.valueOf(500));
    protected static final Transfer TRANSFER_4 = new Transfer( 2, 1,  2002, 2004,  BigDecimal.valueOf(1000));


    private JdbcTransferDao sut;

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcTransferDao(jdbcTemplate);
    }
    @Test
    public void getTransferById_given_invalid_id_returns_null(){
       Transfer transfer = sut.getTransferById(-1);
        Assert.assertNull(transfer);
    }
    @Test
    public void getTransfers_returns_all_transfers() {
        List<Transfer> transfers = sut.findAll();
        Assert.assertNotNull(transfers);
        Assert.assertEquals(3, transfers.size());
        Assert.assertEquals(TRANSFER_1, transfers.get(0));
        Assert.assertEquals(TRANSFER_2, transfers.get(1));

        Assert.assertEquals(TRANSFER_3, transfers.get(2));
    }
    @Test
    public void getTransfersForUser_given_correct_id() {
        List<Transfer> transfers = sut.getTransfersForUser(1002);
        Assert.assertNotNull(transfers);
        Assert.assertEquals(3, transfers.size());
    }
    @Test
    public void getPendingTransferForUser_given_correct_Id() {
        List<Transfer> transfers = sut.getPendingTransfersForUser(1002);
        Assert.assertNotNull(transfers);
        Assert.assertEquals(2, transfers.size());
        Assert.assertEquals(TRANSFER_1, transfers.get(0));
        Assert.assertEquals(TRANSFER_2, transfers.get(1));
    }
    @Test
    public void addTransfer() {
        Transfer transfer = sut.addTransfer(TRANSFER_4);
        System.out.println(transfer.getId());
        TRANSFER_4.setId(transfer.getId());
        Assert.assertNotNull(transfer);
        Assert.assertEquals(TRANSFER_4, transfer);
    }
}


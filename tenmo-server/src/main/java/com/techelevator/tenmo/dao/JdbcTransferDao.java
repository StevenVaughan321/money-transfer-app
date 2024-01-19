package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exception.DaoException;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferStatus;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao{

    private final JdbcTemplate jdbcTemplate;
    private static final String SQL_SELECT_TRANSFER = "SELECT t.transfer_id, t.transfer_type_id, t.transfer_status_id, t.amount, " +
            "aFrom.account_id AS fromAccount, aFrom.user_id AS fromUser, aFrom.balance AS fromBalance,  " +
            "aTo.account_id As toAccount, aTo.user_id AS toUser, aTo.balance AS toBalance " +
            "FROM transfer t " +
            "JOIN account aFrom ON t.account_from = aFrom.account_id " +
            "JOIN account aTo ON t.account_to = aTo.account_id ";


    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Transfer> findAll() {
        List<Transfer> transfers = new ArrayList<>();
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(SQL_SELECT_TRANSFER);
            while (results.next()) {
                Transfer transfer = mapToTransfer(results);
                transfers.add(transfer);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        } return transfers;
    }

    @Override
    public List<Transfer> getPendingTransfersForUser(int userId){
        List<Transfer> transfers = new ArrayList<>();
        String sql = SQL_SELECT_TRANSFER +
                "WHERE (account_from IN (SELECT account_id FROM account WHERE user_id = ?) " +
                "OR  account_to IN (SELECT account_id FROM account WHERE user_id = ?))" +
                "AND transfer_status_id = ?;";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId, userId, TransferStatus.PENDING);
            while (results.next()) {
                Transfer transfer = mapToTransfer(results);
                transfers.add(transfer);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return transfers;
    }

    @Override
    public List<Transfer> getTransfersForUser(int userId) {
        List<Transfer> transfers = new ArrayList<>();
        String sql = SQL_SELECT_TRANSFER +
                "WHERE (account_from IN (SELECT account_id FROM account WHERE user_id = ?) " +
                "OR  account_to IN (SELECT account_id FROM account WHERE user_id = ?));";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId, userId);
            while (results.next()) {
                Transfer transfer = mapToTransfer(results);
                transfers.add(transfer);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        } return transfers;
    }

    @Override
   public Transfer addTransfer(Transfer newtransfer) {

    String sql = "INSERT INTO transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
            "VALUES ( ?, ?, ?, ?, ?) RETURNING transfer_id;";
        try {
            long newTransferId = jdbcTemplate.queryForObject(sql, Long.class,  newtransfer.getTransferType(), newtransfer.getTransferStatus(), newtransfer.getAccountFrom(), newtransfer.getAccountTo(), newtransfer.getAmount());
            return getTransferById(newTransferId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation" + newtransfer.toString(), e);
        }  catch  (DataAccessException e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public Transfer updateTransferStatus(long transferId, int newStatus) {
        Transfer updatedTransfer = null;
        String sql = "UPDATE transfer SET transfer_status_id = ? WHERE transfer_id = ?;";
        try {
            int numberOfRows = jdbcTemplate.update(sql, newStatus, transferId);
            if (numberOfRows == 0) {
                throw new DaoException("Zero rows were affected");
            } else {
            updatedTransfer = getTransferById(transferId);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        } return updatedTransfer;
    }

    @Override
    public Transfer getTransferById(long transferId) {
        Transfer transfer = null;
        String sql = SQL_SELECT_TRANSFER +
                "WHERE transfer_id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferId);
            if (results.next()) {
                transfer = mapToTransfer(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        } return transfer;
    }
    private Transfer mapToTransfer(SqlRowSet sqlRowSet) {
        Transfer transfer = new Transfer();
        transfer.setTransferStatus(sqlRowSet.getInt("transfer_status_id"));
        transfer.setTransferType(sqlRowSet.getInt("transfer_type_id"));
        transfer.setAccountFrom((sqlRowSet.getInt("fromAccount")));
        transfer.setAccountTo(sqlRowSet.getInt("toAccount"));
        transfer.setAmount(sqlRowSet.getBigDecimal("amount"));
        transfer.setId(sqlRowSet.getLong("transfer_id"));
        return transfer;
    }
}

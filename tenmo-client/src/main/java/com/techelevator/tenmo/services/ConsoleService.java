package com.techelevator.tenmo.services;


import com.techelevator.tenmo.model.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.List;

public class ConsoleService {
    private UserService userService;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleService(UserService userService){
        this.userService = userService;
    }

    public int promptForMenuSelection(String prompt) {
        int menuSelection;
        System.out.print(prompt);
        try {
            menuSelection = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            menuSelection = -1;
        }
        return menuSelection;
    }

    public void printGreeting() {
        System.out.println("*********************");
        System.out.println("* Welcome to TEnmo! *");
        System.out.println("*********************");
    }

    public void printLoginMenu() {
        System.out.println();
        System.out.println("1: Register");
        System.out.println("2: Login");
        System.out.println("0: Exit");
        System.out.println();
    }

    public void printMainMenu() {
        System.out.println();
        System.out.println("1: View your current balance");
        System.out.println("2: View your past transfers");
        System.out.println("3: View your pending requests");
        System.out.println("4: Send TE bucks");
        System.out.println("5: Request TE bucks");
        System.out.println("0: Exit");
        System.out.println();
    }

    public UserCredentials promptForCredentials() {
        String username = promptForString("Username: ");
        String password = promptForString("Password: ");
        return new UserCredentials(username, password);
    }

    public String promptForString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public int promptForInt(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }

    public BigDecimal promptForBigDecimal(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return new BigDecimal(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a decimal number.");
            }
        }
    }

    public void pause() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    public void printAcceptRejectMenu() {
        System.out.println();
        System.out.println("1: Approve");
        System.out.println("2: Reject");
        System.out.println("0: Don't approve or reject");
        printSeparator(9);
    }

    public void printErrorMessage() {
        System.out.println("An error occurred. Check the log for details.");
    }
    public void printTransferMenu(Transfer[] transfers, User currentUser) {
        List<String> ids = new ArrayList<>();
        List<String> names = new ArrayList<>();
        List<String> amounts = new ArrayList<>();

        for (Transfer transfer: transfers) {
            ids.add(String.valueOf(transfer.getId()));
            names.add(buildToFromString(transfer, currentUser));
            amounts.add("$"+String.valueOf(transfer.getAmount()));
        }

        List<MenuColumn> columns = new ArrayList<>();
        columns.add(new MenuColumn("ID", 12, ids));
        columns.add(new MenuColumn("From/To", 24, names));
        columns.add(new MenuColumn("Amount", 24, amounts));

        printTable("Transfers", columns);

    }
    private String buildToFromString(Transfer transfer, User user) {
        String toFromString = null;
        if(user.getUsername().equals(userService.getUserByAccountId(transfer.getAccountTo()).getUsername())) {
            toFromString = "From: " + userService.getUserByAccountId(transfer.getAccountFrom()).getUsername();
        } else {
            toFromString = "To: "+userService.getUserByAccountId(transfer.getAccountTo()).getUsername();
        }
        return toFromString;
    }

    public void printPendingTransferMenu(Transfer[] transfers, User currentUser) {
        List<String> ids = new ArrayList<>();
        List<String> names = new ArrayList<>();
        List<String> amounts = new ArrayList<>();

        for (Transfer transfer: transfers) {
                ids.add(String.valueOf(transfer.getId()));
                names.add(userService.getUserByAccountId(transfer.getAccountTo()).getUsername());
                amounts.add("$"+String.valueOf(transfer.getAmount()));
        }

        List<MenuColumn> columns = new ArrayList<>();
        columns.add(new MenuColumn("ID", 12, ids));
        columns.add(new MenuColumn("To", 12, names));
        columns.add(new MenuColumn("Amount", 24, amounts));

        printTable("Pending Transfers", columns);
    }

    public void printUserMenu(User[] users) {
        List<String> ids = new ArrayList<>();
        List<String> names = new ArrayList<>();

        for (User user : users){
            ids.add(String.valueOf(user.getId()));
            names.add(user.getUsername());
        }

        List<MenuColumn> columns = new ArrayList<>();
        columns.add(new MenuColumn("ID", 12, ids));
        columns.add(new MenuColumn("NAME", 12, names));

        printTable("USERS", columns);

    }

    private void printTable(String menuTitle, List<MenuColumn> columns){
        StringBuilder titleRow = new StringBuilder();
        List<StringBuilder> rows = getTableRows(titleRow, columns);

        printSeparator(43);
        System.out.println(menuTitle);
        System.out.println(titleRow);
        printSeparator(43);
        for (StringBuilder row : rows){
            System.out.println(row);
        }
        printSeparator(9);
    }

    private List<StringBuilder> getTableRows(StringBuilder titleRow, List<MenuColumn> columns){
        int numberOfRows = columns.get(0).getNumberOfRows();
        List<StringBuilder> rows = new ArrayList<>();

        for (int i = 0; i < numberOfRows; i++){
            rows.add(new StringBuilder());
        }

        for (MenuColumn column : columns){
            titleRow.append(column.getTitle());
            String whitespace = getWhitespace(column.getWhiteSpaceLength());
            titleRow.append(whitespace);

            for (int i = 0; i < column.getNumberOfRows(); i++){
                String rowContent = column.getRows().get(i);
                StringBuilder menuRow = rows.get(i);

                menuRow.append(rowContent);
                whitespace = getWhitespace(column.getWhiteSpaceLength(i));
                menuRow.append(whitespace);
            }
        }
        return rows;
    }

    //used to make the --------- is case formatting
    public void printSeparator(int length) {
        System.out.println(getRepeatedCharacter('-', length));
    }

    public String getWhitespace(int length){
        return getRepeatedCharacter(' ', length);
    }

    public String getRepeatedCharacter(char character, int repeats){
        StringBuilder repeatedCharacter = new StringBuilder();
        for (int i = 0; i < repeats; i++) {
            repeatedCharacter.append(character);
        }
        return repeatedCharacter.toString();
    }
    public void printTransferDetails(Transfer transfer ) {
        getRepeatedCharacter('-', 20);
        System.out.println("Transfer Details");
        getRepeatedCharacter('-', 20);
        System.out.println("Id: "+transfer.getId());
        System.out.println("From: "+ userService.getUserByAccountId(transfer.getAccountFrom()).getUsername());
        System.out.println("To: "+ userService.getUserByAccountId(transfer.getAccountTo()).getUsername());
        System.out.println("Type: " + (transfer.getTransferType() == TransferType.REQUEST ? "REQUEST" : "SEND"));
        System.out.println("Status: "+ getTransferStatus(transfer.getTransferStatus()));
        System.out.println("Amount: "+transfer.getAmount());
    }
    private String getTransferStatus(int transferStatusId) {
        String status;
        switch (transferStatusId) {
            case 1 : status = "pending";
            break;
            case 2 : status = "accepted";
                break;
            case 3 : status = "rejected";
                break;
            default : status = "invalid transfer status";
                break;
        } return status;
    }
}

package com.techelevator.tenmo.model;

import java.util.List;

public class MenuColumn {
    private String title;
    private int width;
    private List<String> rows;

    public MenuColumn(String title, int width, List<String> rows) {
        this.title = title;
        this.width = width;
        this.rows = rows;
    }

    public String getTitle() {
        return title;
    }

    public int getWidth() {
        return width;
    }

    public List<String> getRows() {
        return rows;
    }

    public int getNumberOfRows(){
        return rows.size();
    }

    public int getWhiteSpaceLength(int rowIndex){
        String row = rows.get(rowIndex);
        int length = width-row.length();
        return Math.max(length, 0);
    }

    public int getWhiteSpaceLength(){
        int length = width-title.length();
        return Math.max(length, 0);
    }
}

package mg.itu.m1p10android.data.http;

import java.util.ArrayList;

public class SqliteResultSet {
    private String[] columns;
    private  ArrayList[] rows;
    private Integer rowsAffected;
    private Long lastInsertedId;
    public SqliteResultSet(){}

    public String[] getColumns() {
        return columns;
    }

    public void setColumns(String[] columns) {
        this.columns = columns;
    }

    public ArrayList[] getRows() {
        return rows;
    }

    public void setRows(ArrayList[] rows) {
        this.rows = rows;
    }

    public Integer getRowsAffected() {
        return rowsAffected;
    }

    public void setRowsAffected(Integer rowsAffected) {
        this.rowsAffected = rowsAffected;
    }

    public Long getLastInsertedId() {
        return lastInsertedId;
    }

    public void setLastInsertedId(Long lastInsertedId) {
        this.lastInsertedId = lastInsertedId;
    }

}

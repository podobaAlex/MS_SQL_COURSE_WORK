package database;

import javax.swing.*;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataBaseTable {

    private Object[][] data;
    private String[] columnName;

    public JTable DBTable;

    public DataBaseTable(Object[][] table, String[] columnName) {
        this.data = table;

    }

}

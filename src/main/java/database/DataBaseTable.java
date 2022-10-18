package database;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataBaseTable extends JPanel {

    private Object[][] data;
    private String[] columnName;
    private JTable DBTable;

    public DataBaseTable(Object[][] data, String[] columnName) {
        this.data = data;
        this.columnName = columnName;
        initTable();
    }

    private void initTable() {
        DBTable = new JTable(this.data, this.columnName);
        DBTable.setPreferredScrollableViewportSize(new Dimension(500, 500));
        DBTable.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(DBTable);
        add(scrollPane);

    }

    private void back() {
        AdminMenu dialog = new AdminMenu();
        dialog.pack();
        dialog.setVisible(true);
    }

}

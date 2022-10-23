package database;

import database.activities.admin.AdminMenuActivity;
import database.activities.user.UserMenuActivity;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DataBaseTable extends JPanel {
    private final Object[][] data;
    private final String[] columnName;
    private JTable DBTable;
    private DefaultTableModel model;

    public DataBaseTable(Object[][] data, String[] columnName) {
        this.data = data;
        this.columnName = columnName;

        setLayout(new BorderLayout());

        initTable();
    }

    private void initTable() {
        model = new DefaultTableModel(this.data, this.columnName);
        DBTable = new JTable(model);
        DBTable.setPreferredScrollableViewportSize(new Dimension(500, 300));
        DBTable.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(DBTable);
        add(scrollPane, BorderLayout.CENTER);

    }

    public int[] getSelected() {
        return DBTable.getSelectedRows();
    }

    public void removeSelected(int selected) {

        model.removeRow(selected);

    }

    public String getInfo(int row, int column) {
        return (String) data[row][column];
    }
}

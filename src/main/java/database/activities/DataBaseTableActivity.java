package database.activities;

import database.Main;
import database.activities.admin.AdminMenuActivity;
import database.activities.user.UserMenuActivity;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;

public class DataBaseTableActivity extends JPanel {

    private final Object[][] data;
    private final String[] columnName;
    private JTable DBTable;
    private final JButton backButton = new JButton("Back");
    private DefaultTableModel model;

    public DataBaseTableActivity(Object[][] data, String[] columnName) {
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

        backButton.addActionListener(e -> onBack());

        JScrollPane scrollPane = new JScrollPane(DBTable);
        add(scrollPane, BorderLayout.CENTER);
        add(backButton, BorderLayout.SOUTH);

    }

    private void onBack() {
        JPanel pane;
        switch (Main.getCurrentRole()) {
            case Main.ADMIN_ROLE:
                pane = new AdminMenuActivity();
                break;
            //case Main.AGENT_ROLE:
                //TODO
                //break;
            default:
                pane = new UserMenuActivity();
                break;
        }
        Main.frame.setContentPane(pane);
        Main.frame.setVisible(true);
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

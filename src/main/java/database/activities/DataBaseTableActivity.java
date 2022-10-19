package database.activities;

import database.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DataBaseTableActivity extends JPanel {

    private final Object[][] data;
    private final String[] columnName;
    private JTable DBTable;
    private final JButton backButton = new JButton("Back");

    public DataBaseTableActivity(Object[][] data, String[] columnName) {
        this.data = data;
        this.columnName = columnName;
        initTable();
    }

    private void initTable() {
        DBTable = new JTable(this.data, this.columnName);
        DBTable.setPreferredScrollableViewportSize(new Dimension(500, 300));
        DBTable.setFillsViewportHeight(true);

        backButton.addActionListener(e -> onBack());

        JScrollPane scrollPane = new JScrollPane(DBTable);
        add(scrollPane);
        add(backButton);

    }

    private void onBack() {
        Main.frame.setContentPane(new AdminMenuActivity());
        Main.frame.pack();
    }

}

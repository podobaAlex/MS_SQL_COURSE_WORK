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
    private JButton backButton;

    public DataBaseTable(Object[][] data, String[] columnName) {
        this.data = data;
        this.columnName = columnName;
        initTable();
    }

    private void initTable() {
        DBTable = new JTable(this.data, this.columnName);
        DBTable.setPreferredScrollableViewportSize(new Dimension(500, 300));
        DBTable.setFillsViewportHeight(true);

        backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {onBack();}
        });

        JScrollPane scrollPane = new JScrollPane(DBTable);
        add(scrollPane);
        add(backButton);

    }

    private void onBack() {
        Main.frame.setContentPane(new Menu());
        Main.frame.pack();
    }

}

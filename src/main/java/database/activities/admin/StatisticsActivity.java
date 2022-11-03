package database.activities.admin;

import database.DataBaseTable;
import database.Main;
import database.dialogs.AddAgentDialog;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Vector;

public class StatisticsActivity extends JPanel {

    private final JButton addProductToAgentButton = new JButton("Закрепить продукт");
    private final JButton changeSalaryButton = new JButton("Изменить зарплату");
    private final JButton addAgentButton = new JButton("Нанять агента");
    private final JButton fireAgentButton = new JButton("Уволить");
    private final JButton backButton = new JButton("Назад");

    private DataBaseTable dbTable;

    public StatisticsActivity() throws SQLException {

        setLayout(new BorderLayout());
        initTable();
        initListeners();
        initButtonContainer();

    }

    private void initTable() throws SQLException {
        String[] columnsName = {"IdAgent", "FIO", "Salary", "RealizedOrders", "Cash","Orders"};
        String result = Main.sqlConnection.selectFunction("Exec Stat", columnsName.length);

        Object[][] res = Arrays.stream(
                result.split(";")
        ).map(
                i -> i.split("_")
        ).toArray(Object[][]::new);

        dbTable = new DataBaseTable(res, columnsName);
        add(dbTable, BorderLayout.CENTER);
    }

    private void initListeners() {
        addProductToAgentButton.addActionListener(e -> onAddProductToAgent());
        changeSalaryButton.addActionListener(e -> onChangeSalary());
        addAgentButton.addActionListener(e -> onAddAgent());
        fireAgentButton.addActionListener(e -> onFireAgent());
        backButton.addActionListener(e -> onBack());
    }

    private void initButtonContainer() {
        Container buttonContainer = new Container();
        buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.X_AXIS));
        buttonContainer.add(addProductToAgentButton);
        buttonContainer.add(changeSalaryButton);
        buttonContainer.add(addAgentButton);
        buttonContainer.add(fireAgentButton);
        buttonContainer.add(backButton);
        add(buttonContainer, BorderLayout.SOUTH);
    }

    private void onAddProductToAgent() {
        int[] selected = dbTable.getSelected();
        Arrays.stream(selected).forEach(i -> {
            JFrame frame = new JFrame();
            try {
                frame.setContentPane(new AddProductToAgentActivity(dbTable.getInfo(i, 0)));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            frame.setVisible(true);
            frame.setLocationRelativeTo(null);
            frame.pack();
        });
    }

    private void onChangeSalary() {
        Vector<Vector> data = dbTable.getData();
        data.forEach(i -> {
            try {
                Main.sqlConnection.insertFunction("Exec Change_Salary "
                        + i.get(0) + ","
                        + i.get(2));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

    }

    private void onAddAgent() {
        AddAgentDialog addAgentDialog = new AddAgentDialog(this.dbTable);
        addAgentDialog.pack();
        addAgentDialog.setVisible(true);
    }

    private void onFireAgent() {
        int[] selected = dbTable.getSelected();
        Arrays.stream(selected).forEach(i -> {
            try {
                Main.sqlConnection.insertFunction("Exec Del_Agent " + dbTable.getInfo(i,0));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            dbTable.removeSelected(i);
        });
    }

    private void onBack() {
        Main.frameAdmin.setContentPane(new AdminMenuActivity());
        Main.frameAdmin.setVisible(true);
    }

}

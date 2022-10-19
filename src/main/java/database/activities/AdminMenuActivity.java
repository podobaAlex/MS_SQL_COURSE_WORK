package database.activities;

import database.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Arrays;

public class AdminMenuActivity extends JPanel {

    private final Container container = new Container();

    private final JButton statisticButton = new JButton("Statistic");
    private final JButton catalogueButton = new JButton("Catalogue");
    private final JButton addAgentButton = new JButton("Add agent");
    private final JButton changeSalaryButton = new JButton("Change salary");

    public AdminMenuActivity() {
        initListeners();
        initContainer();

        add(container);
    }

    private void initListeners() {
        catalogueButton.addActionListener(e -> { try {showCatalogue();} catch (SQLException er) {er.printStackTrace();}});

        statisticButton.addActionListener(e -> { try {showStatistic();} catch (SQLException er) {er.printStackTrace();}});

        addAgentButton.addActionListener(e -> { try {addAgent();} catch (SQLException er) {er.printStackTrace();}});

        changeSalaryButton.addActionListener(e -> {
            try {
                changeSalary();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
    }

    private void initContainer() {
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        catalogueButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(catalogueButton);

        statisticButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(statisticButton);

        addAgentButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(addAgentButton);

        changeSalaryButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(changeSalaryButton);

    }

    private void addAgent() throws SQLException {
        AddAgentActivity agentActivity = new AddAgentActivity();
        Main.frame.setContentPane(agentActivity);
        Main.frame.pack();
        Main.frame.setVisible(true);
    }

    private void showStatistic() throws SQLException {
        String[] columnsName = {"IdAgent", "FIO", "Salary", "RealizedOrders", "Cash","Orders"};
        String result = Main.sqlConnection.selectFunction("Exec Stat", columnsName.length);

        Object[][] res = Arrays.stream(
                result.split(";")
        ).map(
                i -> i.split(",")
        ).toArray(Object[][]::new);

        DataBaseTableActivity tableFrame = new DataBaseTableActivity(res, columnsName);

        tableFrame.setOpaque(true);

        Main.frame.setContentPane(tableFrame);
        Main.frame.pack();
        Main.frame.setVisible(true);
    }

    private void showCatalogue() throws SQLException {

        String[] columnsName = {"IdProd", "Name", "Num"};
        String result = Main.sqlConnection.selectFunction("Exec Catalog_Prod", columnsName.length);

        Object[][] res = Arrays.stream(
                result.split(";")
        ).map(
                i -> i.split(",")
        ).toArray(Object[][]::new);

        DataBaseTableActivity tableFrame = new DataBaseTableActivity(res, columnsName);

        tableFrame.setOpaque(true);

        Main.frame.setContentPane(tableFrame);
        Main.frame.pack();
        Main.frame.setVisible(true);
    }

    private void changeSalary() throws SQLException {

        String result = Main.sqlConnection.selectFunction("Exec Catalog_Prod", 1);

        String[] res = Arrays.stream(
                result.split(";")
        ).map(x -> x.substring(0,x.length()-1)).toArray(String[]::new);

        ChangeSalaryActivity changeSalaryActivity = new ChangeSalaryActivity(res);

        changeSalaryActivity.setOpaque(true);

        Main.frame.setContentPane(changeSalaryActivity);
        Main.frame.pack();
        Main.frame.setVisible(true);
    }

}

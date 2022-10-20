package database.activities.admin;

import database.Main;
import database.activities.DataBaseTableActivity;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.Arrays;

public class AdminMenuActivity extends JPanel {

    private final Container container = new Container();

    private final JButton statisticButton = new JButton("Statistic");
    private final JButton catalogueButton = new JButton("Catalogue");
    private final JButton addAgentButton = new JButton("Add agent");
    private final JButton changeSalaryButton = new JButton("Change salary");
    private final JButton deleteAgentButton = new JButton("Delete agent");
    private final JButton buyProductButton = new JButton("Buy products");

    public AdminMenuActivity() {
        initListeners();
        initContainer();

        add(container);
    }

    private void initListeners() {
        catalogueButton.addActionListener(e -> { try {showCatalogue();} catch (SQLException er) {er.printStackTrace();}});
        statisticButton.addActionListener(e -> { try {showStatistic();} catch (SQLException er) {er.printStackTrace();}});
        addAgentButton.addActionListener(e -> { try {addAgent();} catch (SQLException er) {er.printStackTrace();}});
        changeSalaryButton.addActionListener(e -> { try {changeSalary();} catch (SQLException ex) {ex.printStackTrace();}});
        deleteAgentButton.addActionListener(e -> {try {deleteAgent();} catch (SQLException ex) {ex.printStackTrace();}});
        buyProductButton.addActionListener(e -> {try {buyProduct();} catch (SQLException ex) {ex.printStackTrace();}});
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

        deleteAgentButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(deleteAgentButton);

        buyProductButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(buyProductButton);

    }

    private void addAgent() throws SQLException {
        AddAgentActivity agentActivity = new AddAgentActivity();
        Main.frame.setContentPane(agentActivity);
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
        Main.frame.setVisible(true);
    }

    private void changeSalary() throws SQLException {

        String result = Main.sqlConnection.selectFunction("Exec Stat", 1);

        String[] res = Arrays.stream(
                result.split(";")
        ).map(x -> x.substring(0,x.length()-1)).toArray(String[]::new);

        ChangeSalaryActivity changeSalaryActivity = new ChangeSalaryActivity(res);

        changeSalaryActivity.setOpaque(true);

        Main.frame.setContentPane(changeSalaryActivity);
        Main.frame.setVisible(true);
    }

    private void deleteAgent() throws SQLException {

        String result = Main.sqlConnection.selectFunction("Exec Stat", 1);

        String[] res = Arrays.stream(
                result.split(";")
        ).map(x -> x.substring(0, x.length() - 1)).toArray(String[]::new);

        DeleteAgentActivity deleteAgentActivity = new DeleteAgentActivity(res);

        Main.frame.setContentPane(deleteAgentActivity);
        Main.frame.setVisible(true);

    }

    private void buyProduct() throws SQLException {

        BuyProductActivity buyProductActivity = new BuyProductActivity();

        Main.frame.setContentPane(buyProductActivity);
        Main.frame.setVisible(true);

    }

}
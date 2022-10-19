package database;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Arrays;

public class Menu extends JPanel {

    private final Container container = new Container();

    private final JButton statisticButton = new JButton("Statistic");
    private final JButton catalogueButton = new JButton("Catalogue");
    private final JButton addAgentButton = new JButton("Add agent");

    public Menu() {
        initListeners();
        initContainer();

        add(container);
    }

    private void initListeners() {
        catalogueButton.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e)  { try {showCatalogue();} catch (SQLException er) {er.printStackTrace();}}
        });

        statisticButton.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e)  { try {showStatistic();} catch (SQLException er) {er.printStackTrace();}}
        });

        addAgentButton.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e)  { try {addAgent();} catch (SQLException er) {er.printStackTrace();}}
        });
    }

    private void initContainer() {
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        catalogueButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(catalogueButton);

        statisticButton.setPreferredSize(new Dimension(50, 100));
        statisticButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(statisticButton);

        addAgentButton.setPreferredSize(new Dimension(50, 100));
        addAgentButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(addAgentButton);

    }

    private void addAgent() throws SQLException {
        addAgentActivity agentActivity = new addAgentActivity();
        Main.frame.setContentPane(agentActivity);
        Main.frame.pack();
        Main.frame.setVisible(true);
    }

    private void showStatistic() throws SQLException {
        String[] columnsName = {"IdAgent", "FIO", "Salary", "RealizedOrders", "Cash","Orders"};
        String result = null;
        result = Main.sqlConnection.selectFunction("Exec Stat", columnsName.length);

        Object[][] res = Arrays.stream(
                result.split(";")
        ).map(
                i -> i.split(",")
        ).toArray(Object[][]::new);

        DataBaseTable tableFrame = new DataBaseTable(res, columnsName);

        tableFrame.setOpaque(true);

        Main.frame.setContentPane(tableFrame);
        Main.frame.pack();
        Main.frame.setVisible(true);
    }

    private void showCatalogue() throws SQLException {

        String[] columnsName = {"IdProd", "Name", "Num"};
        String result = null;
        result = Main.sqlConnection.selectFunction("Exec Catalog_Prod", columnsName.length);

        Object[][] res = Arrays.stream(
                result.split(";")
        ).map(
                i -> i.split(",")
        ).toArray(Object[][]::new);

        DataBaseTable tableFrame = new DataBaseTable(res, columnsName);

        tableFrame.setOpaque(true);

        Main.frame.setContentPane(tableFrame);
        Main.frame.pack();
        Main.frame.setVisible(true);
    }

}

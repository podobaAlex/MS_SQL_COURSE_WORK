package database.activities.agent;

import database.DataBaseTable;
import database.Main;
import database.activities.user.UserMenuActivity;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Objects;

public class ShowOrdersActivity extends JPanel {

    private DataBaseTable dbTable;

    private final JButton makeOrderButton = new JButton("Оформить заказ");
    private final JButton closeOrderButton = new JButton("Закрыть заказ");
    private final JButton cancelOrderButton = new JButton("Отменить выполнение заказа");
    private final JButton backButton = new JButton("Назад");

    public ShowOrdersActivity() throws SQLException {

        setLayout(new BorderLayout());
        initListeners();
        initButtonContainer();
        initTable();

    }

    private void initTable() throws SQLException {
        String[] columnsName = {"IdOrder", "IdAgent", "IdUser", "IdStatus"};
        String result = Main.sqlConnection.selectFunction("Exec ShowNullOrAgentsOrders " + Main.USERID, columnsName.length);

        String[][] res = Arrays.stream(
                result.split(";")
        ).map(
                i -> i.split("_")
        ).toArray(String[][]::new);

        dbTable = new DataBaseTable(res, columnsName);

        dbTable.setOpaque(true);

        add(dbTable, BorderLayout.CENTER);
    }

    private void initListeners() {
        makeOrderButton.addActionListener(e -> makeOrder());
        closeOrderButton.addActionListener(e -> closeOrder());
        cancelOrderButton.addActionListener(e -> cancelOrder());
        backButton.addActionListener(e -> onBack());
    }

    private void initButtonContainer() {
        Container buttonContainer = new Container();
        buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.X_AXIS));
        buttonContainer.add(makeOrderButton);
        buttonContainer.add(closeOrderButton);
        buttonContainer.add(cancelOrderButton);
        buttonContainer.add(backButton);
        add(buttonContainer, BorderLayout.SOUTH);
    }

    private void makeOrder() {
        int[] selected = dbTable.getSelected();
        Arrays.stream(selected).forEach(i -> {
            Object[] changed = dbTable.getSelectedRow(i);
            if (Objects.equals(changed[3], "1") || Objects.equals(changed[3], "3")) {
                try {
                    Main.sqlConnection.insertFunction("Exec MakeOrder " + Main.USERID + "," + dbTable.getInfo(i, 0));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                changed[1] = Integer.toString(Main.USERID);
                changed[3] = "5";
                dbTable.updateRow(i, changed);
            }
        });
    }

    private void closeOrder() {
        int[] selected = dbTable.getSelected();
        Arrays.stream(selected).forEach(i -> {
            Object[] changed = dbTable.getSelectedRow(i);
            if (Objects.equals(changed[3], "5")) {
                try {
                    Main.sqlConnection.insertFunction("Exec CloseOrder " + Main.USERID + "," + dbTable.getInfo(i, 0));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                changed[3] = "2";
                dbTable.updateRow(i, changed);
            }
        });
    }

    private void cancelOrder() {
        int[] selected = dbTable.getSelected();
        Arrays.stream(selected).forEach(i -> {
            Object[] changed = dbTable.getSelectedRow(i);
            if (Objects.equals(changed[3], "5")) {
                try {
                    Main.sqlConnection.insertFunction("Exec CancelOrder " + Main.USERID + "," + dbTable.getInfo(i, 0));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                changed[1] = "NULL";
                changed[3] = "3";
                dbTable.updateRow(i, changed);
            }
        });
    }

    private void onBack() {
        Main.frame.setContentPane(new AgentMenuActivity());
        Main.frame.setVisible(true);
    }

}

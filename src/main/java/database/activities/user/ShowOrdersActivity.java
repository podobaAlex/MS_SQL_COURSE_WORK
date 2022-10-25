package database.activities.user;

import database.DataBaseTable;
import database.Main;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Objects;

public class ShowOrdersActivity extends JPanel {
    private DataBaseTable tableFrame;
    private final JButton takeReadyOrderButton = new JButton("Взять готовый заказ");
    private final JButton cancelOrderButton = new JButton("Отменить заказ");
    private final JButton backButton = new JButton("Назад");

    public ShowOrdersActivity() throws SQLException {
        setLayout(new BorderLayout());

        initListeners();
        initTable();
        initButtonContainer();
    }

    private void initListeners() {
        takeReadyOrderButton.addActionListener(e -> takeReadyOrder());
        cancelOrderButton.addActionListener(e -> cancelOrder());
        backButton.addActionListener(e -> onBack());
    }

    private void initTable() throws SQLException {
        String[] columnsName = {"IdOrder", "IdAgent", "Products", "Num", "Prices", "Summary Price", "Status"};
        String result = Main.sqlConnection.selectFunction("Exec ShowOrder " + Main.USERID, columnsName.length);

        String[][] res = Arrays.stream(
                result.split(";")
        ).map(
                i -> i.split("_")
        ).toArray(String[][]::new);

        tableFrame = new DataBaseTable(res, columnsName);

        tableFrame.setOpaque(true);

        add(tableFrame, BorderLayout.CENTER);
    }

    private void initButtonContainer() {
        Container buttonContainer = new Container();
        buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.X_AXIS));
        buttonContainer.add(takeReadyOrderButton);
        buttonContainer.add(cancelOrderButton);
        buttonContainer.add(backButton);
        add(buttonContainer, BorderLayout.SOUTH);
    }

    private void takeReadyOrder() {
        int[] selected = tableFrame.getSelected();
        Arrays.stream(selected).filter(i -> Objects.equals(getStatus(i), "Готово. Ожидает заказчика")).forEach(
                i -> {
                    tableFrame.removeSelected(i);
                    try {
                        Main.sqlConnection.insertFunction(
                                "Exec TakeReadyOrder "
                                        + getOrderId(i)
                        );
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    private void cancelOrder() {
        int[] selected = tableFrame.getSelected();
        Arrays.stream(selected).forEach(
                i -> {
                    tableFrame.removeSelected(i);
                    System.out.println(getOrderId(i));
                    try {
                        Main.sqlConnection.insertFunction(
                                "Exec DeleteOrder "
                                        + Main.USERID
                                        + "," + getOrderId(i)
                                );
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    private String getOrderId(int index) {
        return tableFrame.getInfo(index, 0);
    }

    private String getStatus(int index) {
        return tableFrame.getInfo(index, 6);
    }

    private void onBack() {
        Main.frame.setContentPane(new UserMenuActivity());
        Main.frame.setVisible(true);
    }

}

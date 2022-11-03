package database.activities.admin;

import database.DataBaseTable;
import database.dialogs.AlertDialog;
import database.Main;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Vector;

public class AddProductToAgentActivity extends JPanel {

    private final JButton moveButton = new JButton("Переместить");
    private final JButton addProductButton = new JButton("Добавить");

    private final String agentId;

    private DataBaseTable productsTable;
    private DataBaseTable agentProductsTable;

    private String[][] oldProducts;

    public AddProductToAgentActivity(String agentId) throws SQLException {

        this.agentId = agentId;

        setLayout(new BorderLayout());
        initProductsTable();
        initAgentProductsTable();
        initListeners();
        initButtonContainer();

    }

    private void initProductsTable() throws SQLException {
        String[] columnsName = {"IdProd", "Name", "Num"};
        String result = Main.sqlConnection.selectFunction("Exec Catalog_Prod", columnsName.length);

        Object[][] res = Arrays.stream(
                result.split(";")
        ).map(
                i -> i.split("_")
        ).toArray(Object[][]::new);

        productsTable = new DataBaseTable(res, columnsName);

        productsTable.setOpaque(true);

        add(productsTable, BorderLayout.WEST);
    }

    private void initAgentProductsTable() {
        String[] columnsName = {"IdProd", "Name", "currentNum"};
        String[][] res = null;
        try {
            String result = Main.sqlConnection.selectFunction("Exec ShowAgentProducts " + agentId, columnsName.length);

            res = Arrays.stream(
                    result.split(";")
            ).map(
                    i -> i.split("_")
            ).toArray(String[][]::new);

            System.out.println(Arrays.toString(oldProducts));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        oldProducts = res;

        agentProductsTable = new DataBaseTable(res, columnsName);
        agentProductsTable.setOpaque(true);

        add(agentProductsTable, BorderLayout.EAST);
    }

    private void initListeners() {
        moveButton.addActionListener(e -> onMove());
        addProductButton.addActionListener(e -> onAdd());
    }

    private void initButtonContainer() {
        Container container = new Container();
        container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));

        container.add(moveButton);
        container.add(addProductButton);

        add(container, BorderLayout.SOUTH);
    }

    private void onMove() {
        int[] selected = productsTable.getSelected();
        Arrays.stream(selected).forEach(i -> {
            Vector<Vector> agentProducts = agentProductsTable.getData();
            Object[] row = productsTable.getSelectedRow(i);
            row[2] = "0";
            if (agentProducts.stream().noneMatch(j -> Objects.equals((String) j.get(0), (String) row[0]))) {
                agentProductsTable.addRow(row);
            }
        });
    }

    private void onAdd() {
        Vector<Vector> data = agentProductsTable.getData();
        int num = 0;
        for (int i = 0; i < data.size(); i++) {
            if (i < oldProducts.length) {
                num = Integer.parseInt((String) data.get(i).get(2)) - Integer.parseInt(oldProducts[i][2]);
                System.out.println(num);
            } else {
                num = Integer.parseInt((String) data.get(i).get(2));
            }
            try {
                Main.sqlConnection.insertFunction("Exec AddProdToAgent "
                        + agentId + ","
                        + data.get(i).get(0) + ","
                        + num);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        oldProducts = data.stream().map(i -> i.toArray(String[]::new)).toArray(String[][]::new);

    }

    private void callAlert(String errorName) {
        AlertDialog alert = new AlertDialog(errorName);
        alert.pack();
        alert.setVisible(true);
    }

}

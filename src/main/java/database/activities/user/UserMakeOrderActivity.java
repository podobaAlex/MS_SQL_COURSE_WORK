package database.activities.user;

import database.DataBaseTable;
import database.Main;
import database.dialogs.AlertDialog;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Vector;

public class UserMakeOrderActivity extends JPanel {

    private DataBaseTable storageProducts;
    private final DataBaseTable orderedProducts = new DataBaseTable(new Object[][]{}, new String[]{"IdProd", "Name", "Num"});

    private final JButton makeOrderButton = new JButton("Сделать заказ");
    private final JButton backButton = new JButton("Назад");
    private final JButton addToOrderButton = new JButton("Добавить продукт");

    public UserMakeOrderActivity() throws SQLException {

        setLayout(new BorderLayout());
        initTable();
        add(orderedProducts, BorderLayout.EAST);
        initListeners();
        initButtonContainer();
    }

    private void initListeners() {
        makeOrderButton.addActionListener(e -> {try {makeOrder();} catch (SQLException ex) {ex.printStackTrace();}});
        addToOrderButton.addActionListener(e -> addToOrder());
        backButton.addActionListener(e -> onBack());
    }

    private void initButtonContainer() {
        Container buttonContainer = new Container();
        buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.X_AXIS));

        buttonContainer.add(makeOrderButton);
        buttonContainer.add(addToOrderButton);
        buttonContainer.add(backButton);
        add(buttonContainer, BorderLayout.SOUTH);
    }

    private void initTable() throws SQLException {
        String[] columnsName = {"IdProd", "Name", "Num"};
        String result = Main.sqlConnection.selectFunction("Exec Catalog_Prod", columnsName.length);

        Object[][] res = Arrays.stream(
                result.split(";")
        ).map(
                i -> i.split("_")
        ).toArray(Object[][]::new);

        storageProducts = new DataBaseTable(res, columnsName);

        storageProducts.setOpaque(true);

        add(storageProducts, BorderLayout.WEST);
    }

    private void addToOrder() {
        int[] selected = storageProducts.getSelected();
        Arrays.stream(selected).forEach(i->{
            Object[] obj = storageProducts.getSelectedRow(i);
            obj[2] = "0";
            orderedProducts.addRow(obj);
        });
    }

    private void makeOrder() throws SQLException {
        Vector<Vector> data = orderedProducts.getData();
        StringBuilder res = new StringBuilder();
        for(int i = 0; i < data.size(); i++) {
            String idProd = (String) data.elementAt(i).elementAt(0);
            String numProd = (String) data.elementAt(i).elementAt(2);
            if(Integer.parseInt(numProd) <= 0) {
                callAlert("Количество продукта под номером " + idProd + " меньше 1");
                return;
            }
            res.append(idProd).append(",").append(numProd).append(";");
        }

        String[] result = res.toString().split(";");

        String orderId = Main.sqlConnection.insertFunctionWithResult("Exec NewOrder " + Main.USERID);

        System.out.println(orderId);

        orderedProducts.clear();

        Arrays.stream(result).forEach(i -> {
            try {
                Main.sqlConnection.insertFunction("AddProdToOrder " + orderId + "," + i);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private void onBack() {
        Main.frameUser.setContentPane(new UserMenuActivity());
        Main.frameUser.setVisible(true);
    }

    private void callAlert(String errorName) {
        AlertDialog alert = new AlertDialog(errorName);
        alert.pack();
        alert.setVisible(true);
    }

}

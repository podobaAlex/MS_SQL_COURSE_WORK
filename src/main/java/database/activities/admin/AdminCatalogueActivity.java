package database.activities.admin;

import database.DataBaseTable;
import database.Main;
import database.dialogs.AlertDialog;
import database.dialogs.BuyNewProductDialog;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Vector;

public class AdminCatalogueActivity extends JPanel {

    private final JButton buyProductsButton = new JButton("Купить продукты");
    private final JButton buyNewProductsButton = new JButton("Купить новые продукты");
    private final JButton addCategoryToProductButton = new JButton("Добавить категорию продукту");
    private final JButton backButton = new JButton("Назад");

    private DataBaseTable dbTable;

    private Object[][] oldData;

    public AdminCatalogueActivity() throws SQLException {

        setLayout(new BorderLayout());

        initTable();
        initListeners();
        initButtonsContainer();

    }

    private void initTable() throws SQLException {
        String[] columnsName = {"IdProd", "Name", "Num"};
        String result = Main.sqlConnection.selectFunction("Exec Catalog_Prod", columnsName.length);

        Object[][] res = Arrays.stream(
                result.split(";")
        ).map(
                i -> i.split("_")
        ).toArray(Object[][]::new);

        dbTable = new DataBaseTable(res, columnsName);

        oldData = res;

        add(dbTable, BorderLayout.CENTER);

    }

    private void initListeners() {
        buyProductsButton.addActionListener(e -> {
            try {
                onBuy();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        buyNewProductsButton.addActionListener(e -> onBuyNew());
        addCategoryToProductButton.addActionListener(e -> onAddCategory());
        backButton.addActionListener(e -> onBack());
    }

    private void initButtonsContainer() {
        Container buttonContainer = new Container();
        buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.X_AXIS));

        buttonContainer.add(buyProductsButton);
        buttonContainer.add(buyNewProductsButton);
        buttonContainer.add(addCategoryToProductButton);
        buttonContainer.add(backButton);

        add(buttonContainer, BorderLayout.SOUTH);
    }

    private void onBuy() throws SQLException {

        Vector<Vector> data = dbTable.getData();

        for (int i = 0; i < data.size(); i++) {
            Vector updatedProduct = data.get(i);
            Object[] oldProduct = oldData[i];

            int num;

            try {
                num = Integer.parseInt((String) updatedProduct.get(2)) - Integer.parseInt((String) oldProduct[2]);
                if (num < 0) {
                    callAlert("Number less than 1");
                    return;
                }
                else if(num == 0) {
                    continue;
                }
            } catch (NumberFormatException e) {
                callAlert("Wrong value");
                return;
            }

            Main.sqlConnection.insertFunction("Exec BuyProd "
                    + "@ID=" + updatedProduct.get(0)
                    + ",@Num=" + num);
        }
    }

    private void onBuyNew() {
        BuyNewProductDialog buyNewProductDialog = new BuyNewProductDialog(this.dbTable);
        buyNewProductDialog.pack();
        buyNewProductDialog.setVisible(true);
    }

    private void onAddCategory() {
        int[] selected = dbTable.getSelected();
        Arrays.stream(selected).forEach(i -> {
            JFrame frame = new JFrame();
            frame.setContentPane(new AddCategoryToProductActivity(dbTable.getInfo(i, 0)));
            frame.setVisible(true);
            frame.setLocationRelativeTo(null);
            frame.pack();
        });
    }

    private void onBack() {
        Main.frameAdmin.setContentPane(new AdminMenuActivity());
        Main.frameAdmin.setVisible(true);
    }

    private void callAlert(String errorName) {
        AlertDialog alert = new AlertDialog(errorName);
        alert.pack();
        alert.setVisible(true);
    }

}

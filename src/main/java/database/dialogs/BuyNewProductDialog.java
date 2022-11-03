package database.dialogs;

import database.DataBaseTable;
import database.Main;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;

public class BuyNewProductDialog extends JDialog {

    private final JButton buyButton = new JButton("Купить");

    private final JTextField nameTextField = new JTextField("Название:");
    private final JTextField buyCostTextField = new JTextField("Цена покупки:");
    private final JTextField sellCostTextField = new JTextField("Цена продажи:");
    private final JTextField numTextField = new JTextField("Количество:");

    private final JTextField nameTextFieldEnter = new JTextField();
    private final JTextField buyCostTextFieldEnter = new JTextField();
    private final JTextField sellCostTextFieldEnter = new JTextField();
    private final JTextField numTextFieldEnter = new JTextField();

    private final Container container = new Container();

    private DataBaseTable dbTable;

    public BuyNewProductDialog(DataBaseTable dbTable) {

        this.dbTable = dbTable;

        initListeners();
        initContainer();

    }

    private void initListeners() {
        buyButton.addActionListener(e -> {
            try {
                onBuy();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
    }

    private void initContainer() {
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setMaximumSize(new Dimension(250, 250));
        initFirstContainer();
        initSecondContainer();
        initThirdContainer();
        initFourthContainer();
        container.add(buyButton);
        add(container);
    }

    private void initFirstContainer() {
        Container newContainer = new Container();
        newContainer.setLayout(new BoxLayout(newContainer, BoxLayout.X_AXIS));
        nameTextField.setHorizontalAlignment(SwingConstants.RIGHT);
        nameTextField.setEditable(false);
        nameTextField.setPreferredSize(new Dimension(100, 30));
        nameTextField.setBorder(new EmptyBorder(0, 0, 0, 0));
        newContainer.add(nameTextField);
        nameTextFieldEnter.setPreferredSize(new Dimension(250, 30));
        newContainer.add(nameTextFieldEnter);
        container.add(newContainer);
        container.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    private void initSecondContainer() {
        Container newContainer = new Container();
        newContainer.setLayout(new BoxLayout(newContainer, BoxLayout.X_AXIS));
        buyCostTextField.setHorizontalAlignment(SwingConstants.RIGHT);
        buyCostTextField.setEditable(false);
        buyCostTextField.setBorder(new EmptyBorder(0, 0, 0, 0));
        buyCostTextField.setPreferredSize(new Dimension(100, 30));
        newContainer.add(buyCostTextField);
        buyCostTextFieldEnter.setPreferredSize(new Dimension(250, 30));
        newContainer.add(buyCostTextFieldEnter);
        container.add(newContainer);
        container.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    private void initThirdContainer() {
        Container newContainer = new Container();
        newContainer.setLayout(new BoxLayout(newContainer, BoxLayout.X_AXIS));
        sellCostTextField.setHorizontalAlignment(SwingConstants.RIGHT);
        sellCostTextField.setEditable(false);
        sellCostTextField.setBorder(new EmptyBorder(0, 0, 0, 0));
        sellCostTextField.setPreferredSize(new Dimension(100, 30));
        newContainer.add(sellCostTextField);
        sellCostTextFieldEnter.setPreferredSize(new Dimension(250, 30));
        newContainer.add(sellCostTextFieldEnter);
        container.add(newContainer);
        container.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    private void initFourthContainer() {
        Container newContainer = new Container();
        newContainer.setLayout(new BoxLayout(newContainer, BoxLayout.X_AXIS));
        numTextField.setHorizontalAlignment(SwingConstants.RIGHT);
        numTextField.setEditable(false);
        numTextField.setBorder(new EmptyBorder(0, 0, 0, 0));
        numTextField.setPreferredSize(new Dimension(100, 30));
        newContainer.add(numTextField);
        numTextFieldEnter.setPreferredSize(new Dimension(250, 30));
        newContainer.add(numTextFieldEnter);
        container.add(newContainer);
        container.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    private void onBuy() throws SQLException {
        String nameProduct = nameTextFieldEnter.getText();
        String buyCost = buyCostTextFieldEnter.getText();
        String sellCost = sellCostTextFieldEnter.getText();
        String numProduct = numTextFieldEnter.getText();

        String[] newRow = {"", nameProduct, numProduct};

        int buyCostInt;
        int sellCostInt;
        int numProductInt;

        try {
            buyCostInt = Integer.parseInt(buyCost);
            sellCostInt = Integer.parseInt(sellCost);
            numProductInt = Integer.parseInt(numProduct);
        }
        catch (NumberFormatException e) {
            callAlert("Wrong number");
            return;
        }

        if (buyCostInt <= 0) {
            callAlert("Buy cost less than 1");
            return;
        }
        else if (sellCostInt <= 0) {
            callAlert("Sell cost less than 1");
            return;
        }
        else if (numProductInt <= 0) {
            callAlert("Num less than 1");
            return;
        }

        nameProduct = "'" + nameProduct + "'";

        newRow[0] = Main.sqlConnection.insertFunctionWithResult("Exec BuyProd "
                + "@Name=" + nameProduct
                + ",@BuyCost=" + buyCost
                + ",@SellCost=" + sellCost
                + ",@Num=" + numProduct
        );

        this.dbTable.addRow(newRow);
        dispose();
    }

    private void callAlert(String errorName) {
        AlertDialog alert = new AlertDialog(errorName);
        alert.pack();
        alert.setVisible(true);
    }

}

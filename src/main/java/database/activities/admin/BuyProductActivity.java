package database.activities.admin;

import database.AlertJDialog;
import database.Main;
import database.fragments.admin.BuyExistProductFragment;
import database.fragments.admin.BuyNewProductFragment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.sql.SQLException;

public class BuyProductActivity extends JPanel {
    private final JButton buyButton = new JButton("Купить");
    private final JButton backButton = new JButton("Назад");

    private JPanel currentPane;

    private final Container container = new Container();
    private final Container buttonContainer = new Container();

    private final JToggleButton switcher = new JToggleButton("Новый продукт");

    public BuyProductActivity() throws SQLException {

        setLayout(new BorderLayout());
        initExistProductFragment();

        initListeners();
        initContainer();

    }

    private void initExistProductFragment() throws SQLException {
        currentPane = new BuyExistProductFragment();
    }

    private void initNewProductFragment() {
        currentPane = new BuyNewProductFragment();
    }

    private void initContainer() {
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setMaximumSize(new Dimension(250, 250));
        container.add(currentPane);
        initButtonsContainer();
        add(container, BorderLayout.CENTER);
    }

    private void initListeners() {
        switcher.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                initNewProductFragment();
            }
            else {
                try {
                    initExistProductFragment();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            container.remove(0);
            container.add(currentPane,0);
            revalidate();
        });
        buyButton.addActionListener(e -> {try {onBuy();} catch (SQLException ex) {ex.printStackTrace();}});
        backButton.addActionListener(e -> onBack());
    }

    private void initButtonsContainer() {
        buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.X_AXIS));

        buttonContainer.add(buyButton);
        buttonContainer.add(backButton);
        buttonContainer.add(switcher);
        container.add(buttonContainer);
    }

    private void onBuy() throws SQLException {

        if (currentPane instanceof BuyExistProductFragment) {
            onBuyExist((BuyExistProductFragment) currentPane);
        }
        else if(currentPane instanceof BuyNewProductFragment) {
            onBuyNew((BuyNewProductFragment) currentPane);
        }

    }

    private void onBuyExist(BuyExistProductFragment fragment) throws SQLException {

        String id = fragment.getSelectedId();
        String num = fragment.getNumProduct();
        int numInt;

        try {numInt = Integer.parseInt(num);} catch(NumberFormatException e) {callAlert("Wrong number"); return;}

        if (numInt <= 0) {
            callAlert("Num less than 1");
            return;
        }

        Main.sqlConnection.insertFunction("Exec BuyProd "
                + "@ID=" + id
                + ",@Num=" + num);
    }

    private void onBuyNew(BuyNewProductFragment fragment) throws SQLException {
        String nameProduct = fragment.getNameProduct();
        String buyCost = fragment.getBuyCost();
        String sellCost = fragment.getSellCost();
        String numProduct = fragment.getNumProduct();

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

        Main.sqlConnection.insertFunction("Exec BuyProd "
                + "@Name=" + nameProduct
                + ",@BuyCost=" + buyCost
                + ",@SellCost=" + sellCost
                + ",@Num=" + numProduct
        );

    }

    private void onBack() {
        Main.frame.setContentPane(new AdminMenuActivity());
        Main.frame.setVisible(true);
    }

    private void callAlert(String errorName) {
        AlertJDialog alert = new AlertJDialog(errorName);
        alert.pack();
        alert.setVisible(true);
    }

}

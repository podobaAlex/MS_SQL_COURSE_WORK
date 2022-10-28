package database.activities.user;

import database.Main;

import javax.swing.*;
import java.sql.SQLException;

public class UserMenuActivity extends JPanel {
    private JButton buttonCatalogue;
    private JButton showOrdersButton;
    private JButton makeOrderButton;
    private JPanel mainPanel;

    public UserMenuActivity() {

        initListeners();

        add(mainPanel);
    }

    private void initListeners() {
        buttonCatalogue.addActionListener(e -> {try {catalogue();}catch(SQLException ex){ex.printStackTrace();}});
        showOrdersButton.addActionListener(e -> {try {showOrders();}catch (SQLException ex){ex.printStackTrace();}});
        makeOrderButton.addActionListener(e -> {try {makeOrder();} catch (SQLException ex) {ex.printStackTrace();}});
    }

    private void catalogue() throws SQLException {
        UserCatalogueActivity userCatalogueActivity = new UserCatalogueActivity();

        Main.frameUser.setContentPane(userCatalogueActivity);
        Main.frameUser.setVisible(true);
    }

    private void showOrders() throws SQLException {
        ShowOrdersActivity showOrdersActivity = new ShowOrdersActivity();

        Main.frameUser.setContentPane(showOrdersActivity);
        Main.frameUser.setVisible(true);
    }

    private void makeOrder() throws SQLException {
        UserMakeOrderActivity userMakeOrderActivity = new UserMakeOrderActivity();

        Main.frameUser.setContentPane(userMakeOrderActivity);
        Main.frameUser.setVisible(true);
    }

}

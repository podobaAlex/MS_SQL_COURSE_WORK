package database.activities.user;

import database.Main;
import database.activities.DataBaseTableActivity;

import javax.swing.*;
import java.sql.SQLException;
import java.util.Arrays;

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
    }

    private void catalogue() throws SQLException {
        UserCatalogueActivity userCatalogueActivity = new UserCatalogueActivity();

        Main.frame.setContentPane(userCatalogueActivity);
        Main.frame.setVisible(true);
    }

    private void showOrders() throws SQLException {
        ShowOrdersActivity showOrdersActivity = new ShowOrdersActivity();

        Main.frame.setContentPane(showOrdersActivity);
        Main.frame.setVisible(true);
    }


}

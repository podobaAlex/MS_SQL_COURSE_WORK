package database.activities.agent;

import database.Main;

import javax.swing.*;
import java.sql.SQLException;

public class AgentMenuActivity extends JPanel{
    private JButton showOrdersButton;
    private JPanel panel1;
    private JButton productsButton;

    public AgentMenuActivity() {

        initListeners();
        add(panel1);

    }

    private void initListeners() {
        showOrdersButton.addActionListener(e -> {
            try {
                showOrders();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        productsButton.addActionListener(e -> {
            try {
                addProduct();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
    }

    private void showOrders() throws SQLException {
        ShowOrdersActivity showOrdersActivity = new ShowOrdersActivity();

        Main.frameAgent.setContentPane(showOrdersActivity);
        Main.frameAgent.setVisible(true);
    }

    private void addProduct() throws SQLException {
        ProductsActivity productsActivity = new ProductsActivity();

        Main.frameAgent.setContentPane(productsActivity);
        Main.frameAgent.setVisible(true);
    }

}

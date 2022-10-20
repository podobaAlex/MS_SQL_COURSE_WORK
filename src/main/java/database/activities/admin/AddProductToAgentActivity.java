package database.activities.admin;

import database.AlertJDialog;
import database.Main;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.Arrays;

public class AddProductToAgentActivity extends JPanel {

    private final JButton addProductButton = new JButton("Add Product");
    private final JButton backButton = new JButton("Back");

    private final JComboBox<String> idAgentComboBox;
    private final JComboBox<String> idProdComboBox;

    private String selectedIdAgent;
    private String selectedIdProduct;

    private final JTextField numProductTextField = new JTextField("Number");

    private final Container container = new Container();
    private final Container buttonContainer = new Container();

    public AddProductToAgentActivity() throws SQLException {

        idAgentComboBox = new JComboBox<>(initComboBox("Exec Stat"));
        idProdComboBox = new JComboBox<>(initComboBox("Exec Catalog_Prod"));

        selectedIdAgent = (String) idAgentComboBox.getSelectedItem();
        selectedIdProduct = (String) idProdComboBox.getSelectedItem();

        initListeners();
        initContainer();

    }

    private String[] initComboBox(String command) throws SQLException {
        String result = Main.sqlConnection.selectFunction(command, 1);

        return Arrays.stream(
                result.split(";")
        ).map(x -> x.substring(0,x.length()-1)).toArray(String[]::new);
    }

    private void initListeners() {
        idAgentComboBox.addItemListener(e -> selectedIdAgent = (String) idAgentComboBox.getSelectedItem());
        idProdComboBox.addItemListener(e -> selectedIdProduct = (String) idProdComboBox.getSelectedItem());
        addProductButton.addActionListener(e -> {
            try {
                addProduct(selectedIdAgent, selectedIdProduct, numProductTextField.getText());
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        backButton.addActionListener(e -> onBack());
    }

    private void initContainer() {
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        idAgentComboBox.setAlignmentX(CENTER_ALIGNMENT);
        container.add(idAgentComboBox);

        idProdComboBox.setAlignmentX(CENTER_ALIGNMENT);
        container.add(idProdComboBox);

        numProductTextField.setAlignmentX(CENTER_ALIGNMENT);
        container.add(numProductTextField);

        initButtonContainer();

        add(container);
    }

    private void initButtonContainer() {
        buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.X_AXIS));

        buttonContainer.add(backButton);
        buttonContainer.add(addProductButton);

        container.add(buttonContainer);
    }

    private void addProduct(String idAgent, String idProduct, String num) throws SQLException {
        int numInt;

        try {
            numInt = Integer.parseInt(num);
        } catch (NumberFormatException e) {
            callAlert("Wrong number");
            return;
        }

        if(numInt <= 0) {
            callAlert("Number less than 1");
            return;
        }

        Main.sqlConnection.insertFunction(
                "Exec AddProdToAgent "
                        + idAgent + ", "
                        + idProduct + ", "
                        + num
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

    public String getIdAgent() {
        return selectedIdAgent;
    }

    public String getIdProduct() {
        return selectedIdProduct;
    }

}

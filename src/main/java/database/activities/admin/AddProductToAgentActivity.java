package database.activities.admin;

import database.dialogs.AlertDialog;
import database.Main;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;
import java.util.Arrays;

public class AddProductToAgentActivity extends JPanel {

    private final JButton addProductButton = new JButton("Добавить");
    private final JButton backButton = new JButton("Назад");

    private final JTextField idAgentTextField = new JTextField("Номер агента:");
    private final JTextField idProdTextField = new JTextField("Номер продукта:");

    private final JComboBox<String> idAgentComboBox;
    private final JComboBox<String> idProdComboBox;

    private String selectedIdAgent;
    private String selectedIdProduct;

    private final JTextField numProductTextField = new JTextField("Количество:");

    private final JTextField numProductTextFieldEnter = new JTextField();

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
                addProduct(selectedIdAgent, selectedIdProduct, numProductTextFieldEnter.getText());
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

        numProductTextFieldEnter.setAlignmentX(CENTER_ALIGNMENT);
        container.add(numProductTextFieldEnter);

        initFirstContainer();
        initSecondContainer();
        initThirdContainer();
        initButtonContainer();

        add(container);
    }

    private void initFirstContainer() {

        Container newContainer = new Container();
        newContainer.setLayout(new BoxLayout(newContainer, BoxLayout.X_AXIS));

        idAgentTextField.setEditable(false);
        idAgentTextField.setBorder(new EmptyBorder(0, 0, 0, 0));
        idAgentTextField.setHorizontalAlignment(SwingConstants.RIGHT);
        idAgentTextField.setPreferredSize(new Dimension(100, 26));
        newContainer.add(idAgentTextField);
        idAgentComboBox.setPreferredSize(new Dimension(50, 26));
        newContainer.add(idAgentComboBox);
        container.add(newContainer);
        container.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    private void initSecondContainer() {

        Container newContainer = new Container();
        newContainer.setLayout(new BoxLayout(newContainer, BoxLayout.X_AXIS));

        idProdTextField.setEditable(false);
        idProdTextField.setBorder(new EmptyBorder(0,0,0,0));
        idProdTextField.setHorizontalAlignment(SwingConstants.RIGHT);
        idProdTextField.setPreferredSize(new Dimension(100, 26));
        newContainer.add(idProdTextField);
        idProdComboBox.setPreferredSize(new Dimension(50, 26));
        newContainer.add(idProdComboBox);
        container.add(newContainer);
        container.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    private void initThirdContainer() {

        Container newContainer = new Container();
        newContainer.setLayout(new BoxLayout(newContainer, BoxLayout.X_AXIS));

        numProductTextField.setEditable(false);
        numProductTextField.setBorder(new EmptyBorder(0, 0, 0, 0));
        numProductTextField.setHorizontalAlignment(SwingConstants.RIGHT);
        numProductTextField.setPreferredSize(new Dimension(100, 26));
        newContainer.add(numProductTextField);
        numProductTextFieldEnter.setPreferredSize(new Dimension(50, 26));
        newContainer.add(numProductTextFieldEnter);
        container.add(newContainer);
        container.add(Box.createRigidArea(new Dimension(0, 10)));
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
        Main.frameAdmin.setContentPane(new AdminMenuActivity());
        Main.frameAdmin.setVisible(true);
    }

    private void callAlert(String errorName) {
        AlertDialog alert = new AlertDialog(errorName);
        alert.pack();
        alert.setVisible(true);
    }

}

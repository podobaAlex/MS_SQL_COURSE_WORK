package database.dialogs;

import database.Main;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class AddProductDialog extends JDialog {

    private final String productId;
    private final int numMax;

    private final JTextField numberTextField = new JTextField();

    private final JButton addButton = new JButton("Добавить");

    public AddProductDialog(String productId, int numMax, String name) {

        setTitle(name);

        initListeners();
        initContainer();

        this.numMax = numMax;
        this.productId = productId;

    }

    private void initContainer() {
        Container container = new Container();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        numberTextField.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(numberTextField);

        addButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(addButton);

        add(container);

    }

    private void initListeners() {
        addButton.addActionListener(e -> {try {onAdd();} catch (SQLException ex) {ex.printStackTrace();}});
    }

    private void onAdd() throws SQLException {
        try {
            int number = Integer.parseInt(numberTextField.getText());
            if (number > numMax || number < 0) {
                showAlert("Value bigger than storage num or less than 0");
                return;
            }
        } catch (NumberFormatException e) {
            showAlert("Incorrect value");
            return;
        }
        dispose();
        Main.sqlConnection.insertFunction(
                "Exec AddProd "
                        + Main.USERID
                        + "," + productId
                        + "," + numberTextField.getText()
                );
    }

    private void showAlert(String errorName) {
        AlertDialog alert = new AlertDialog(errorName);
        alert.pack();
        alert.setVisible(true);
    }

}


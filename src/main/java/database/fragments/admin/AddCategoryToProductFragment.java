package database.fragments.admin;

import database.Main;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;
import java.util.Arrays;

public class AddCategoryToProductFragment extends JPanel {

    private final JTextField idProductTextField = new JTextField("Номер продукта:");
    private final JTextField idCategoryTextField = new JTextField("Номер категории:");

    private final JComboBox<String> idProductComboBox;
    private final JComboBox<String> idCategoryComboBox;

    private String selectedProductId;
    private String selectedCategoryId;

    private final Container container = new Container();

    public AddCategoryToProductFragment() throws SQLException {

        idProductComboBox = new JComboBox<>(initComboBox("Exec Catalog_Prod"));
        idCategoryComboBox = new JComboBox<>(initComboBox("Exec ShowCategories"));

        selectedProductId = (String) idProductComboBox.getSelectedItem();
        selectedCategoryId = (String) idCategoryComboBox.getSelectedItem();

        initListeners();
        initContainer();
    }

    private String[] initComboBox(String command) throws SQLException {
        String result = Main.sqlConnection.selectFunction(command, 1);

        return Arrays.stream(
                result.split(";")
        ).map(x -> x.substring(0,x.length()-1)).toArray(String[]::new);
    }

    private void initContainer() {

        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        initFirstContainer();
        initSecondContainer();

        add(container);
    }

    private void initFirstContainer() {
        Container newContainer = new Container();
        newContainer.setLayout(new BoxLayout(newContainer, BoxLayout.X_AXIS));
        idProductTextField.setEditable(false);
        idProductTextField.setBorder(new EmptyBorder(0,0,0,0));
        newContainer.add(idProductTextField);
        newContainer.add(idProductComboBox);
        container.add(newContainer);
        container.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    private void initSecondContainer() {
        Container newContainer = new Container();
        newContainer.setLayout(new BoxLayout(newContainer, BoxLayout.X_AXIS));
        idCategoryTextField.setEditable(false);
        idCategoryTextField.setBorder(new EmptyBorder(0, 0, 0, 0));
        newContainer.add(idCategoryTextField);
        newContainer.add(idCategoryComboBox);
        container.add(newContainer);
    }

    private void initListeners() {
        idCategoryComboBox.addActionListener(e -> selectedCategoryId = (String) idCategoryComboBox.getSelectedItem());
        idProductComboBox.addActionListener(e -> selectedProductId = (String) idProductComboBox.getSelectedItem());
    }

    public String getSelectedProductId() {
        return selectedProductId;
    }

    public String getSelectedCategoryId() {
        return selectedCategoryId;
    }

}

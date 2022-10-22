package database.fragments.admin;

import database.Main;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;
import java.util.Arrays;

public class BuyExistProductFragment extends JPanel {

    private final JTextField idProductTextField = new JTextField("Номер продукта:");
    private final JTextField numProductTextField = new JTextField("Количество:");

    private final JComboBox<String> comboBox;
    private String selectedId;

    private final JTextField numProductTextFieldEntry = new JTextField();

    private final Container container = new Container();

    public BuyExistProductFragment() throws SQLException {

        comboBox = new JComboBox<>(initComboBox());
        initListeners();
        initContainer();
        add(container);

    }

    private String[] initComboBox() throws SQLException {
        String result = Main.sqlConnection.selectFunction("Exec Catalog_Prod", 1);

        String[] res = Arrays.stream(
                result.split(";")
        ).map(x -> x.substring(0,x.length()-1)).toArray(String[]::new);

        selectedId = res[0];

        return res;
    }

    private void initContainer() {

        container.setLayout(new BoxLayout(container,BoxLayout.Y_AXIS));

        initFirstContainer();
        initSecondContainer();
    }

    private void initFirstContainer() {

        Container newContainer = new Container();
        newContainer.setLayout(new BoxLayout(newContainer, BoxLayout.X_AXIS));

        idProductTextField.setHorizontalAlignment(SwingConstants.RIGHT);
        idProductTextField.setBorder(new EmptyBorder(0, 0, 0, 0));
        idProductTextField.setEditable(false);

        newContainer.add(idProductTextField);
        newContainer.add(comboBox);

        container.add(newContainer);
        container.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    private void initSecondContainer() {

        Container newContainer = new Container();
        newContainer.setLayout(new BoxLayout(newContainer, BoxLayout.X_AXIS));

        numProductTextField.setHorizontalAlignment(SwingConstants.RIGHT);
        numProductTextField.setBorder(new EmptyBorder(0, 0, 0, 0));
        numProductTextField.setEditable(false);

        newContainer.add(numProductTextField);
        newContainer.add(numProductTextFieldEntry);

        container.add(newContainer);
    }

    private void initListeners() {

        comboBox.addActionListener(e -> selectedId = (String) comboBox.getSelectedItem());
    }

    public String getSelectedId() {
        return selectedId;
    }

    public String getNumProduct() {
        return numProductTextFieldEntry.getText();
    }

}

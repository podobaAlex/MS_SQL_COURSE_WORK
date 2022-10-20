package database.fragments.admin;

import database.Main;
import database.activities.admin.ChangeSalaryActivity;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.Arrays;

public class BuyExistProductFragment extends JPanel {

    private final JComboBox<String> comboBox;
    private String selectedId;

    private final JTextField numProductTextField = new JTextField("Num");

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

        comboBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(comboBox);

        numProductTextField.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(numProductTextField);
    }

    private void initListeners() {

        comboBox.addActionListener(e -> {
            JComboBox<String> cb = (JComboBox<String>)e.getSource();
            selectedId = (String)cb.getSelectedItem();
        });
    }

    public String getSelectedId() {
        return selectedId;
    }

    public String getNumProduct() {
        return numProductTextField.getText();
    }

}

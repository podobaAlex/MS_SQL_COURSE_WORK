package database.dialogs;

import database.DataBaseTable;
import database.Main;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class AddNewCategoryDialog extends JDialog {

    private final JTextField categoryNameTextField = new JTextField();

    private final JButton addButton = new JButton("Добавить");

    private DataBaseTable dataBaseTable;

    public AddNewCategoryDialog(DataBaseTable dataBaseTable) {

        this.dataBaseTable = dataBaseTable;

        initListeners();
        initContainer();

    }

    private void initListeners() {
        addButton.addActionListener(e -> {
            try {
                onAdd();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
    }

    private void initContainer() {
        Container container = new Container();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        categoryNameTextField.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(categoryNameTextField);
        addButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(addButton);

        add(container);
    }

    private void onAdd() throws SQLException {
        String categoryName = categoryNameTextField.getText();

        String categoryId = Main.sqlConnection.insertFunctionWithResult("Exec NewCategory " + categoryName);

        dataBaseTable.addRow(new String[]{categoryId, categoryName});
        dispose();
    }

}

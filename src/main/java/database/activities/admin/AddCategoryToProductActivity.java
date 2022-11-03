package database.activities.admin;

import database.DataBaseTable;
import database.Main;
import database.dialogs.AddNewCategoryDialog;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Objects;

public class AddCategoryToProductActivity extends JPanel {

    private final JButton addButton = new JButton("Добавить");
    private final JButton addNewCategory = new JButton("Добавить новую категорию");

    private DataBaseTable categoriesTable;
    private DataBaseTable categoriesOfProductTable;

    private final String productId;

    public AddCategoryToProductActivity(String productId) {

        this.productId = productId;

        setLayout(new BorderLayout());
        initListeners();
        initCategoriesTable();
        initCategoriesOfProductTable();
        initButtonsContainer();

    }

    private void initCategoriesTable() {
        String[] columnsName = {"IdCategory", "Name"};
        String[][] res = null;
        try {
            String result = Main.sqlConnection.selectFunction("Exec ShowCategories", columnsName.length);

            res = Arrays.stream(
                    result.split(";")
            ).map(
                    i -> i.split("_")
            ).toArray(String[][]::new);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        categoriesTable = new DataBaseTable(res, columnsName);
        categoriesTable.setOpaque(true);

        add(categoriesTable, BorderLayout.WEST);
    }

    private void initCategoriesOfProductTable() {
        String[] columnsName = {"IdCategory", "Name"};
        String[][] res = null;
        try {
            String result = Main.sqlConnection.selectFunction("Exec ShowCategoriesOfProduct " + productId, columnsName.length);

            res = Arrays.stream(
                    result.split(";")
            ).map(
                    i -> i.split("_")
            ).toArray(String[][]::new);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        categoriesOfProductTable = new DataBaseTable(res, columnsName);
        categoriesOfProductTable.setOpaque(true);

        add(categoriesOfProductTable, BorderLayout.EAST);
    }

    private void initListeners() {
        addButton.addActionListener(e -> onAdd());
        addNewCategory.addActionListener(e -> onAddNew());
    }

    private void initButtonsContainer() {
        Container buttonsContainer = new Container();
        buttonsContainer.setLayout(new BoxLayout(buttonsContainer, BoxLayout.X_AXIS));

        buttonsContainer.add(addButton);
        buttonsContainer.add(addNewCategory);

        add(buttonsContainer, BorderLayout.SOUTH);
    }

    private void onAdd() {
        int[] selected = categoriesTable.getSelected();

        Arrays.stream(selected).forEach(i -> {
            try {
                String categoryId = categoriesTable.getInfo(i, 0);
                if(categoriesOfProductTable.getData().stream().noneMatch(j -> j.stream().anyMatch(k -> Objects.equals((String) k, categoryId)))) {
                    categoriesOfProductTable.addRow(new String[]{categoryId, categoriesTable.getInfo(i, 1)});
                    Main.sqlConnection.insertFunction(
                            "Exec AddCategoryToProduct "
                                    + categoryId
                                    + ", " + productId
                    );
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private void onAddNew() {
        AddNewCategoryDialog addNewCategoryDialog = new AddNewCategoryDialog(this.categoriesTable);
        addNewCategoryDialog.pack();
        addNewCategoryDialog.setVisible(true);
    }

}

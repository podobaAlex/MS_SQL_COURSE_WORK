package database.activities.agent;

import database.DataBaseTable;
import database.Main;
import database.dialogs.AddProductDialog;
import database.dialogs.AlertDialog;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.Arrays;

public class ProductsActivity extends JPanel {

    private final JButton addButton = new JButton("Добавить");
    private final JButton showCommentsButton = new JButton("Показать комментарии");
    private final JButton backButton = new JButton("Назад");

    private DataBaseTable dbTable;

    public ProductsActivity() throws SQLException {

        setLayout(new BorderLayout());
        initTable();
        initListeners();
        initButtonContainer();

    }

    private void initTable() throws SQLException {
        String[] columnsName = {"IdProd", "Name", "currentNum", "storageNum"};
        String result = Main.sqlConnection.selectFunction("Exec ShowAgentProducts " + Main.USERID, columnsName.length);

        String[][] res = Arrays.stream(
                result.split(";")
        ).map(
                i -> i.split("_")
        ).toArray(String[][]::new);

        dbTable = new DataBaseTable(res, columnsName);

        dbTable.setOpaque(true);

        add(dbTable, BorderLayout.CENTER);
    }

    private void initListeners() {
        addButton.addActionListener(e -> onAdd());
        showCommentsButton.addActionListener(e -> showComments());
        backButton.addActionListener(e -> onBack());
    }

    private void initButtonContainer() {
        Container buttonContainer = new Container();
        buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.X_AXIS));
        buttonContainer.add(addButton);
        buttonContainer.add(showCommentsButton);
        buttonContainer.add(backButton);
        add(buttonContainer, BorderLayout.SOUTH);
    }

    private void onAdd() {
        int[] selected = dbTable.getSelected();

        Arrays.stream(selected).forEach(i -> {
            AddProductDialog addProductDialog = new AddProductDialog(
                    dbTable.getInfo(i,0),
                    Integer.parseInt(dbTable.getInfo(i,3)),
                    dbTable.getInfo(i, 1)
            );
            addProductDialog.pack();
            addProductDialog.setVisible(true);
        });
    }

    private void showComments() {
        int[] selected = dbTable.getSelected();

        Arrays.stream(selected).forEach(i -> {
            Object[] objects = dbTable.getSelectedRow(i);
            JFrame frame = new JFrame((String) objects[1]);
            try {
                frame.setContentPane(new ShowCommentsActivity((String) objects[0]));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            frame.setSize(500, 500);
            frame.setVisible(true);
            frame.setLocationRelativeTo(null);
        });


    }

    private void onBack() {
        Main.frameAgent.setContentPane(new AgentMenuActivity());
        Main.frameAgent.setVisible(true);
    }

}

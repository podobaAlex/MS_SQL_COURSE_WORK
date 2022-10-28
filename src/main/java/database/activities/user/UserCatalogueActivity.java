package database.activities.user;

import database.DataBaseTable;
import database.Main;
import database.dialogs.AlertDialog;
import database.dialogs.CommentDialog;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.Arrays;

public class UserCatalogueActivity extends JPanel {

    private DataBaseTable tablePane;

    private final JButton backButton = new JButton("Назад");
    private final JButton commentaryButton = new JButton("Оставить комментарий");

    public UserCatalogueActivity() throws SQLException {

        setLayout(new BorderLayout());
        initTable();

        add(tablePane, BorderLayout.CENTER);

        initListeners();
        initContainer();

    }

    private void initListeners() {
        backButton.addActionListener(e -> onBack());
        commentaryButton.addActionListener(e -> onComment());
    }

    private void initContainer() {
        Container container = new Container();
        container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));
        backButton.setAlignmentX(CENTER_ALIGNMENT);
        container.add(backButton);
        container.add(commentaryButton);
        add(container, BorderLayout.SOUTH);
    }

    private void initTable() throws SQLException {
        String[] columnsName = {"IdProd", "Name", "Num"};
        String result = Main.sqlConnection.selectFunction("Exec Catalog_Prod", columnsName.length);

        Object[][] res = Arrays.stream(
                result.split(";")
        ).map(
                i -> i.split("_")
        ).toArray(Object[][]::new);

        tablePane = new DataBaseTable(res, columnsName);
    }

    private void onComment() {
        int[] selected = tablePane.getSelected();

        if(selected.length != 1) {
            return;
        }

        CommentDialog comment = new CommentDialog(tablePane.getInfo(selected[0],0));
        comment.pack();
        comment.setVisible(true);
    }

    private void onBack() {
        Main.frameUser.setContentPane(new UserMenuActivity());
        Main.frameUser.setVisible(true);
    }

}

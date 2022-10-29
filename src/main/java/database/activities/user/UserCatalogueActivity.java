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

    private final JButton commentaryButton = new JButton("Оставить комментарий");
    private final JButton showCommentsButton = new JButton("Показать комментарии");
    private final JButton backButton = new JButton("Назад");

    private DataBaseTable tablePane;

    public UserCatalogueActivity() throws SQLException {

        setLayout(new BorderLayout());
        initTable();

        add(tablePane, BorderLayout.CENTER);

        initListeners();
        initContainer();

    }

    private void initListeners() {
        commentaryButton.addActionListener(e -> onComment());
        showCommentsButton.addActionListener(e -> onShowComment());
        backButton.addActionListener(e -> onBack());
    }

    private void initContainer() {
        Container container = new Container();
        container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));
        backButton.setAlignmentX(CENTER_ALIGNMENT);
        container.add(commentaryButton);
        container.add(showCommentsButton);
        container.add(backButton);
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

    private void onShowComment() {
        int[] selected = tablePane.getSelected();

        Arrays.stream(selected).forEach(i -> {
            try {
                JFrame frame = new JFrame(tablePane.getInfo(i, 1));
                frame.setContentPane(new ShowCommentsActivity(tablePane.getInfo(i, 0)));
                frame.setVisible(true);
                frame.setSize(new Dimension(500, 500));
                frame.setLocationRelativeTo(null);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

    }

    private void onBack() {
        Main.frameUser.setContentPane(new UserMenuActivity());
        Main.frameUser.setVisible(true);
    }

}

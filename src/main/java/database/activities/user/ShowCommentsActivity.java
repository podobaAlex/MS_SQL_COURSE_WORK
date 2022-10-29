package database.activities.user;

import database.DataBaseTable;
import database.Main;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.Arrays;

public class ShowCommentsActivity extends JPanel {
    private final String IdProduct;

    private DataBaseTable dbTable;

    public ShowCommentsActivity(String IdProduct) throws SQLException {

        this.IdProduct = IdProduct;

        setLayout(new BorderLayout());
        initTable();

    }

    private void initTable() throws SQLException {
        String[] columnsName = {"IdComment", "IdUser", "IdProd", "Comment", "Response"};
        String result = Main.sqlConnection.selectFunction("Exec ShowComments " + IdProduct, columnsName.length);

        String[][] res = Arrays.stream(
                result.split(";")
        ).map(
                i -> i.split("_")
        ).toArray(String[][]::new);

        dbTable = new DataBaseTable(res, columnsName);

        dbTable.setOpaque(true);

        add(dbTable, BorderLayout.CENTER);
    }

}

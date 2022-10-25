package database.activities.agent;

import database.DataBaseTable;
import database.Main;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Vector;

public class ShowCommentsActivity extends JPanel{

    private final String IdProduct;

    private final JButton responseButton = new JButton("Ответить");

    private DataBaseTable dbTable;

    public ShowCommentsActivity(String IdProduct) throws SQLException {

        this.IdProduct = IdProduct;

        setLayout(new BorderLayout());
        initTable();
        initListeners();
        initButtonContainer();

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

    private void initListeners() {
        responseButton.addActionListener(e -> onResponse());
    }

    private void initButtonContainer() {
        Container buttonContainer = new Container();
        buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.X_AXIS));
        buttonContainer.add(responseButton);
        add(buttonContainer, BorderLayout.SOUTH);
    }

    private void onResponse() {
        Vector<Vector> data = dbTable.getData();
        data.forEach(i -> {
            String response = (String) i.elementAt(4);
            if (!Objects.equals(response, "") || !Objects.equals(response, "null")) {
                response = "'" + response + "'";
                try {
                    Main.sqlConnection.insertFunction(
                            "Exec ResponseOnComment "
                                    + Main.USERID
                                    + "," + i.elementAt(0)
                                    + "," + response
                    );
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}

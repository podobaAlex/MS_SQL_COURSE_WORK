package database;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.sql.*;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        String connectionUrl =
                "jdbc:sqlserver://DESKTOP-GNG9DNC\\SQLEXPRESS01;"
                        + "database=T&P_Firm;"
                        + "integratedSecurity=true;"
                        + "encrypt=false;";

        String[] columnsName = {"IdProd", "Name", "Num"};
        String result = null;
        try {
            SQLConnection sqlConnection = new SQLConnection(connectionUrl);
            result = sqlConnection.selectFunction("Exec Catalog_Prod", columnsName.length);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Object[][] res = Arrays.stream(
                result.split(";")
        ).map(
                i -> i.split(",")
        ).toArray(Object[][]::new);

        DataBaseTable tableFrame = new DataBaseTable(res, columnsName);

        JFrame frame = new JFrame("Table");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tableFrame.setOpaque(true);
        frame.setContentPane(tableFrame);

        frame.pack();
        frame.setVisible(true);

    }

}

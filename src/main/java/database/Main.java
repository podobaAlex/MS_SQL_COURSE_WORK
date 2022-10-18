package database;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.sql.*;
import java.util.Arrays;

public class Main {

    public static SQLConnection sqlConnection;

    public static void main(String[] args) {

        String connectionUrl =
                "jdbc:sqlserver://DESKTOP-GNG9DNC\\SQLEXPRESS01;"
                        + "database=T&P_Firm;"
                        + "integratedSecurity=true;"
                        + "encrypt=false;";

        try {
            sqlConnection = new SQLConnection(connectionUrl);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        AdminMenu dialog = new AdminMenu();
        dialog.pack();
        dialog.setVisible(true);


        //System.exit(0);

    }

}

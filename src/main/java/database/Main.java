package database;

import database.activities.admin.AdminMenuActivity;

import javax.swing.*;
import java.sql.*;

public class Main {

    public static JFrame frame;
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

        frame = new JFrame("CourseWork");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new AdminMenuActivity());
        frame.setSize(1000, 1000);
        frame.setVisible(true);

    }

}

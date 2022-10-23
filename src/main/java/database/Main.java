package database;

import database.activities.user.UserMenuActivity;

import javax.swing.*;
import java.sql.*;

public class Main {

    public static final int ADMIN_ROLE = 0;
    public static final int AGENT_ROLE = 1;
    public static final int USER_ROLE = 2;

    private static int currentRole = 2;

    public static int USERID = 3;
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
        frame.setContentPane(new UserMenuActivity());
        frame.setSize(1000, 1000);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    public static int getCurrentRole() {
        return currentRole;
    }

}
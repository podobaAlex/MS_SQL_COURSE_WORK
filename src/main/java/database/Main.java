package database;

import database.activities.MainMenuActivity;
import database.activities.admin.AdminMenuActivity;
import database.activities.agent.AgentMenuActivity;
import database.activities.user.UserMenuActivity;

import javax.swing.*;
import java.sql.*;

public class Main {

    public static final int ADMIN_ROLE = 0;
    public static final int AGENT_ROLE = 1;
    public static final int USER_ROLE = 2;

    private static int currentRole = 1;

    public static int USERID = 1;
    public static JFrame frameAdmin;
    public static JFrame frameAgent;
    public static JFrame frameUser;
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

        JFrame mainFrame = new JFrame("CourseWork");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setContentPane(new MainMenuActivity());
        mainFrame.pack();
        mainFrame.setVisible(true);
        mainFrame.setLocationRelativeTo(null);
    }

    public static int getCurrentRole() {
        return currentRole;
    }

}
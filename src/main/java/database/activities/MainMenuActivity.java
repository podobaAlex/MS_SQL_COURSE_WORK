package database.activities;

import database.Main;
import database.activities.admin.AdminMenuActivity;
import database.activities.agent.AgentMenuActivity;
import database.activities.user.UserMenuActivity;

import javax.swing.*;

public class MainMenuActivity extends JPanel {
    private JButton adminButton;
    private JButton userButton;
    private JButton agentButton;
    private JPanel mainPanel;

    public MainMenuActivity() {

        initListeners();
        add(mainPanel);

    }

    private void initListeners() {
        adminButton.addActionListener(e -> onAdmin());
        agentButton.addActionListener(e -> onAgent());
        userButton.addActionListener(e -> onUser());
    }

    private void onAdmin() {
        if(Main.frameAdmin != null) {
            return;
        }
        Main.frameAdmin = new JFrame("Admin");
        Main.frameAdmin.setContentPane(new AdminMenuActivity());
        Main.frameAdmin.setSize(1000, 1000);
        Main.frameAdmin.setVisible(true);
        Main.frameAdmin.setLocationRelativeTo(null);
    }

    private void onAgent() {
        if(Main.frameUser != null) {
            return;
        }
        Main.frameAgent = new JFrame("Agent");
        Main.frameAgent.setContentPane(new AgentMenuActivity());
        Main.frameAgent.setSize(1000, 1000);
        Main.frameAgent.setVisible(true);
        Main.frameAgent.setLocationRelativeTo(null);
    }

    private void onUser() {
        if(Main.frameUser != null) {
            return;
        }
        Main.frameUser = new JFrame("User");
        Main.frameUser.setContentPane(new UserMenuActivity());
        Main.frameUser.setSize(1000, 1000);
        Main.frameUser.setVisible(true);
        Main.frameUser.setLocationRelativeTo(null);
    }

}

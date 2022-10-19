package database.activities;

import database.AlertJDialog;
import database.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Objects;

public class AddAgentActivity extends JPanel {

    private final JTextField agentFIO = new JTextField("FIO", 30);
    private final JTextField agentSalary = new JTextField("Salary", 30);

    private final JButton addAgentButton = new JButton("Add");
    private final JButton backButton = new JButton("Back");

    private final Container containerOfButtons = new Container();
    private final Container container = new Container();

    public AddAgentActivity() {

        initListeners();
        initContainer();
        initContainerOfButtons();

        add(container);

    }

    private void initContainer() {
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        agentFIO.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(agentFIO);

        agentSalary.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(agentSalary);

        container.add(containerOfButtons);
    }

    private void initContainerOfButtons() {
        containerOfButtons.setLayout(new BoxLayout(containerOfButtons, BoxLayout.X_AXIS));
        containerOfButtons.add(addAgentButton);
        containerOfButtons.add(backButton);
    }

    private void initListeners() {
        addAgentButton.addActionListener(e -> {
            try {
                addAgent(agentFIO.getText(), agentSalary.getText());
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        backButton.addActionListener(e -> onBack());
    }

    private void onBack() {
        Main.frame.setContentPane(new AdminMenuActivity());
        Main.frame.pack();
    }

    private void addAgent(String FIO, String salary) throws SQLException {
        if (Objects.equals(FIO, "")) {
            callAlert("Empty name");
            return;
        }

        try {
            Integer.parseInt(salary);
        } catch (NumberFormatException e) {
            callAlert("Wrong number");
            return;
        }

        FIO = "'" + FIO + "'";

        Main.sqlConnection.insertFunction("Exec Add_Agent " + FIO + ", " + salary);

    }

    private void callAlert(String errorName) {
        AlertJDialog alert = new AlertJDialog(errorName);
        alert.pack();
        alert.setVisible(true);
    }

}

package database.activities.admin;

import database.dialogs.AlertDialog;
import database.Main;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;
import java.util.Objects;

public class AddAgentActivity extends JPanel {

    private final JTextField agentFIO = new JTextField("Фамилия, Имя, Отчество (Инициалы):");
    private final JTextField agentSalary = new JTextField("Зарплата:");

    private final JTextField agentFIOEnter = new JTextField("", 30);
    private final JTextField agentSalaryEnter = new JTextField("", 30);

    private final JButton addAgentButton = new JButton("Добавить");
    private final JButton backButton = new JButton("Назад ");

    private final Container containerOfButtons = new Container();
    private final Container container = new Container();

    public AddAgentActivity() {

        agentFIO.setEditable(false);
        agentSalary.setEditable(false);

        initListeners();
        initContainer();
        initContainerOfButtons();

        add(container);

    }

    private void initContainer() {
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        Container containerFIO = new Container();
        containerFIO.setLayout(new BoxLayout(containerFIO, BoxLayout.X_AXIS));
        agentFIO.setBorder(new EmptyBorder(0,0,0,0));
        agentFIO.setSize(new Dimension(500, 30));
        agentFIO.setHorizontalAlignment(SwingConstants.RIGHT);
        containerFIO.add(agentFIO);
        agentFIOEnter.setMaximumSize(new Dimension(300, 30));
        containerFIO.add(agentFIOEnter);

        container.add(containerFIO);
        container.add(Box.createRigidArea(new Dimension(0, 10)));

        Container containerSalary = new Container();
        containerSalary.setLayout(new BoxLayout(containerSalary, BoxLayout.X_AXIS));
        agentSalary.setBorder(new EmptyBorder(0,0,0,0));
        agentSalary.setSize(new Dimension(500, 30));
        agentSalary.setHorizontalAlignment(SwingConstants.RIGHT);
        containerSalary.add(agentSalary);
        agentSalaryEnter.setMaximumSize(new Dimension(300, 30));
        containerSalary.add(agentSalaryEnter);

        container.add(containerSalary);
        container.add(Box.createRigidArea(new Dimension(0, 10)));

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
                addAgent(agentFIOEnter.getText(), agentSalaryEnter.getText());
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        backButton.addActionListener(e -> onBack());
    }

    private void onBack() {
        Main.frame.setContentPane(new AdminMenuActivity());
        Main.frame.setVisible(true);
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
        AlertDialog alert = new AlertDialog(errorName);
        alert.pack();
        alert.setVisible(true);
    }

}

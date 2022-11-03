package database.dialogs;

import database.DataBaseTable;
import database.Main;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;
import java.util.Objects;

public class AddAgentDialog extends JDialog {
    private final JTextField agentNameTextField = new JTextField("ФИО Агента:");
    private final JTextField agentSalaryTextField = new JTextField("Зарплата агента:");

    private final JTextField agentNameTextFieldEnter = new JTextField();
    private final JTextField agentSalaryTextFieldEnter = new JTextField();

    private final JButton addAgentButton = new JButton("Добавить агента");

    private DataBaseTable dbTable;

    public AddAgentDialog(DataBaseTable dbTable) {
        this.dbTable = dbTable;
        initListeners();
        initContainers();
    }

    private void initListeners() {
        addAgentButton.addActionListener(e -> {
            try {
                onAddAgent();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
    }

    private void initContainers() {
        Container mainContainer = new Container();
        mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));

        Container nameContainer = new Container();
        nameContainer.setLayout(new BoxLayout(nameContainer, BoxLayout.X_AXIS));
        agentNameTextField.setBorder(new EmptyBorder(0, 0, 0 ,0));
        agentNameTextField.setEditable(false);
        agentNameTextField.setHorizontalAlignment(SwingConstants.RIGHT);
        agentNameTextField.setMaximumSize(new Dimension(300, 30));
        nameContainer.add(agentNameTextField);
        agentNameTextFieldEnter.setMaximumSize(new Dimension(200, 30));
        nameContainer.add(agentNameTextFieldEnter);
        mainContainer.add(nameContainer);

        Container salaryContainer = new Container();
        salaryContainer.setLayout(new BoxLayout(salaryContainer, BoxLayout.X_AXIS));
        agentSalaryTextField.setBorder(new EmptyBorder(0, 0, 0 ,0));
        agentSalaryTextField.setEditable(false);
        agentSalaryTextField.setHorizontalAlignment(SwingConstants.RIGHT);
        agentSalaryTextField.setMaximumSize(new Dimension(300, 30));
        salaryContainer.add(agentSalaryTextField);
        agentSalaryTextFieldEnter.setMaximumSize(new Dimension(200, 30));
        salaryContainer.add(agentSalaryTextFieldEnter);
        mainContainer.add(salaryContainer);

        addAgentButton.setMaximumSize(new Dimension(500, 30));
        mainContainer.add(addAgentButton);
        add(mainContainer);
    }

    private void onAddAgent() throws SQLException {
        String FIO = agentNameTextFieldEnter.getText();
        String salary = agentSalaryTextFieldEnter.getText();

        if (Objects.equals(FIO, "")) {
            callAlert("Empty name");
            return;
        }

        try {
            if (Integer.parseInt(salary) <= 0) {
                callAlert("Salary less than 1");
            }
        } catch (NumberFormatException e) {
            callAlert("Wrong number");
            return;
        }

        dispose();
        String resultId = Main.sqlConnection.insertFunctionWithResult("Exec Add_Agent " + "'" + FIO + "'" + ", " + salary);

        String[] newAgent = {resultId, FIO, salary, "0", "0", "0"};
        dbTable.addRow(newAgent);
        dispose();
    }

    private void callAlert(String errorName) {
        AlertDialog alert = new AlertDialog(errorName);
        alert.pack();
        alert.setVisible(true);
    }

}

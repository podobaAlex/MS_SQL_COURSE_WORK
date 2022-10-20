package database.activities.admin;

import database.AlertJDialog;
import database.Main;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class ChangeSalaryActivity extends JPanel {

    private final JButton changeButton = new JButton("Change");
    private final JButton backButton = new JButton("Back");

    private final JTextField salaryTextField = new JTextField("Salary");
    private final JComboBox<String> comboBox;

    private final Container container = new Container();

    private String selectedId;

    public ChangeSalaryActivity(String[] IdAgents) {

        comboBox = new JComboBox<>(IdAgents);
        comboBox.setEditable(false);

        selectedId = IdAgents[0];

        initContainer();

        add(container);

    }

    private void initContainer() {

        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        initListeners();
        initFirstContainer();
        initSecondContainer();

    }

    private void initListeners() {

        comboBox.addActionListener(e -> {
            JComboBox<String> cb = (JComboBox<String>)e.getSource();
            selectedId = (String) cb.getSelectedItem();
        });

        changeButton.addActionListener(e -> {
            try {
                onChange(selectedId, salaryTextField.getText());
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        backButton.addActionListener(e -> onBack());

    }

    private void initFirstContainer() {

        Container firstContainer = new Container();

        firstContainer.setLayout(new BoxLayout(firstContainer, BoxLayout.X_AXIS));
        firstContainer.add(comboBox);
        firstContainer.add(salaryTextField);

        container.add(firstContainer);
    }

    private void initSecondContainer() {

        Container secondContainer = new Container();

        secondContainer.setLayout(new BoxLayout(secondContainer, BoxLayout.X_AXIS));
        secondContainer.add(changeButton);
        secondContainer.add(backButton);

        container.add(secondContainer);
    }

    private void onBack() {
        Main.frame.setContentPane(new AdminMenuActivity());
        Main.frame.setVisible(true);
    }

    private void onChange(String id, String salary) throws SQLException {
        try {
            Integer.parseInt(salary);
        } catch (NumberFormatException e) {
            callAlert("Wrong number");
            return;
        }
        Main.sqlConnection.insertFunction("Exec Change_Salary " + id + ", " + salary);

    }

    private void callAlert(String errorName) {
        AlertJDialog alert = new AlertJDialog(errorName);
        alert.pack();
        alert.setVisible(true);
    }

}

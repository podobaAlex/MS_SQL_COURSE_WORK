package database.activities.admin;

import database.dialogs.AlertDialog;
import database.Main;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;

public class ChangeSalaryActivity extends JPanel {

    private final JButton changeButton = new JButton("Изменить");
    private final JButton backButton = new JButton("Назад");

    private final JTextField salaryTextField = new JTextField("Зарплата:");
    private final JTextField idAgentTextField = new JTextField("Номер Агента:");

    private final JTextField salaryTextFieldEnter = new JTextField();
    private final JComboBox<String> comboBox;

    private final Container container = new Container();

    private String selectedId;

    public ChangeSalaryActivity(String[] IdAgents) {

        comboBox = new JComboBox<>(IdAgents);
        comboBox.setEditable(false);

        selectedId = IdAgents[0];

        initContainer();
        initListeners();

        add(container);

    }

    private void initContainer() {

        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        initFirstContainer();
        initSecondContainer();
        initButtonContainer();

        System.out.println(idAgentTextField.getPreferredSize());

    }

    private void initListeners() {

        comboBox.addActionListener(e -> selectedId = (String) comboBox.getSelectedItem());

        changeButton.addActionListener(e -> {
            try {
                onChange(selectedId, salaryTextFieldEnter.getText());
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        backButton.addActionListener(e -> onBack());

    }

    private void initFirstContainer() {

        Container firstContainer = new Container();

        firstContainer.setLayout(new BoxLayout(firstContainer, BoxLayout.X_AXIS));

        idAgentTextField.setEditable(false);
        idAgentTextField.setHorizontalAlignment(SwingConstants.RIGHT);
        idAgentTextField.setBorder(new EmptyBorder(0,0,0,0));
        idAgentTextField.setPreferredSize(new Dimension(80,30));
        firstContainer.add(idAgentTextField);
        firstContainer.add(comboBox);

        container.add(firstContainer);
        container.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    private void initSecondContainer() {

        Container secondContainer = new Container();

        salaryTextField.setEditable(false);
        salaryTextField.setHorizontalAlignment(SwingConstants.RIGHT);
        salaryTextField.setBorder(new EmptyBorder(0,0,0,0));
        salaryTextField.setPreferredSize(new Dimension(80,30));
        secondContainer.setLayout(new BoxLayout(secondContainer, BoxLayout.X_AXIS));
        secondContainer.add(salaryTextField);
        secondContainer.add(salaryTextFieldEnter);

        container.add(secondContainer);
        container.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    private void initButtonContainer() {

        Container buttonContainer = new Container();

        buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.X_AXIS));
        buttonContainer.add(changeButton);
        buttonContainer.add(backButton);

        container.add(buttonContainer);
    }

    private void onBack() {
        Main.frameAdmin.setContentPane(new AdminMenuActivity());
        Main.frameAdmin.setVisible(true);
    }

    private void onChange(String id, String salary) throws SQLException {
        int salaryInt;
        try {
            salaryInt = Integer.parseInt(salary);
        } catch (NumberFormatException e) {
            callAlert("Wrong number");
            return;
        }

        if (salaryInt <= 0) {
            callAlert("Salary less than 1");
            return;
        }

        Main.sqlConnection.insertFunction("Exec Change_Salary " + id + ", " + salary);

    }

    private void callAlert(String errorName) {
        AlertDialog alert = new AlertDialog(errorName);
        alert.pack();
        alert.setVisible(true);
    }

}

package database.activities.admin;

import database.dialogs.AlertDialog;
import database.Main;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;

public class DeleteAgentActivity extends JPanel {

    private final JButton deleteButton = new JButton("Удалить");
    private final JButton backButton = new JButton("Назад");

    private final JTextField idAgentTextField = new JTextField("Номер агента:");

    private final JComboBox<String> comboBox;

    private final Container container = new Container();

    private String selectedId;

    public DeleteAgentActivity(String[] IdAgents) {

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
        initButtonContainer();

    }

    private void initListeners() {

        comboBox.addActionListener(e -> selectedId = (String) comboBox.getSelectedItem());

        deleteButton.addActionListener(e -> {
            try {
                onDelete(selectedId);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        backButton.addActionListener(e -> onBack());
    }

    private void initFirstContainer() {

        Container firstContainer = new Container();

        firstContainer.setLayout(new BoxLayout(firstContainer, BoxLayout.X_AXIS));
        idAgentTextField.setHorizontalAlignment(SwingConstants.RIGHT);
        idAgentTextField.setEditable(false);
        idAgentTextField.setBorder(new EmptyBorder(0,0,0,0));
        firstContainer.add(idAgentTextField);
        firstContainer.add(comboBox);

        container.add(firstContainer);
        container.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    private void initButtonContainer() {

        Container buttonContainer = new Container();

        buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.X_AXIS));
        buttonContainer.add(deleteButton);
        buttonContainer.add(backButton);

        container.add(buttonContainer);

    }

    private void onBack() {
        Main.frame.setContentPane(new AdminMenuActivity());
        Main.frame.setVisible(true);
    }

    private void onDelete(String id) throws SQLException {
        try {
            Integer.parseInt(id);
        } catch (NumberFormatException e) {
            callAlert("Wrong number");
            return;
        }
        try {
            Main.sqlConnection.insertFunction("Exec Del_Agent " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        comboBox.removeItem(id);
    }

    private void callAlert(String errorName) {
        AlertDialog alert = new AlertDialog(errorName);
        alert.pack();
        alert.setVisible(true);
    }

}

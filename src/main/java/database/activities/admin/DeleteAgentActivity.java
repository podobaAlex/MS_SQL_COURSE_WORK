package database.activities.admin;

import database.AlertJDialog;
import database.Main;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class DeleteAgentActivity extends JPanel {

    private final JButton deleteButton = new JButton("Delete");
    private final JButton backButton = new JButton("Back");

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

        container.add(comboBox);

        initListeners();
        initFirstContainer();

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
        firstContainer.add(deleteButton);
        firstContainer.add(backButton);

        container.add(firstContainer);
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
        Main.sqlConnection.insertFunction("Exec Del_Agent " + id);
        comboBox.removeItem(id);
        //revalidate();
    }

    private void callAlert(String errorName) {
        AlertJDialog alert = new AlertJDialog(errorName);
        alert.pack();
        alert.setVisible(true);
    }

}

package database;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class addAgentActivity extends JPanel {

    private final JTextField agentFIO = new JTextField("FIO", 30);
    private final JTextField agentSalary = new JTextField("Salary", 30);

    private final JButton addAgentButton = new JButton("Add");
    private final JButton backButton = new JButton("Back");

    private final Container containerOfButtons = new Container();
    private final Container container = new Container();

    public addAgentActivity() {

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
        containerOfButtons.add(addAgentButton);
        containerOfButtons.add(backButton);

        containerOfButtons.setLayout(new BoxLayout(containerOfButtons, BoxLayout.X_AXIS));
    }

    private void initListeners() {
        addAgentButton.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                addAgent(agentFIO.getText(), agentSalary.getText());}
        });

        backButton.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {onBack();}
        });
    }

    private void onBack() {
        Main.frame.setContentPane(new Menu());
        Main.frame.pack();
    }

    private void addAgent(String FIO, String Salary) {
        if (Objects.equals(FIO, "")) {
            callAlert("Empty name");
            return;
        }

        try {
            Integer.parseInt(Salary);
        } catch (NumberFormatException e) {
            callAlert("Wrong number");
            return;
        }

    }

    private void callAlert(String errorName) {
        AlertJDialog alert = new AlertJDialog(errorName);
        alert.pack();
        alert.setVisible(true);
    }

}

package database;

import javax.swing.*;
import java.awt.event.*;

public class AdminMenu extends JDialog {
    private JPanel contentPane;
    private JButton catalogueButton;

    public AdminMenu() {
        setContentPane(contentPane);
        setModal(true);

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        catalogueButton.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) { showCatalogue(); }
        });

    }

    private void showCatalogue() {

    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

//    public static void main(String[] args) {
//        AdminMenu dialog = new AdminMenu();
//        dialog.pack();
//        dialog.setVisible(true);
//        System.exit(0);
//    }
}

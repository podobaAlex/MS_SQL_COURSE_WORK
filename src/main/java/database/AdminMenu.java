package database;

import javax.swing.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.Arrays;

public class AdminMenu extends JDialog {
    private JPanel contentPane;
    private JButton catalogueButton;
    private JButton button1;
    private JButton statisticButton;

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
            @Override public void actionPerformed(ActionEvent e)  { try {showCatalogue();} catch (SQLException er) {er.printStackTrace();}}
        });

        statisticButton.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e)  { try {showStatistic();} catch (SQLException er) {er.printStackTrace();}}
        });

    }

    private void showStatistic() throws SQLException {
        String[] columnsName = {"IdAgent", "FIO", "Salary", "RealizedOrders", "Cash","Orders"};
        String result = null;
        result = Main.sqlConnection.selectFunction("Exec Stat", columnsName.length);

        Object[][] res = Arrays.stream(
                result.split(";")
        ).map(
                i -> i.split(",")
        ).toArray(Object[][]::new);

        DataBaseTable tableFrame = new DataBaseTable(res, columnsName);

        JFrame frame = new JFrame("Table");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tableFrame.setOpaque(true);
        frame.setContentPane(tableFrame);

        frame.pack();
        frame.setVisible(true);
        dispose();
    }

    private void showCatalogue() throws SQLException {

        String[] columnsName = {"IdProd", "Name", "Num"};
        String result = null;
        result = Main.sqlConnection.selectFunction("Exec Catalog_Prod", columnsName.length);

        Object[][] res = Arrays.stream(
                result.split(";")
        ).map(
                i -> i.split(",")
        ).toArray(Object[][]::new);

        DataBaseTable tableFrame = new DataBaseTable(res, columnsName);

        JFrame frame = new JFrame("Table");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tableFrame.setOpaque(true);
        frame.setContentPane(tableFrame);

        frame.pack();
        frame.setVisible(true);
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

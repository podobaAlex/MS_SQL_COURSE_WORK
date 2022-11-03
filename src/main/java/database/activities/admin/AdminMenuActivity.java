package database.activities.admin;

import database.Main;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class AdminMenuActivity extends JPanel {

    private final Container container = new Container();

    private final JButton statisticButton = new JButton("Статистика");
    private final JButton catalogueButton = new JButton("Каталог");

    public AdminMenuActivity() {

        setLayout(new BorderLayout());
        add(Box.createRigidArea(new Dimension(0, 50)), BorderLayout.NORTH);
        add(Box.createRigidArea(new Dimension(0, 50)), BorderLayout.SOUTH);

        initListeners();
        initContainer();

    }

    private void addButton(JButton button) {
        button.setMaximumSize(new Dimension(400, 30));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(button);
        container.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    private void initListeners() {
        catalogueButton.addActionListener(e -> { try {showCatalogue();} catch (SQLException ex) {ex.printStackTrace();}});
        statisticButton.addActionListener(e -> { try {showStatistic();} catch (SQLException ex) {ex.printStackTrace();}});
    }

    private void initContainer() {
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        addButton(statisticButton);
        addButton(catalogueButton);

        add(container, BorderLayout.CENTER);
    }

    private void showStatistic() throws SQLException {
        StatisticsActivity statisticsActivity = new StatisticsActivity();
        Main.frameAdmin.setContentPane(statisticsActivity);
        Main.frameAdmin.setVisible(true);
    }

    private void showCatalogue() throws SQLException {

        AdminCatalogueActivity adminCatalogueActivity = new AdminCatalogueActivity();

        Main.frameAdmin.setContentPane(adminCatalogueActivity);
        Main.frameAdmin.setVisible(true);
    }

}

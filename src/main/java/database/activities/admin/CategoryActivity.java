package database.activities.admin;

import database.Main;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;

public class CategoryActivity extends JPanel {

    private final JTextField nameCategory = new JTextField("Название новой категории:");
    private final JTextField nameCategoryEnter = new JTextField();

    private final JButton addButton = new JButton("Добавить");
    private final JButton backButton = new JButton("Назад");

    private final Container container = new Container();

    public CategoryActivity() {

        initListeners();
        initContainer();

    }

    private void initContainer() {
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        initFirstContainer();
        initButtonContainer();
        add(container);
    }

    private void initButtonContainer() {
        Container newContainer = new Container();
        newContainer.setLayout(new BoxLayout(newContainer, BoxLayout.X_AXIS));
        newContainer.add(addButton);
        newContainer.add(backButton);
        container.add(newContainer);
    }

    private void initFirstContainer() {
        Container newContainer = new Container();
        newContainer.setLayout(new BoxLayout(newContainer, BoxLayout.X_AXIS));
        nameCategory.setEditable(false);
        nameCategory.setHorizontalAlignment(SwingConstants.RIGHT);
        nameCategory.setPreferredSize(new Dimension(200, 30));
        nameCategory.setBorder(new EmptyBorder(0,0,0,0));
        newContainer.add(nameCategory);
        nameCategoryEnter.setPreferredSize(new Dimension(200, 30));
        newContainer.add(nameCategoryEnter);
        container.add(newContainer);
    }

    private void initListeners() {
        addButton.addActionListener(e -> {try {onAdd();} catch (SQLException ex) {ex.printStackTrace();}});
        backButton.addActionListener(e -> onBack());
    }

    private void onAdd() throws SQLException {
        //TODO
    }

    private void onBack() {
        Main.frame.setContentPane(new AdminMenuActivity());
        Main.frame.setVisible(true);
    }

}

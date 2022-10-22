package database.fragments.admin;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AddNewCategoryFragment extends JPanel {

    private final JTextField nameCategory = new JTextField("Название новой категории:");
    private final JTextField nameCategoryEnter = new JTextField();

    private final Container container = new Container();

    public AddNewCategoryFragment() {

        initContainer();

    }

    private void initContainer() {
        container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));
        nameCategory.setEditable(false);
        nameCategory.setHorizontalAlignment(SwingConstants.RIGHT);
        nameCategory.setPreferredSize(new Dimension(200, 30));
        nameCategory.setBorder(new EmptyBorder(0,0,0,0));
        container.add(nameCategory);
        nameCategoryEnter.setPreferredSize(new Dimension(200, 30));
        container.add(nameCategoryEnter);
        add(container);
    }

    public String getNameCategory() {
        return nameCategoryEnter.getText();
    }

}

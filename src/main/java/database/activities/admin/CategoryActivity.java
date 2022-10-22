package database.activities.admin;

import database.Main;
import database.fragments.admin.AddCategoryToProductFragment;
import database.fragments.admin.AddNewCategoryFragment;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.sql.SQLException;

public class CategoryActivity extends JPanel {

    private JPanel currentPane;

    private final JToggleButton switcher = new JToggleButton("Добавить категорию продукту");

    private final JButton addButton = new JButton("Добавить");
    private final JButton backButton = new JButton("Назад");

    private final Container container = new Container();

    public CategoryActivity() {

        initAddNewCategoryFragment();

        setLayout(new BorderLayout());

        initListeners();
        initContainer();

    }

    private void initContainer() {
        container.setMaximumSize(new Dimension(250, 250));
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        switcher.setAlignmentX(CENTER_ALIGNMENT);
        container.add(currentPane);
        container.add(switcher);
        container.add(Box.createRigidArea(new Dimension(0, 10)));
        initButtonContainer();
        add(container, BorderLayout.CENTER);
    }

    private void initButtonContainer() {
        Container newContainer = new Container();
        newContainer.setLayout(new BoxLayout(newContainer, BoxLayout.X_AXIS));
        newContainer.add(addButton);
        newContainer.add(backButton);
        container.add(newContainer);
    }

    private void initAddNewCategoryFragment() {
        currentPane = new AddNewCategoryFragment();
    }

    private void initAddCategoryToProductFragment() throws SQLException {
        currentPane = new AddCategoryToProductFragment();
    }

    private void initListeners() {
        addButton.addActionListener(e -> {try {onAdd();} catch (SQLException ex) {ex.printStackTrace();}});
        backButton.addActionListener(e -> onBack());
        switcher.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                try {
                    initAddCategoryToProductFragment();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            else {
                initAddNewCategoryFragment();
            }
            container.remove(0);
            container.add(currentPane,0);
            revalidate();
        });
    }

    private void onAdd() throws SQLException {
        if (currentPane instanceof AddNewCategoryFragment) {
            addNewCategory((AddNewCategoryFragment) currentPane);
        }
        else if (currentPane instanceof AddCategoryToProductFragment) {
            addCategoryToProduct((AddCategoryToProductFragment) currentPane);
        }
    }

    private void addNewCategory(AddNewCategoryFragment fragment) throws SQLException {
        String categoryName = fragment.getNameCategory();

        Main.sqlConnection.insertFunction("Exec NewCategory " + categoryName);
    }

    private void addCategoryToProduct(AddCategoryToProductFragment fragment) throws SQLException {
        String categoryId = fragment.getSelectedCategoryId();
        String productId = fragment.getSelectedProductId();

        Main.sqlConnection.insertFunction(
                "Exec AddCategoryToProduct "
                        + productId
                        + ", " + categoryId
        );
    }

    private void onBack() {
        Main.frame.setContentPane(new AdminMenuActivity());
        Main.frame.setVisible(true);
    }

}

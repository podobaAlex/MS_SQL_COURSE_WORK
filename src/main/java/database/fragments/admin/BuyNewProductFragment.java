package database.fragments.admin;

import javax.swing.*;
import java.awt.*;

public class BuyNewProductFragment extends JPanel {

    private final JTextField nameTextField = new JTextField("Name");
    private final JTextField buyCostTextField = new JTextField("Buy cost");
    private final JTextField sellCostTextField = new JTextField("Sell cost");
    private final JTextField numTextField = new JTextField("Num");

    private final Container container = new Container();

    public BuyNewProductFragment() {
        initContainer();
    }

    private void initContainer() {
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        nameTextField.setAlignmentX(CENTER_ALIGNMENT);
        container.add(nameTextField);

        buyCostTextField.setAlignmentX(CENTER_ALIGNMENT);
        container.add(buyCostTextField);

        sellCostTextField.setAlignmentX(CENTER_ALIGNMENT);
        container.add(sellCostTextField);

        numTextField.setAlignmentX(CENTER_ALIGNMENT);
        container.add(numTextField);

        add(container);
    }

    public String getNameProduct() {
        return nameTextField.getText();
    }

    public String getBuyCost() {
        return buyCostTextField.getText();
    }

    public String getSellCost() {
        return sellCostTextField.getText();
    }

    public String getNumProduct() {
        return numTextField.getText();
    }

}

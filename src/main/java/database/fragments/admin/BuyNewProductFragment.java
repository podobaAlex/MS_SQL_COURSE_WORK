package database.fragments.admin;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class BuyNewProductFragment extends JPanel {

    private final JTextField nameTextField = new JTextField("Название:");
    private final JTextField buyCostTextField = new JTextField("Цена покупки:");
    private final JTextField sellCostTextField = new JTextField("Цена продажи:");
    private final JTextField numTextField = new JTextField("Количество:");

    private final JTextField nameTextFieldEnter = new JTextField();
    private final JTextField buyCostTextFieldEnter = new JTextField();
    private final JTextField sellCostTextFieldEnter = new JTextField();
    private final JTextField numTextFieldEnter = new JTextField();

    private final Container container = new Container();

    public BuyNewProductFragment() {
        initContainer();
    }

    private void initContainer() {

        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        initFirstContainer();
        initSecondContainer();
        initThirdContainer();
        initFourthContainer();

        add(container);
    }

    private void initFirstContainer() {
        Container newContainer = new Container();
        newContainer.setLayout(new BoxLayout(newContainer, BoxLayout.X_AXIS));
        nameTextField.setHorizontalAlignment(SwingConstants.RIGHT);
        nameTextField.setEditable(false);
        nameTextField.setBorder(new EmptyBorder(0, 0, 0, 0));
        newContainer.add(nameTextField);
        nameTextFieldEnter.setPreferredSize(new Dimension(250, 30));
        newContainer.add(nameTextFieldEnter);
        container.add(newContainer);
        container.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    private void initSecondContainer() {
        Container newContainer = new Container();
        newContainer.setLayout(new BoxLayout(newContainer, BoxLayout.X_AXIS));
        buyCostTextField.setHorizontalAlignment(SwingConstants.RIGHT);
        buyCostTextField.setEditable(false);
        buyCostTextField.setBorder(new EmptyBorder(0, 0, 0, 0));
        newContainer.add(buyCostTextField);
        buyCostTextFieldEnter.setPreferredSize(new Dimension(250, 30));
        newContainer.add(buyCostTextFieldEnter);
        container.add(newContainer);
        container.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    private void initThirdContainer() {
        Container newContainer = new Container();
        newContainer.setLayout(new BoxLayout(newContainer, BoxLayout.X_AXIS));
        sellCostTextField.setHorizontalAlignment(SwingConstants.RIGHT);
        sellCostTextField.setEditable(false);
        sellCostTextField.setBorder(new EmptyBorder(0, 0, 0, 0));
        newContainer.add(sellCostTextField);
        sellCostTextFieldEnter.setPreferredSize(new Dimension(250, 30));
        newContainer.add(sellCostTextFieldEnter);
        container.add(newContainer);
        container.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    private void initFourthContainer() {
        Container newContainer = new Container();
        newContainer.setLayout(new BoxLayout(newContainer, BoxLayout.X_AXIS));
        numTextField.setHorizontalAlignment(SwingConstants.RIGHT);
        numTextField.setEditable(false);
        numTextField.setBorder(new EmptyBorder(0, 0, 0, 0));
        newContainer.add(numTextField);
        numTextFieldEnter.setPreferredSize(new Dimension(250, 30));
        newContainer.add(numTextFieldEnter);
        container.add(newContainer);
        container.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    public String getNameProduct() {
        return nameTextFieldEnter.getText();
    }

    public String getBuyCost() {
        return buyCostTextFieldEnter.getText();
    }

    public String getSellCost() {
        return sellCostTextFieldEnter.getText();
    }

    public String getNumProduct() {
        return numTextFieldEnter.getText();
    }

}

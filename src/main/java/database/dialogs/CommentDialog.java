package database.dialogs;

import database.Main;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class CommentDialog extends JDialog {

    private final String userId = Integer.toString(Main.USERID);
    private final String productId;

    private final JTextField commentTextField = new JTextField();

    private final JButton commentButton = new JButton("Отправить");

    public CommentDialog(String productId) {

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        initListeners();
        initContainer();

        this.productId = productId;
    }

    private void initContainer() {
        Container container = new Container();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        commentTextField.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(commentTextField);

        commentButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(commentButton);

        add(container);

    }

    private void initListeners() {
        commentButton.addActionListener(e -> {try {onComment();} catch (SQLException ex) {ex.printStackTrace();}});
    }

    private void onComment() throws SQLException {
        String comment = "'" + commentTextField.getText() + "'";
        Main.sqlConnection.insertFunction(
                "Exec CommentProduct "
                        + userId
                        + "," + productId
                        + "," + comment
        );
        dispose();
    }

}

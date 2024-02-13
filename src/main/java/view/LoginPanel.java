package view;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel {

    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton backButton;

    private ActionListener actionListener;

    public LoginPanel() {
        initializeComponents();
        layoutComponents();
    }

    private void initializeComponents() {
        usernameLabel = new JLabel("Username:");
        passwordLabel = new JLabel("Password:");
        usernameField = new JTextField(15);
        passwordField = new JPasswordField(15);
        loginButton = new JButton("Login");
        backButton = new JButton("Back");
    }

    private void layoutComponents() {
        setLayout(new MigLayout("", "[grow][grow][grow]", "[grow][grow][grow][grow][grow][grow]"));
        add(usernameLabel, "cell 0 0, align right");
        add(usernameField, "cell 1 0, span 2, align left");
        add(passwordLabel, "cell 0 1, align right");
        add(passwordField, "cell 1 1, span 2, align left");
        add(loginButton, "cell 2 5, align right");
        add(backButton, "cell 0 5, align left");
    }

    private void activateComponents() {
        loginButton.addActionListener(actionListener);
        backButton.addActionListener(actionListener);
    }

    public void setActionListener(ActionListener actionListener) {
        this.actionListener = actionListener;
        activateComponents();
    }

    public void clearFields() {
        usernameField.setText("");
        passwordField.setText("");
    }

    public JTextField getUsernameField() {
        return usernameField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public JButton getBackButton() {
        return backButton;
    }
}

package view;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.ActionListener;

public class RegisterPanel extends JPanel {

    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JLabel confirmPasswordLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JButton registerButton;
    private JButton backButton;

    private ActionListener actionListener;

    public RegisterPanel() {
        initializeComponents();
        layoutComponents();
    }

    private void initializeComponents() {
        usernameLabel = new JLabel("Username:");
        passwordLabel = new JLabel("Password:");
        confirmPasswordLabel = new JLabel("Confirm password:");
        usernameField = new JTextField(15);
        passwordField = new JPasswordField(15);
        confirmPasswordField = new JPasswordField(15);
        registerButton = new JButton("Register");
        backButton = new JButton("Back");
    }

    private void layoutComponents() {
        setLayout(new MigLayout("", "[grow][grow]", "[grow][grow][grow][grow][grow][grow]"));
        add(usernameLabel, "cell 0 0, align right");
        add(usernameField, "cell 1 0, align left");
        add(passwordLabel, "cell 0 1, align right");
        add(passwordField, "cell 1 1, align left");
        add(confirmPasswordLabel, "cell 0 2, align right");
        add(confirmPasswordField, "cell 1 2, align left");
        add(registerButton, "cell 1 5, align right");
        add(backButton, "cell 0 5, align left");
    }

    private void activateComponents() {
        registerButton.addActionListener(actionListener);
        backButton.addActionListener(actionListener);
    }

    public void setActionListener(ActionListener actionListener) {
        this.actionListener = actionListener;
        activateComponents();
    }

    public void clearFields() {
        usernameField.setText("");
        passwordField.setText("");
        confirmPasswordField.setText("");
    }

    public JTextField getUsernameField() {
        return usernameField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public JPasswordField getConfirmPasswordField() {
        return confirmPasswordField;
    }

    public JButton getRegisterButton() {
        return registerButton;
    }

    public JButton getBackButton() {
        return backButton;
    }
}
package view;

import controller.DatabaseService;
import lombok.Setter;
import model.User;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel implements ActionListener{

    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton backButton;

    @Setter
    private CardLayout cardLayout;
    @Setter private JPanel mainPanel;

    @Setter private DatabaseService databaseService;

    public LoginPanel() {
        initializeComponents();
        layoutComponents();
        activateComponents();
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
        loginButton.addActionListener(this);
        backButton.addActionListener(this);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean loginPressed = e.getSource() == loginButton;
        boolean backPressed = e.getSource() == backButton;

        if (loginPressed) {
            handleLoginPressed();
        }
        if (backPressed) {
            handleBackPressed();
        }
    }

    private void handleLoginPressed() {
        String username = usernameField.getText();
        User user;
        try {
            user = databaseService.getUserFromDatabaseByUsername(username);
            validateUserDatabaseData(user);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "User not found", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void validateUserDatabaseData(User user) {
        String password = String.valueOf(passwordField.getPassword());
        if (user.getPassword().equals(password)) {
            // TODO open user notes panel
            System.out.println("User logged in");
        } else {
            JOptionPane.showMessageDialog(this, "Incorrect password", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleBackPressed() {
        cardLayout.show(mainPanel, "AuthPanel");
    }
}

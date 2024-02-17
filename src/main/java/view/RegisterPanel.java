package view;

import controller.DatabaseServiceSingleton;
import controller.UsersDatabaseConnection;
import model.User;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterPanel extends JPanel implements ActionListener{

    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JLabel confirmPasswordLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JButton registerButton;
    private JButton backButton;

    private DatabaseServiceSingleton databaseServiceSingleton;
    private MainFrame mainFrame;
    private final ValidationFormData validationFormData = new ValidationFormData();

    public RegisterPanel() {
        initializeComponents();
        layoutComponents();
        activateComponents();
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

        databaseServiceSingleton = DatabaseServiceSingleton.getInstance(new UsersDatabaseConnection());
        mainFrame = MainFrame.getInstance();
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
        registerButton.addActionListener(this);
        backButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean registerPressed = e.getSource() == registerButton;
        boolean backPressed = e.getSource() == backButton;

        if (registerPressed) {
            handleRegisterPressed();
        }
        if (backPressed) {
            handleBackPressed();
        }
    }

    private void handleBackPressed() {
        mainFrame.hidePanel(this);
        mainFrame.showPanel(mainFrame.getAuthPanel());
    }

    private void handleRegisterPressed() {
        String userName = usernameField.getText();
        String password = String.valueOf(passwordField.getPassword());
        String confirmPassword = String.valueOf(confirmPasswordField.getPassword());
        if (validationFormData.isPasswordDataCorrect(password, confirmPassword)) {
            databaseServiceSingleton.addUserToDatabase(new User(userName, password));
            JOptionPane.showMessageDialog(this, "User registered successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            handleBackPressed();
        }
        cleanRegistrationFields();
    }

    private void cleanRegistrationFields() {
        usernameField.setText("");
        passwordField.setText("");
        confirmPasswordField.setText("");
    }

//    private boolean isPasswordDataCorrect(String password, String confirmPassword) {
//        if (!password.equals(confirmPassword)) {
//            JOptionPane.showMessageDialog(this, "Passwords do not match", "Error", JOptionPane.ERROR_MESSAGE);
//            return false;
//        }
//        if (password.length() < 4) {
//            JOptionPane.showMessageDialog(this, "Password must be at least 4 characters long", "Error", JOptionPane.ERROR_MESSAGE);
//            return false;
//        }
//        return true;
//    }

    private class ValidationFormData {
        private boolean isPasswordDataCorrect(String password, String confirmPassword) {
            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(RegisterPanel.this, "Passwords do not match", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            if (password.length() < 4) {
                JOptionPane.showMessageDialog(RegisterPanel.this, "Password must be at least 4 characters long", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            return true;
        }
    }
}

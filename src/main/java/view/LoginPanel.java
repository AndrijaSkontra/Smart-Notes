package view;

import controller.services.DatabaseServiceSingleton;
import controller.UsersDatabaseConnection;
import lombok.Getter;
import model.User;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class responsible for log in the user in the application.
 */
public class LoginPanel extends JPanel implements ActionListener{

    private JLabel usernameLabel;
    private JLabel passwordLabel;
    @Getter
    private JTextField usernameField;
    @Getter
    private JPasswordField passwordField;
    @Getter
    private JButton loginButton;
    @Getter
    private JButton backButton;

    private MainFrame mainFrame;
    private DatabaseServiceSingleton databaseService;

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

        mainFrame = MainFrame.getInstance();
        databaseService = DatabaseServiceSingleton.getInstance(UsersDatabaseConnection.getInstance());
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

        user = databaseService.getUserFromDatabaseByUsername(username);
        if (user == null) {
            JOptionPane.showMessageDialog(this, "No user with this username in database", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        boolean isPasswordCorrect = ValidateLogin.isPasswordDataCorrect(user.getPassword(), String.valueOf(passwordField.getPassword()));
        if (isPasswordCorrect) {
            handleLoginSuccesfull(user);
        } else {
            JOptionPane.showMessageDialog(this, "Incorrect credientials", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleLoginSuccesfull(User user) {
        mainFrame.hidePanel(this);
        mainFrame.showPanel(mainFrame.getUserPanel());
        mainFrame.setSize(600, 500);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.getUserPanel().initializeUserInUserPanel(user);
        passwordField.setText("");
        usernameField.setText("");
    }

    private void handleBackPressed() {
        mainFrame.hidePanel(this);
        mainFrame.showPanel(mainFrame.getAuthPanel());
    }

    /**
     * Validate login data from user.
     */
    private class ValidateLogin {
        private static boolean isPasswordDataCorrect(String password, String confirmPassword) {
            return password.equals(confirmPassword);
        }
    }

}

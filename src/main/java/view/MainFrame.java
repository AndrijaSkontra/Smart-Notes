package view;

import model.User;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {

    private AuthPanel authPanel;
    private LoginPanel loginPanel;
    private RegisterPanel registerPanel;

    public MainFrame() {
        setupFrame();
        initFrameComponents();
        activateFrame();

        setShownPanel(authPanel);
    }

    private void hidePanel(JPanel panel) {
        panel.setVisible(false);
        add(panel, "hidemode 3");
    }

    private void setShownPanel(JPanel panel) {
        panel.setVisible(true);
        add(panel, "w 100%, h 100%");
    }

    private void initFrameComponents() {
        authPanel = new AuthPanel();
        loginPanel = new LoginPanel();
        registerPanel = new RegisterPanel();
    }


    private void activateFrame() {
        activateAuthPanel();
        activateLoginPanel();
        activateRegisterPanel();
    }

    private void activateRegisterPanel() {
        registerPanel.setActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean registerPressed = e.getSource() == registerPanel.getRegisterButton();
                boolean backPressed = e.getSource() == registerPanel.getBackButton();
                if (registerPressed) {
                    String userName = registerPanel.getUsernameField().getText();
                    String password = String.valueOf(registerPanel.getPasswordField().getPassword());
                    String confirmPassword = String.valueOf(registerPanel.getConfirmPasswordField().getPassword());
                    if (!isPasswordCorrect(password, confirmPassword)) {
                        registerPanel.getUsernameField().setText("");
                        registerPanel.getPasswordField().setText("");
                        registerPanel.getConfirmPasswordField().setText("");
                    }
                    User user = new User(userName, password);
                }
                if (backPressed) {
                    hidePanel(registerPanel);
                    setShownPanel(authPanel);
                }
            }
        });
    }

    private boolean isPasswordCorrect(String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(MainFrame.this, "Passwords do not match", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (password.length() < 3) {
            JOptionPane.showMessageDialog(MainFrame.this, "Password must be at least 3 characters long", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void activateLoginPanel() {
        loginPanel.setActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean loginPressed = e.getSource() == loginPanel.getLoginButton();
                boolean backPressed = e.getSource() == loginPanel.getBackButton();
                if (loginPressed) {
                    System.out.println("Login pressed");
                }
                if (backPressed) {
                    hidePanel(loginPanel);
                    setShownPanel(authPanel);
                }
            }
        });
    }

    private void activateAuthPanel() {
        authPanel.setActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean loginPressed = e.getSource() == authPanel.getLoginButton();
                boolean registerPressed = e.getSource() == authPanel.getRegisterButton();
                if (loginPressed) {
                    hidePanel(authPanel);
                    setShownPanel(loginPanel);
                }
                if (registerPressed) {
                    hidePanel(authPanel);
                    setShownPanel(registerPanel);
                }
            }
        });
    }

    private void setupFrame() {
        setTitle("Simple notes");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setLayout(new MigLayout());
    }
}

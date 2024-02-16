package view;

import controller.DatabaseService;
import controller.UsersDatabaseConnection;
import model.User;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel mainPanel = new JPanel(cardLayout);

    private AuthPanel authPanel;
    private LoginPanel loginPanel;
    private RegisterPanel registerPanel;
    private final DatabaseService databaseService = new DatabaseService(new UsersDatabaseConnection());

    public MainFrame() {
        setupFrame();
        initFrameComponents();

        mainPanel.add(authPanel, "AuthPanel");
        mainPanel.add(loginPanel, "LoginPanel");
        mainPanel.add(registerPanel, "RegisterPanel");

        activateFrame();

        setLayout(new MigLayout());
        add(mainPanel, "w 100%, h 100%");

    }

    private void initFrameComponents() {
        authPanel = new AuthPanel();
        loginPanel = new LoginPanel();
        registerPanel = new RegisterPanel();
        // TODO cleaning needed
        registerPanel.setCardLayout(cardLayout);
        registerPanel.setMainPanel(mainPanel);
        registerPanel.setDatabaseService(databaseService);
    }

    private void activateFrame() {
        activateAuthPanel();
        activateLoginPanel();
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
                    cardLayout.show(mainPanel, "AuthPanel");
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
                    cardLayout.show(mainPanel, "LoginPanel");
                }
                if (registerPressed) {
                    cardLayout.show(mainPanel, "RegisterPanel");
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

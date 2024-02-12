package view;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class MainFrame extends JFrame {

    private AuthPanel authPanel;
    private LoginPanel loginPanel;
    private RegisterPanel registerPanel;

    public MainFrame() {
        setupFrame();
        initFrame();
        layoutComponents();
        activateFrame();
    }

    private void initFrame() {
        authPanel = new AuthPanel();
        loginPanel = new LoginPanel();
        registerPanel = new RegisterPanel();
    }

    private void layoutComponents() {
        setLayout(new MigLayout());
        // add(authPanel, "w 100%, h 100%");
        add(registerPanel, "w 100%, h 100%");
    }

    private void activateFrame() {

    }

    private void setupFrame() {
        setTitle("Simple notes");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}

package view;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class AuthPanel extends JPanel {

    private JButton loginButton;
    private JButton registerButton;

    public AuthPanel() {

        initiliazeComponents();
        layoutComponents();
        activateComponents();
    }

    private void initiliazeComponents() {
        loginButton = new JButton("Login");
        registerButton = new JButton("Register");
    }

    private void layoutComponents() {
        setLayout(new MigLayout("", "[grow]", "[grow][grow][grow][grow][grow]"));
        add(loginButton, "cell 0 1, align center");
        add(registerButton, "cell 0 2, align center");
    }

    private void activateComponents() {

    }
}

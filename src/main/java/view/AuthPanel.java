package view;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.ActionListener;

public class AuthPanel extends JPanel {

    private JButton loginButton;
    private JButton registerButton;

    private ActionListener actionListener;

    public AuthPanel() {

        initiliazeComponents();
        layoutComponents();

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
        loginButton.addActionListener(actionListener);
        registerButton.addActionListener(actionListener);
    }

    public void setActionListener(ActionListener actionListener) {
        this.actionListener = actionListener;
        activateComponents();
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public JButton getRegisterButton() {
        return registerButton;
    }
}

package view;

import lombok.Getter;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AuthPanel extends JPanel implements ActionListener {

    @Getter
    private JButton loginButton;
    @Getter
    private JButton registerButton;

    private MainFrame mainFrame;

    public AuthPanel() {

        initiliazeComponents();
        layoutComponents();
        activateComponents();
    }

    private void initiliazeComponents() {
        loginButton = new JButton("Login");
        registerButton = new JButton("Register");
        mainFrame = MainFrame.getInstance();
    }

    private void layoutComponents() {
        setLayout(new MigLayout("", "[grow]", "[grow][grow][grow][grow][grow]"));
        add(loginButton, "cell 0 1, align center");
        add(registerButton, "cell 0 2, align center");
    }

    private void activateComponents() {
        loginButton.addActionListener(this);
        registerButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean loginPressed = e.getSource() == getLoginButton();
        boolean registerPressed = e.getSource() == getRegisterButton();
        if (loginPressed) {
            mainFrame.hidePanel(mainFrame.getAuthPanel());
            mainFrame.showPanel(mainFrame.getLoginPanel());
        }
        if (registerPressed) {
            mainFrame.hidePanel(mainFrame.getAuthPanel());
            mainFrame.showPanel(mainFrame.getRegisterPanel());
        }
    }
}

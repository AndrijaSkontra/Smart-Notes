package view;

import lombok.Setter;
import model.User;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserPanel extends JPanel implements ActionListener {

    private JLabel usernameLabel;
    private JTextArea notesTextArea;
    private JButton addNoteButton;
    private JButton signOutButton;
    private JButton seeNotesButton;

    private MainFrame mainFrame;

    private User user;

    public UserPanel() {
        initializeComponents();
        layoutComponents();
        activateComponents();
    }

    private void initializeComponents() {
        usernameLabel = new JLabel();
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        notesTextArea = new JTextArea();
        addNoteButton = new JButton("Add note");
        signOutButton = new JButton("Sign out");
        seeNotesButton = new JButton("See notes");
        mainFrame = MainFrame.getInstance();
    }

    private void layoutComponents() {
        setLayout(new MigLayout("", "[grow][grow]", "[grow][grow][grow][grow][grow]"));
        add(usernameLabel, "cell 0 0, spanx 2, align center");
        add(notesTextArea, "grow, cell 0 1, spany 3, align center");
        add(addNoteButton, "cell 0 4, align center");
        add(seeNotesButton, "cell 1 3, align right");
        add(signOutButton, "cell 1 4, align right");
    }

    private void activateComponents() {
        addNoteButton.addActionListener(this);
        signOutButton.addActionListener(this);
        seeNotesButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean addNotePressed = e.getSource() == addNoteButton;
        boolean signOutPressed = e.getSource() == signOutButton;
        boolean seeNotesPressed = e.getSource() == seeNotesButton;
        if (addNotePressed) {
            System.out.println("Add note");
        }
        if (signOutPressed) {
            handleSignOutPressed();
        }
        if (seeNotesPressed) {
            System.out.println("See notes");
        }
    }

    private void handleSignOutPressed() {
        mainFrame.hidePanel(mainFrame.getUserPanel());
        mainFrame.showPanel(mainFrame.getAuthPanel());
        mainFrame.setSize(400, 300);
    }

    public void setUser(User user) {
        this.user = user;
        usernameLabel.setText("Welcome, " + user.getUsername());
    }
}

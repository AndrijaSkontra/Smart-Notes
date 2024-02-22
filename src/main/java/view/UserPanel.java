package view;

import controller.DatabaseServiceSingleton;
import controller.UsersDatabaseConnection;
import model.User;
import model.UserNote;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.List;

public class UserPanel extends JPanel implements ActionListener {

    private JLabel usernameLabel;
    private JTextArea notesTextArea;
    private JScrollPane notesScrollPane;
    private JButton addNoteButton;
    private JButton signOutButton;
    private JButton seeNotesButton;
    private JList<User> subscribedUsersList;
    private JList<User> availableToSubscribeUsersList;
    private JScrollPane subscribedUsersScrollPane;
    private JScrollPane availableToSubscribeUsersScrollPane;
    private JButton subscribeButton;
    private JButton unsubscribeButton;

    private MainFrame mainFrame;

    private DatabaseServiceSingleton databaseServiceSingleton;

    private User user;

    public UserPanel() {
        initializeComponents();
        layoutComponents();
        activateComponents();
    }

    private void initializeComponents() {
        mainFrame = MainFrame.getInstance();
        databaseServiceSingleton = DatabaseServiceSingleton.getInstance(UsersDatabaseConnection.getInstance());
        initiliazeLeftSideComponents();
        initiliazeRightSideComponents();
    }

    private void initiliazeLeftSideComponents() {
        usernameLabel = new JLabel();
        usernameLabel.setFont(new Font("Serif", Font.BOLD, 20));
        notesTextArea = new JTextArea();
        notesTextArea.setLineWrap(true);
        notesTextArea.setWrapStyleWord(true);
        notesScrollPane = new JScrollPane(notesTextArea);
        notesScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        addNoteButton = new JButton("Add note");
        signOutButton = new JButton("Sign out");
        seeNotesButton = new JButton("See notes");
    }

    private void initiliazeRightSideComponents() {
        subscribedUsersList = new JList<>();
        availableToSubscribeUsersList = new JList<>();
        subscribeButton = new JButton("Subscribe");
        unsubscribeButton = new JButton("Unsubscribe");
        availableToSubscribeUsersScrollPane = new JScrollPane(availableToSubscribeUsersList);
        subscribedUsersScrollPane = new JScrollPane(subscribedUsersList);
        availableToSubscribeUsersScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        subscribedUsersScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    }

    private void layoutComponents() {
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new MigLayout());
        leftPanel.add(usernameLabel, "cell 0 0");
        leftPanel.add(notesScrollPane, "cell 0 1, grow, h 65%");
        leftPanel.add(new JSeparator(), "cell 0 2, growx, spanx, pushy, top");
        leftPanel.add(addNoteButton, "cell 0 3, spanx 2, align center");

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new MigLayout());
        rightPanel.add(subscribedUsersScrollPane, "cell 0 0, grow, h 30%, w 100%");
        rightPanel.add(unsubscribeButton, "cell 0 1, top, center, pushy");
        rightPanel.add(availableToSubscribeUsersScrollPane, "cell 0 2, h 30%, w 100%");
        rightPanel.add(subscribeButton, "cell 0 3, align center");

        setLayout(new MigLayout());
        add(seeNotesButton, "cell 0 0, left");
        add(signOutButton, "cell 1 0, right");
        add(new JSeparator(), "cell 0 1, growx, spanx");
        add(leftPanel, "cell 0 2, w 50%, h 100%");
        add(rightPanel, "cell 1 2, w 50%, h 100%");
    }

    private void activateComponents() {
        addNoteButton.addActionListener(this);
        signOutButton.addActionListener(this);
        seeNotesButton.addActionListener(this);
        subscribeButton.addActionListener(this);
        unsubscribeButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean addNotePressed = e.getSource() == addNoteButton;
        boolean signOutPressed = e.getSource() == signOutButton;
        boolean seeNotesPressed = e.getSource() == seeNotesButton;
        boolean subscribePressed = e.getSource() == subscribeButton;
        boolean unsubscribePressed = e.getSource() == unsubscribeButton;
        if (addNotePressed) {
            addNoteToDatabase();
            notesTextArea.setText("");
        }
        if (signOutPressed) {
            handleSignOutPressed();
        }
        if (seeNotesPressed) {
            handleSeeNotesPressed();
        }
        if (subscribePressed) {
            handleSubscribePressed();
        }
        if (unsubscribePressed) {
            handleUnsubscribePressed();
        }
    }

    private void handleUnsubscribePressed() {
        User selectedUser = subscribedUsersList.getSelectedValue();
        if (selectedUser != null) {
            databaseServiceSingleton.unsubscribeUser(user, selectedUser);
            updateAvailableUsersToSubJList();
            updateSubscribedToJList();
        }
    }

    private void handleSubscribePressed() {
        User selectedUser = availableToSubscribeUsersList.getSelectedValue();
        if (selectedUser != null) {
            databaseServiceSingleton.subscribeUser(user, selectedUser);
            updateAvailableUsersToSubJList();
            updateSubscribedToJList();
        }
    }

    private void addNoteToDatabase() {
        UserNote userNote = new UserNote();
        userNote.setContent(notesTextArea.getText());
        userNote.setDateMade(LocalDateTime.now());
        userNote.setUser(user);
        databaseServiceSingleton.addUserNoteToDatabase(userNote);
    }

    private void handleSeeNotesPressed() {
        NotesPanel notesPanel = mainFrame.getNotesPanel();
        notesPanel.setUser(user);
        mainFrame.hidePanel(mainFrame.getUserPanel());
        mainFrame.showPanel(mainFrame.getNotesScrollPane());
    }

    private void handleSignOutPressed() {
        mainFrame.hidePanel(mainFrame.getUserPanel());
        mainFrame.showPanel(mainFrame.getAuthPanel());
        mainFrame.setSize(400, 300);
    }

    // TODO this method does more then one thing!

    public void setUser(User user) {
        this.user = user;
        usernameLabel.setText("Welcome, " + user.getUsername());
        // fillUpSubscribedUsersList();
        updateSubscribedToJList();
        updateAvailableUsersToSubJList();
    }
    private void updateAvailableUsersToSubJList() {
        List<User> availableUsersList = databaseServiceSingleton.getAvailableToSubscribeUsers(user);
        DefaultListModel<User> availableToSubscribeUsersListModel = new DefaultListModel<>();
        for (User user : availableUsersList) {
            availableToSubscribeUsersListModel.addElement(user);
        }
        availableToSubscribeUsersList.setModel(availableToSubscribeUsersListModel);
        availableToSubscribeUsersList.revalidate();
        availableToSubscribeUsersList.repaint();
    }

    private void updateSubscribedToJList() {
        List<User> subscribedToUsers = databaseServiceSingleton.getSubscribedUsers(user);
        DefaultListModel<User> subscribedToUsersListModel = new DefaultListModel<>();
        for (User user : subscribedToUsers) {
            subscribedToUsersListModel.addElement(user);
        }
        subscribedUsersList.setModel(subscribedToUsersListModel);
        subscribedUsersList.revalidate();
        subscribedUsersList.repaint();
    }
}

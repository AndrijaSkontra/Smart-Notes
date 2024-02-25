package view;

import controller.services.DatabaseServiceSingleton;
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

/**
 * Panel responsible for the user's main view.
 */
public class UserPanel extends JPanel implements ActionListener {

    private JLabel usernameLabel;
    private JTextArea notesTextArea;
    private JScrollPane notesScrollPane;
    private JButton addNoteButton;
    private JButton signOutButton;
    private JButton seeNotesButton;
    private JButton notificationsButton;
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
        initiliateUpperButtons();
    }

    private void initiliazeLeftSideComponents() {
        usernameLabel = new JLabel();
        usernameLabel.setFont(new Font("Serif", Font.BOLD, 20));
        notesTextArea = new JTextArea();
        notesTextArea.setLineWrap(true);
        notesTextArea.setWrapStyleWord(true);
        notesScrollPane = new JScrollPane(notesTextArea);
        notesScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        addNoteButton = new JButton("Add blog post");
    }

    private void initiliateUpperButtons() {
        notificationsButton = new JButton("Notifications");
        signOutButton = new JButton("Sign out");
        seeNotesButton = new JButton("See all blogs");
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
        var notifButtonWidth = notificationsButton.getPreferredSize().getWidth();
        add(seeNotesButton, "cell 0 0, spanx 2, center, w " + notifButtonWidth + "px");
        add(notificationsButton, "cell 0 0, gap left 15%, gap right 15%, w " + notifButtonWidth + "px");
        add(signOutButton, "cell 0 0, w 100, w " + notifButtonWidth + "px");
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
        notificationsButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean addNotePressed = e.getSource() == addNoteButton;
        boolean signOutPressed = e.getSource() == signOutButton;
        boolean seeNotesPressed = e.getSource() == seeNotesButton;
        boolean subscribePressed = e.getSource() == subscribeButton;
        boolean unsubscribePressed = e.getSource() == unsubscribeButton;
        boolean notificationsPressed = e.getSource() == notificationsButton;
        if (addNotePressed) {
            handleAddNotePressed();
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
        if (notificationsPressed) {
            handleNotificationsPressed();
        }
    }

    private void handleAddNotePressed() {
        UserNote userNote = new UserNote();
        addNoteToDatabase(userNote);
        notesTextArea.setText("");
        notifyAllSubscribers(userNote);
    }

    private void notifyAllSubscribers(UserNote userNote) {
        List<User> subscribedUsers = databaseServiceSingleton.getAllSubscribedToUser(user);
        for (User user : subscribedUsers) {
            user.setReadAllNotes(false);
            databaseServiceSingleton.updateUser(user);
        }
        for (User user : databaseServiceSingleton.getAllSubscribedToUser(user)) {
            System.out.println(user.getUsername());
            user.getUserNotificationNotes().add(userNote);
            userNote.getUserSet().add(user);
            databaseServiceSingleton.updateUser(user);
        }
    }

    private void handleNotificationsPressed() {
        var notificationFrame = new NotificationsFrame(user);
        user.setReadAllNotes(true);
        databaseServiceSingleton.updateUser(user);
        updateNotificationButton(user);
    }

    private void handleUnsubscribePressed() {
        User selectedUser = subscribedUsersList.getSelectedValue();
        if (selectedUser != null) {
            databaseServiceSingleton.unsubscribeUser(user, selectedUser);
            updateAvailableUsersToSubscribersJList();
            updateSubscribedToJList();
        }
    }

    private void handleSubscribePressed() {
        User selectedUser = availableToSubscribeUsersList.getSelectedValue();
        if (selectedUser != null) {
            databaseServiceSingleton.subscribeUser(user, selectedUser);
            updateAvailableUsersToSubscribersJList();
            updateSubscribedToJList();
        }
    }

    private void addNoteToDatabase(UserNote userNote) {
        userNote.setContent(notesTextArea.getText());
        userNote.setDateMade(LocalDateTime.now());
        userNote.setUser(user);
        databaseServiceSingleton.addUserNoteToDatabase(userNote);
    }

    private void handleSeeNotesPressed() {
        NotesPanel notesPanel = mainFrame.getNotesPanel();
        notesPanel.initializeNotesPanel(user);
        mainFrame.hidePanel(mainFrame.getUserPanel());
        mainFrame.showPanel(mainFrame.getNotesScrollPane());
    }

    private void handleSignOutPressed() {
        mainFrame.hidePanel(mainFrame.getUserPanel());
        mainFrame.showPanel(mainFrame.getAuthPanel());
        mainFrame.setSize(400, 300);
    }

    public void initializeUserInUserPanel(User user) {
        this.user = user;
        usernameLabel.setText("Welcome, " + user.getUsername());
        updateSubscribedToJList();
        updateAvailableUsersToSubscribersJList();
        updateNotificationButton(user);
    }

    private void updateNotificationButton(User user) {
        if (!user.isReadAllNotes()) {
            notificationsButton.setBackground(Color.red);
            notificationsButton.setFont(new Font("Arial", Font.BOLD, 12));
        } else {
            notificationsButton.setBackground(null);
            notificationsButton.setFont(new Font("Arial", Font.PLAIN, 12));
        }
        notificationsButton.revalidate();
        notificationsButton.repaint();
    }

    private void updateAvailableUsersToSubscribersJList() {
        List<User> availableUsersList = databaseServiceSingleton.getAvailableToSubscribeUsers(user);
        DefaultListModel<User> availableToSubscribeUsersListModel = new DefaultListModel<>();
        for (User user : availableUsersList) {
            availableToSubscribeUsersListModel.addElement(user);
        }
        updateJList(availableToSubscribeUsersList, availableToSubscribeUsersListModel);
    }

    private void updateSubscribedToJList() {
        List<User> subscribedToUsers = databaseServiceSingleton.getAllUserSubscriptions(user);
        DefaultListModel<User> subscribedToUsersListModel = new DefaultListModel<>();
        for (User user : subscribedToUsers) {
            subscribedToUsersListModel.addElement(user);
        }
        updateJList(subscribedUsersList, subscribedToUsersListModel);
    }

    private void updateJList(JList<User> userJList, DefaultListModel<User> listModel) {
        userJList.setModel(listModel);
        userJList.revalidate();
        userJList.repaint();
    }
}

package view;

import controller.UsersDatabaseConnection;
import controller.services.DatabaseServiceSingleton;
import model.User;
import model.UserNote;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class NotificationsFrame extends JFrame {

    private List<SingleNotificationPanel> notificationPanels;
    private User user;
    private JPanel mainPanel;
    private JScrollPane mainPanelWrapper;
    private DatabaseServiceSingleton databaseServiceSingleton;

    public NotificationsFrame(User user) {
        this.user = user;
        databaseServiceSingleton = DatabaseServiceSingleton.getInstance(UsersDatabaseConnection.getInstance());
        setupFrame();
        initiliazeComponents();
        layoutComponents();
    }

    private void initiliazeComponents() {
        notificationPanels = fillNotificationPanelsList();
        mainPanel = new JPanel();
        mainPanelWrapper = new JScrollPane(mainPanel);
        mainPanelWrapper.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        mainPanelWrapper.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    }

    private ArrayList<SingleNotificationPanel> fillNotificationPanelsList() {
        Set<UserNote> subscribedUserNotes = user.getUserNotificationNotes();
        var notificationPanels = new ArrayList<SingleNotificationPanel>();
        subscribedUserNotes.forEach(userNote -> notificationPanels.add(new SingleNotificationPanel(userNote)));
        return notificationPanels;
    }

    private void setupFrame() {
        setTitle("Notifications");
        setSize(200, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void layoutComponents() {
        setLayout(new MigLayout());
        add(mainPanelWrapper, "w 100%, h 100%");
        mainPanel.setLayout(new MigLayout());
        notificationPanels = notificationPanels.reversed();
        for (SingleNotificationPanel singleNotificationPanel : notificationPanels) {
            mainPanel.add(singleNotificationPanel, "wrap, w 100%");
        }
    }

    private class SingleNotificationPanel extends JPanel{

        private UserNote userNote;
        private JLabel noteLabel;
        private JLabel dateLabel;

        public SingleNotificationPanel(UserNote userNote) {
            this.userNote = userNote;
            setBorder(BorderFactory.createBevelBorder(1, getBackground(), getBackground().darker()));

            noteLabel = new JLabel("blog post by: " + userNote.getUser().getUsername());
            dateLabel = new JLabel(userNote.getDateMade().toString().substring(0, 16).replaceAll("T", " "));

            setLayout(new MigLayout());
            add(noteLabel, "wrap");
            add(dateLabel);
        }
    }
}

package view;

import controller.services.DatabaseServiceSingleton;
import controller.UsersDatabaseConnection;
import model.User;
import model.UserNote;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class NotesPanel extends JPanel implements ActionListener{

    private ArrayList<SingleNotePanel> singleNotePanels;
    private User user;
    private DatabaseServiceSingleton databaseServiceSingleton;
    private JButton backToUserPanelButton;
    private MainFrame mainFrame;

    private ArrayList<UserNote> listOfUserNotes;
    private ArrayList<UserNote> listOfSubscribedUserNotes;

    public void initializeNotesPanel(User user) {
        this.user = user;
        mainFrame = MainFrame.getInstance();
        databaseServiceSingleton = DatabaseServiceSingleton.getInstance(UsersDatabaseConnection.getInstance());
        initializeComponents();
        layoutComponents();
        activateComponents();
    }

    private void initializeComponents() {
        singleNotePanels = new ArrayList<>();
        listOfUserNotes = databaseServiceSingleton.getAListOfUserNotes(user);
        listOfUserNotes.forEach(userNote -> singleNotePanels.add(new SingleNotePanel(userNote, true)));
        listOfSubscribedUserNotes = databaseServiceSingleton.getAListOfSubscribedUserNotes(user);
        listOfSubscribedUserNotes.forEach(userNote -> singleNotePanels.add(new SingleNotePanel(userNote, false)));
        backToUserPanelButton = new JButton("Back");
    }

    private void layoutComponents() {
        setLayout(new MigLayout());
        for (SingleNotePanel singleNotePanel : singleNotePanels) {
            add(singleNotePanel, "wrap, growx");
        }
        add(backToUserPanelButton, "align left");
    }

    private void activateComponents() {
        backToUserPanelButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean backPressed = e.getSource() == backToUserPanelButton;
        if (backPressed) {
            handleBackPressed();
        }
    }

    private void handleBackPressed() {
        for (SingleNotePanel singleNotePanel : singleNotePanels) {
            remove(singleNotePanel);
        }
        remove(backToUserPanelButton);
        mainFrame.hidePanel(mainFrame.getNotesScrollPane());
        mainFrame.showPanel(mainFrame.getUserPanel());
    }

    private void updateLayoutNotesPanel() {
        singleNotePanels.clear();
        remove(backToUserPanelButton);

        listOfUserNotes = databaseServiceSingleton.getAListOfUserNotes(user);
        listOfUserNotes.forEach(userNote -> singleNotePanels.add(new SingleNotePanel(userNote, true)));
        listOfSubscribedUserNotes = databaseServiceSingleton.getAListOfSubscribedUserNotes(user);
        listOfSubscribedUserNotes.forEach(userNote -> singleNotePanels.add(new SingleNotePanel(userNote, false)));
        layoutComponents();
        repaint();
        revalidate();
    }

    private class SingleNotePanel extends JPanel implements ActionListener {

        private UserNote userNote;
        private JTextArea noteTextArea;
        private JScrollPane noteScrollPane;
        private JButton deleteButton;
        private final boolean isDeleteButtonShown;

        public SingleNotePanel(UserNote userNote, boolean isDeleteButtonShown) {
            this.isDeleteButtonShown = isDeleteButtonShown;
            initializeComponents(userNote);
            layoutComponents();
            activateComponents();
            setUpBorder(userNote);
        }

        private void setUpBorder(UserNote userNote) {
            String current_time = userNote.getDateMade().toString();
            current_time = current_time.replaceAll("T", " ");
            current_time = current_time.substring(0, current_time.length() - 4);
            setBorder(BorderFactory.createTitledBorder(current_time + " - " + userNote.getUser().getUsername()));
        }

        private void initializeComponents(UserNote userNote) {
            this.userNote = userNote;
            noteTextArea = new JTextArea(userNote.getContent());
            setupNoteTextArea();
            noteScrollPane = new JScrollPane(noteTextArea);
            noteScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            deleteButton = new JButton("Delete");
        }

        private void setupNoteTextArea() {
            noteTextArea.setLineWrap(true);
            noteTextArea.setWrapStyleWord(true);
            noteTextArea.setColumns(40);
            noteTextArea.setRows(7);
            noteTextArea.setEditable(false);
        }

        private void layoutComponents() {
            setLayout(new MigLayout("", "[grow][grow]", "[grow][grow]"));
            add(noteScrollPane, "cell 0 0, grow, spanx 2, align center");
            if (isDeleteButtonShown) {
                add(deleteButton, "cell 0 1, align left");
            }
        }

        private void activateComponents() {
            deleteButton.addActionListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            boolean deletePressed = e.getSource() == deleteButton;
            if (deletePressed) {
                handleDeletePressed();
            }
        }

        private void handleDeletePressed() {
            databaseServiceSingleton.deleteUserNoteFromDatabase(userNote);
            JOptionPane.showMessageDialog(this, "Note deleted!");
            updateLayoutNotesPanel();
        }

    }
}

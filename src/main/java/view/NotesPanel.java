package view;

import controller.DatabaseServiceSingleton;
import controller.UsersDatabaseConnection;
import lombok.Setter;
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

    public NotesPanel() {
    }

    public void setUser(User user) {
        this.user = user;
        mainFrame = MainFrame.getInstance();
        databaseServiceSingleton = DatabaseServiceSingleton.getInstance(UsersDatabaseConnection.getInstance());
        initializeComponents();
        layoutComponents();
        activateComponents();
    }

    private void initializeComponents() {
        ArrayList<UserNote> listOfUserNotes = databaseServiceSingleton.getAListOfUserNotes(user);
        singleNotePanels = new ArrayList<>();
        for (UserNote userNote : listOfUserNotes) {
            singleNotePanels.add(new SingleNotePanel(userNote));
        }
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
        mainFrame.hidePanel(mainFrame.getNotesScrollPane());
        mainFrame.showPanel(mainFrame.getUserPanel());
    }

    private class SingleNotePanel extends JPanel implements ActionListener {

        private UserNote userNote;
        private JTextArea noteTextArea;
        private JScrollPane noteScrollPane;
        private JButton deleteButton;


        public SingleNotePanel(UserNote userNote) {
            initializeComponents(userNote);
            layoutComponents();
            activateComponents();
            setBorder(BorderFactory.createTitledBorder(userNote.getDateMade().toString()));
        }

        private void initializeComponents(UserNote userNote) {
            this.userNote = userNote;
            noteTextArea = new JTextArea(userNote.getContent());
            noteTextArea.setLineWrap(true);
            noteTextArea.setWrapStyleWord(true);
            noteTextArea.setColumns(40);
            noteTextArea.setRows(7);
            noteTextArea.setEditable(false);
            noteScrollPane = new JScrollPane(noteTextArea);
            noteScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            deleteButton = new JButton("Delete");
        }

        private void layoutComponents() {
            setLayout(new MigLayout("", "[grow][grow]", "[grow][grow]"));
            add(noteScrollPane, "cell 0 0, grow, spanx 2, align center");
            add(deleteButton, "cell 0 1, align left");
        }

        private void activateComponents() {
            deleteButton.addActionListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            boolean deletePressed = e.getSource() == deleteButton;
            if (deletePressed) {
                System.out.println("Delete pressed");
            }
        }
    }
}

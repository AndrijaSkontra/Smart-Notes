package view;

import lombok.Getter;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

@Getter
public class MainFrame extends JFrame {

    private static volatile MainFrame instance;
    private AuthPanel authPanel;
    private LoginPanel loginPanel;
    private RegisterPanel registerPanel;
    private UserPanel userPanel;
    private NotesPanel notesPanel;

    private MainFrame() {
        setTitle("Simple notes");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setLayout(new MigLayout());
    }

    public void initFrameComponents() {
        authPanel = new AuthPanel();
        loginPanel = new LoginPanel();
        registerPanel = new RegisterPanel();
        userPanel = new UserPanel();
        notesPanel = new NotesPanel();
        showPanel(authPanel);
    }

    public static MainFrame getInstance() {
        if (instance == null) {
            synchronized (MainFrame.class) {
                if (instance == null) {
                    instance = new MainFrame();
                }
            }
        }
        return instance;
    }

    public void showPanel(JPanel panel) {
        panel.setVisible(true);
        add(panel, "w 100%, h 100%, hidemode 0");
    }

    public void showPanel(JScrollPane notesScrollPane) {
        notesScrollPane.setVisible(true);
        add(notesScrollPane, "w 100%, h 100%, hidemode 0");
    }

    public void hidePanel(JPanel panel) {
        panel.setVisible(false);
        add(panel, "hidemode 3");
    }

    public void hidePanel(JScrollPane panel) {
        panel.setVisible(false);
        add(panel, "hidemode 3");
    }
}

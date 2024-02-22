package view;

import model.User;

import javax.swing.*;

public class UserPanelTest {

//    @Test
//    public void testUserPanelCreation() {
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                MainFrame mainFrame = MainFrame.getInstance();
//                UserPanel userPanel = new UserPanel();
//                userPanel.setUser(createUser());
//                mainFrame.showPanel(userPanel);
//            }
//        });
//    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainFrame mainFrame = MainFrame.getInstance();
                mainFrame.setSize(600, 500);
                mainFrame.setLocationRelativeTo(null);
                UserPanel userPanel = new UserPanel();
                userPanel.initializeUserInUserPanel(createUser());
                mainFrame.showPanel(userPanel);
            }
        });
    }

    private static User createUser() {
        User user = new User();
        user.setUsername("Andrija");
        user.setPassword("Kokafaca");
        return user;
    }
}

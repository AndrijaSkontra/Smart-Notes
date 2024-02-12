import view.MainFrame;

import javax.swing.*;

public class RunApp {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
        });
    }
}

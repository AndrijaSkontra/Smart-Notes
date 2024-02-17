import com.formdev.flatlaf.themes.FlatMacLightLaf;
import view.MainFrame;

import javax.swing.*;

public class RunApp {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            try {
                UIManager.setLookAndFeel(new FlatMacLightLaf());
            } catch (UnsupportedLookAndFeelException e) {
                throw new RuntimeException(e);
            }
            var mainFrame = MainFrame.getInstance();
            mainFrame.initFrameComponents();
        });
    }
}

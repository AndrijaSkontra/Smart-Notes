package view;

import javax.swing.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        setupFrame();

    }

    private void setupFrame() {
        setTitle("Simple notes");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
}

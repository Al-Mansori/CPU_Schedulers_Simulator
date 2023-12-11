package GUI;

import javax.swing.*;

public class MainWindow {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("CPU Scheduler Simulator");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 400);

            JPanel inputPanel = new InputPanel();
            JPanel outputPanel = new OutputPanel();

            JTabbedPane tabbedPane = new JTabbedPane();
            tabbedPane.addTab("Input", inputPanel);
            tabbedPane.addTab("Output", outputPanel);

            frame.add(tabbedPane);
            frame.setVisible(true);
        });
    }
}

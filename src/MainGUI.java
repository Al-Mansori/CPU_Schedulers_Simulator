import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("CPU Scheduler Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(600, 400));

        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel sjfPanel = new JPanel();
        JPanel srtfPanel = new JPanel();
        JPanel priorityPanel = new JPanel();
        JPanel agPanel = new JPanel();

        // Add components to each panel as needed for displaying output

        tabbedPane.addTab("SJF", sjfPanel);
        tabbedPane.addTab("SRTF", srtfPanel);
        tabbedPane.addTab("Priority", priorityPanel);
        tabbedPane.addTab("AG", agPanel);

        frame.add(tabbedPane);
        frame.pack();
        frame.setVisible(true);
    }
}

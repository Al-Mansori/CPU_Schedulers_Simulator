package GUI;

import javax.swing.*;

public class OutputPanel extends JPanel {
    public OutputPanel() {
        // Display output components (tables, labels, etc.)
        // Example:
        JTextArea outputArea = new JTextArea();
        outputArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        // Add scrollPane to this panel
        add(scrollPane);
    }
}

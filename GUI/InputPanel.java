package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InputPanel extends JPanel {
    public InputPanel() {
        setLayout(new BorderLayout());

        // Add input components (text fields, buttons, etc.)
        // Example:
        JPanel inputFields = new JPanel(new GridLayout(5, 2));
        JTextField processNameField = new JTextField();
        JTextField processColorField = new JTextField();
        JTextField arrivalTimeField = new JTextField();
        JTextField burstTimeField = new JTextField();
        JTextField priorityField = new JTextField();

        inputFields.add(new JLabel("Process Name:"));
        inputFields.add(processNameField);
        inputFields.add(new JLabel("Process Color:"));
        inputFields.add(processColorField);
        inputFields.add(new JLabel("Arrival Time:"));
        inputFields.add(arrivalTimeField);
        inputFields.add(new JLabel("Burst Time:"));
        inputFields.add(burstTimeField);
        inputFields.add(new JLabel("Priority:"));
        inputFields.add(priorityField);

        JButton addProcessButton = new JButton("Add Process");
        addProcessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get data from input fields and create Process object
                // Add the process to the scheduler
            }
        });

        // Add inputFields and addProcessButton to this panel
        // Add the necessary layout to arrange components
        // Example:
        add(inputFields, BorderLayout.CENTER);
        add(addProcessButton, BorderLayout.SOUTH);
    }
}

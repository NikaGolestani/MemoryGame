package MemoryGame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SelectLevel extends JDialog {

    private static JComboBox<String> levelComboBox;
    private static String selectedLevel = "Easy";

    // Constructor
    public SelectLevel(JFrame parent) {
        super(parent, "Select Level", true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel label = new JLabel("Select Level:");
        levelComboBox = new JComboBox<>(new String[]{"Easy", "Medium", "Hard"});
        levelComboBox.setSelectedItem(selectedLevel);

        JPanel selectionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        selectionPanel.add(label);
        selectionPanel.add(levelComboBox);

        panel.add(selectionPanel, BorderLayout.CENTER);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
                selectedLevel = (String) levelComboBox.getSelectedItem();
                dispose();
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(okButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);
        pack();
        setLocationRelativeTo(parent);
    }

    // Get the selected level
    public static int getSelectedLevel() {
        switch (selectedLevel) {
            case "Easy":
                return 1;
            case "Medium":
                return 2;
            case "Hard":
                return 3;
            default:
                return 1;
        }
    }
}

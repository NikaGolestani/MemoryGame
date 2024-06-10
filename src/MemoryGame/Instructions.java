package MemoryGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

class Instructions extends JDialog {

    public Instructions(JFrame parent, String txt,String header, Color color) {
        super(parent, header, true);
        JPanel panel = new JPanel(new BorderLayout());
        if(color!=null) {
			panel.setBackground(color);
		}
        JTextArea textArea = new JTextArea();
        textArea.setText(txt);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setOpaque(false);

        JPanel textPanel = new JPanel(new BorderLayout());
        textPanel.setOpaque(false);
        textPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 0, 0));
        textPanel.add(textArea, BorderLayout.CENTER);
        Font boldFont = textArea.getFont().deriveFont(Font.BOLD);
        textArea.setFont(boldFont);

        JScrollPane scrollPane = new JScrollPane(textPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);

        panel.add(scrollPane, BorderLayout.CENTER);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(okButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        buttonPanel.setBackground(null);

        add(panel);
        setSize(500, 200);
        setLocationRelativeTo(parent);
        setVisible(true);
    }
}
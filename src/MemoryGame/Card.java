package MemoryGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Card {
    private int value;
    private ImageIcon hiddenImage;
    private ImageIcon revealedImage;
    private boolean hidden;
    private JLabel label; // Store reference to the label
    private Row row; // Store reference to the row
    private boolean lock = false;

    // Constructor
    public Card(int value, String imagePath, String hiddenImagePath, Row row) {
        this.value = value;
        this.revealedImage = new ImageIcon(imagePath);
        this.hiddenImage = new ImageIcon(hiddenImagePath);
        this.hidden = true;
        this.row = row;
        initializeLabel();
    }

    // Method to toggle the hidden state of the card
    public void toggleHiddenState() {
        if (!lock) {
            hidden = !hidden;
            repaint(); // Repaint the label to reflect changes
        }
    }

    // Method to initialize the label
    private void initializeLabel() {
        label = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2 = (Graphics2D) g;
                int labelWidth = getWidth();
                int labelHeight = getHeight();

                // Draw the outer rounded rectangle with a darker color
                g2.setColor(row.getDark());
                g2.fillRoundRect(0, 0, labelWidth, labelHeight, 20, 20);

                // Draw the inner rounded rectangle with a lighter color, slightly inset
                g2.setColor(row.getLight());
                g2.fillRoundRect(3, 3, labelWidth - 6, labelHeight - 6, 20, 20);

                // Draw the image centered
                ImageIcon icon = hidden ? hiddenImage : revealedImage;
                int x = (labelWidth - 100) / 2;
                int y = (labelHeight - 100) / 2;
                g2.drawImage(icon.getImage(), x, y, 100, 100, this);
            }
        };

        // Set the preferred size of the label
        label.setPreferredSize(new Dimension(100, 100));

        // Set background color to transparent
        label.setBackground(new Color(0, 0, 0, 0));

        // Ensure the label is not opaque to allow custom painting
        label.setOpaque(false);

        // Add a mouse listener for click events
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Check if the label is not locked and is currently hidden
                if (!lock && hidden) {
                    // Notify the row of the click event and pass the clicked card reference
                    row.handleClick(Card.this);
                    // Disable the label to prevent further clicks during the delay
                    label.setEnabled(false);
                    // Create a timer to re-enable the label after 200 milliseconds
                    Timer timer = new Timer(200, event -> label.setEnabled(true));
                    timer.setRepeats(false);
                    timer.start();
                }
            }
        });
    }

    // Method to display the card image at a specific position
    public void display(Container container) {
        JPanel cardPanel = new JPanel(new BorderLayout());
        cardPanel.add(label, BorderLayout.CENTER);
        cardPanel.setPreferredSize(new Dimension(100, 100));
        cardPanel.setBackground(null);
        container.add(cardPanel);
    }

    // Method to repaint the card
    public void repaint() {
        if (label != null) {
            label.repaint();
        }
    }

    // Getters and setters
    public boolean getLock() {
        return lock;
    }

    public void setLock(boolean lock) {
        this.lock = lock;
    }

    public int getValue() {
        return value;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
        repaint(); // Repaint the label to reflect changes
    }
}

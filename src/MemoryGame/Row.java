package MemoryGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.ListIterator;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Row {
    private ArrayList<Card> row = new ArrayList<>();
    private int level;
    private JFrame frame;
    private Card revealedCard;
    private int miss;
    private JLabel pointLabel;
    private JLabel gameOverLabel;
    private Game caller;
    private boolean started;
    private JPanel cardsPanel;
    Thread shuffleThread = new ShuffleThread(row);

    // Constructor
    public Row(int level, JFrame frame, Game game) {
        started = false;
        this.caller = game;
        this.level = level;
        this.frame = frame;
        calculateMiss();
        createCards();
        initializePointLabel();
        initializeGameOverLabel();
        started = true;
    }

    // Calculate the number of misses based on the level
    private void calculateMiss() {
        if (level == 1) {
            miss = 15;
        } else if (level == 2) {
            miss = 12;
        } else {
            miss = 10;
        }
    }

    // Create the cards for the row
    private void createCards() {
        // Generate card pairs
        for (int i = 1; i <= 8; i++) {
            String imagePath = String.format("src/Java Project Assets/Level%d/%d.png", level, i);
            String hiddenImagePath = String.format("src/Java Project Assets/Level%d/no_image.png", level);
            Card card1 = new Card(i, imagePath, hiddenImagePath, this);
            Card card2 = new Card(i, imagePath, hiddenImagePath, this);
            row.add(card1);
            row.add(card2);
        }

        // Start the thread to shuffle the cards
        shuffleThread.start();

        // Show cards temporarily before hiding them
        for (Card card : row) {
            card.setHidden(false);
        }

        frame.repaint(); // Repaint to show changes
    }

    // Display the row of cards
    public void display() {
        cardsPanel = new JPanel(new GridLayout(4, 4, 30, 30)); // Grid layout for cards
        cardsPanel.setBackground(null); // Transparent background

        // Clear existing cards
        cardsPanel.removeAll();

        // Add cards to the panel
        for (Card card : row) {
            card.display(cardsPanel);
        }

        // Add components to the frame
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(pointLabel, BorderLayout.NORTH);
        frame.getContentPane().add(cardsPanel, BorderLayout.CENTER);
        frame.pack(); // Adjust size
        frame.setSize(700, 700); // Set size
        frame.setLocationRelativeTo(null); // Center on screen
        frame.setVisible(true); // Show
    }

    // Handle click events on cards
    public void handleClick(Card clickedCard) {
        if (!clickedCard.getLock() && miss != 0) {
            clickedCard.toggleHiddenState();
            if (revealedCard == null) {
                revealedCard = clickedCard;
            } else {
                if (clickedCard.getValue() == revealedCard.getValue() && clickedCard != revealedCard) {
                    clickedCard.setLock(true);
                    revealedCard.setLock(true);
                    caller.incPoint();
                    revealedCard = null;
                } else {
                    new java.util.Timer().schedule(new java.util.TimerTask() {
                        @Override
                        public void run() {
                            caller.decPoint();
                            miss--;
                            clickedCard.toggleHiddenState();
                            revealedCard.toggleHiddenState();
                            if (level == 3) {
								shuffleThread.run();
							}
                            refreshCards();
                            revealedCard = null;
                        }
                    }, 500);
                }
            }
        }

        refreshCards();
        updateGameOverLabelVisibility();
        if (checkWin()) {
            caller.updateGame();
        }
    }

    // Refresh the cards display
    public void refreshCards() {
        cardsPanel.removeAll(); // Clear existing cards

        // Add each card to the panel
        for (Card card : row) {
            card.display(cardsPanel);
        }

        // Add the panel to the frame
        frame.getContentPane().add(cardsPanel, BorderLayout.CENTER);

        // Revalidate and repaint to apply changes
        frame.revalidate();
        frame.repaint();

        // Update the point label
        updatePointLabel();
    }

    // Initialize the point label
    private void initializePointLabel() {
        pointLabel = new JLabel("Points: " + caller.getPoint() + "            " + "Left: " + miss);
        pointLabel.setHorizontalAlignment(SwingConstants.CENTER);
        pointLabel.setBounds(0, 0, frame.getWidth(), 35);
        pointLabel.setBackground(caller.getLighterColorForLevel());
        pointLabel.setOpaque(true);
        pointLabel.setForeground(Color.WHITE);
        frame.add(pointLabel);
    }

    // Update the point label
    private void updatePointLabel() {
        pointLabel.setText("Points: " + caller.getPoint() + "            " + "Left: " + miss);
    }

    // Initialize the game over label
    private void initializeGameOverLabel() {
        gameOverLabel = new JLabel("<html><center>GAMEOVER<br>Point : " + caller.getPoint() + "</center></html>");
        gameOverLabel.setForeground(Color.RED);
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 75));
        gameOverLabel.setBounds(0, 0, frame.getWidth(), frame.getHeight() / 2);
        gameOverLabel.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(gameOverLabel);
        gameOverLabel.setVisible(false); // Initially invisible
    }

    // Update visibility of game over label
    private void updateGameOverLabelVisibility() {
        gameOverLabel.setVisible(miss == 0);
    }

    // Check if all cards are matched
    public boolean checkWin() {
        if (!started) {
			return false;
		}
        ListIterator<Card> iter = row.listIterator();
        while (iter.hasNext()) {
            Card card = iter.next();
            if (!card.getLock()) {
				return false;
			}
        }
        return true;
    }

    // Get lighter color for the level
    public Color getLight() {
        return caller.getLighterColorForLevel().brighter();
    }

    // Get darker color for the level
    public Color getDark() {
        return caller.getColorForLevel().darker();
    }

	public void restart() {
		for (Card card : row) {

            card.setLock(false);
        }
		shuffleThread.run();
		refreshCards();
	}
}

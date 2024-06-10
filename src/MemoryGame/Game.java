package MemoryGame;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

public class Game {
    private JFrame frame;
    private int level;
    private Row row;
    private int point;
    private GameMenu parent;

    public Game(int level, JFrame frame, GameMenu gameMenu) {
        this.level = level;
        this.frame = frame;
        this.parent = gameMenu;
        point = 0;
        initialize();
    }

    // Initialize the game
    private void initialize() {
        setupFrame();
        createMenuBar();
        startNewGame();
        frame.setVisible(true);
    }

    // Setup frame properties
    private void setupFrame() {
        frame.setTitle("Memory Game");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(getColorForLevel());
    }

    // Start a new game
    private void startNewGame() {
        clearFrame();
        row = new Row(level, frame, this);
        row.display();
    }

    // Clear the frame
    private void clearFrame() {
        frame.getContentPane().removeAll();
        setupFrame();
    }

    // Update the game state
    void updateGame() {
        if (row.checkWin()) {
            level++;
            if (level > 3) {
                addToBoard();
                level = 1;
                point = 0;
            }
            clearFrame();
            startNewGame();
        }
    }

    // Add score to the leaderboard
    private void addToBoard() {
        String name;
        do {
            name = JOptionPane.showInputDialog(null, "Enter your name:", "Name Input", JOptionPane.QUESTION_MESSAGE);
            if (name == null) {
                int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to discard your score?", "Confirm Discard", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    return;
                }
            } else if (name.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Name cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                HighScore.append(name, point);
                JOptionPane.showMessageDialog(null, "Score saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                break;
            }
        } while (true);
    }

    // Create the menu bar
    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu gameMenu = new JMenu("Game");
        JMenuItem restartItem = new JMenuItem("Restart");
        JMenuItem highScoreItem = new JMenuItem("HighScore");

        restartItem.addActionListener(e -> {setPoint(0);row.restart();});
        highScoreItem.addActionListener(e -> HighScore.displayHighScores(getLighterColorForLevel()));

        gameMenu.add(restartItem);
        gameMenu.add(highScoreItem);
        menuBar.add(gameMenu);

        JMenu aboutMenu = new JMenu("About");
        JMenuItem aboutDeveloperItem = new JMenuItem("About The Developer");
        JMenuItem aboutGameItem = new JMenuItem("About The Game");

        aboutDeveloperItem.addActionListener(e -> new Instructions(frame, "Nika Golestani\nStudent No", "About Developer", getLighterColorForLevel()));
        aboutGameItem.addActionListener(e -> new Instructions(frame, "Term Project of CSE212\nSpring 2024", "About Project", getLighterColorForLevel()));

        aboutMenu.add(aboutDeveloperItem);
        aboutMenu.add(aboutGameItem);
        menuBar.add(aboutMenu);

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> {
            frame.setVisible(false);
            parent.comeback();
        });
        menuBar.add(exitItem);

        frame.setJMenuBar(menuBar);
    }

    private void setPoint(int i) {
		point = i;
		
	}

	// Get current player point
    public int getPoint() {
        return point;
    }

    // Increase player point
    public void incPoint() {
        point += (level == 1) ? 5 : (level == 2) ? 4 : 3;
    }

    // Decrease player point
    public void decPoint() {
        point -= (level == 1) ? 1 : (level == 2) ? 2 : 3;
    }

    // Get color for current level
    public Color getColorForLevel() {
        switch (this.level) {
            case 1:
                return new Color(0x0053B9); // Level 1 color (hex: #0053B9)
            case 2:
                return new Color(0x7500B9); // Level 2 color (hex: #7500B9)
            case 3:
                return new Color(0xB90000); // Level 3 color (hex: #B90000)
            default:
                return Color.BLACK;
        }
    }

    // Get lighter color for current level
    public Color getLighterColorForLevel() {
        switch (level) {
            case 1:
                return new Color(0x80a7ea);
            case 2:
                return new Color(0xa971ea);
            case 3:
                return new Color(0xea7b71);
            default:
                return Color.WHITE;
        }
    }
}


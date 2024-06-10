package MemoryGame;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class GameMenu extends JFrame {
    private Image backgroundImage;
    private JFrame frame;
    private JPanel panel;

    public GameMenu() {
        initializeUI();
        loadBackgroundImage();
        createLayout();
        createButtons();
    }

    // Method to initialize the UI of the frame
    private void initializeUI() {
        setTitle("Game Menu");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 800);
    }

    // Method to load the background image
    private void loadBackgroundImage() {
        try {
            backgroundImage = Toolkit.getDefaultToolkit().getImage("src/Java Project Assets/background.jpg");
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception gracefully
        }
    }

    // Method to create the layout of the frame
    private void createLayout() {
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        setVisible(true);
    }

    // Method to create buttons
    private void createButtons() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        panel.setLayout(new BorderLayout());

        JLabel headerLabel = new JLabel("Memory Game");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        panel.add(headerLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        addButton(buttonPanel, "Start", this::handleStartButtonClick);
        addButton(buttonPanel, "Select Level", this::handleLevelButtonClick);
        addButton(buttonPanel, "Instructions", this::handleInstructionsButtonClick);
        addButton(buttonPanel, "Exit", this::handleExitButtonClick);

        panel.add(buttonPanel, BorderLayout.CENTER);

        add(panel,BorderLayout.CENTER);
        setVisible(true);
    }

    // Method to add buttons to the panel
    private void addButton(Container container, String text, ActionListener listener) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(300, 50));
        button.setMaximumSize(button.getPreferredSize());
        button.setMinimumSize(button.getPreferredSize());
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(listener);
        container.add(Box.createVerticalStrut(30));
        container.add(button);
    }

    // Method to handle the start button click
    private void handleStartButtonClick(ActionEvent e) {
        System.out.println("Start button pressed");
        // Add your logic here for handling the start button click
        frame = new JFrame("Memory Game");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setLayout(new BorderLayout());
        Game game = new Game(SelectLevel.getSelectedLevel(), frame, this);
        frame.setVisible(true);
        this.setVisible(false);
    }

    // Method to handle the select level button click
    private void handleLevelButtonClick(ActionEvent e) {
        System.out.println("Select Level button pressed");
        SelectLevel selectLevelDialog = new SelectLevel(this);
        selectLevelDialog.setVisible(true); // Make the dialog visible
    }

    // Method to handle the instructions button click
    private void handleInstructionsButtonClick(ActionEvent e) {
        System.out.println("Instructions button pressed");
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource());
        new Instructions(parentFrame, "Instructions:\n1. Click Start to begin the game.\n2. Select a level to play.\n3. Enjoy the Memory Game!", "Instructions", null);
    }

    // Method to handle the exit button click
    private void handleExitButtonClick(ActionEvent e) {
        System.out.println("Exit button pressed");
        System.exit(0);
    }

    // Main method to start the program
    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameMenu::new);
    }

    // Method to come back to the menu from the game
    public void comeback() {
        frame.setVisible(false);
        setVisible(true);
    }

    // Method to restart the game
    public void restart() {
        Game game = new Game(SelectLevel.getSelectedLevel(), frame, this);
        frame.setVisible(true);
        this.setVisible(false);
    }
}

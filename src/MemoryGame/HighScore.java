package MemoryGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class HighScore {
    // Path to the CSV file
    private static final String FILE_PATH = "src/data/highscores.csv";

    // Method to read all high scores from the CSV file
    public static List<Score> read() {
        List<Score> highScores = new ArrayList<>();
        File file = new File(FILE_PATH);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            // Read each line from the file
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                // Check if the line format is valid
                if (values.length == 2) {
                    try {
                        String name = values[0];
                        int point = Integer.parseInt(values[1]);
                        // Add score to the list
                        highScores.add(new Score(name, point));
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid score format: " + line);
                    }
                } else {
                    System.err.println("Invalid line format: " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading high scores file: " + e.getMessage());
        }
        return highScores;
    }

    // Method to append a new high score to the CSV file
    public static void append(String name, int score) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            // Write the new score to the file
            bw.write(name + "," + score);
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Error appending high score: " + e.getMessage());
        }
    }

    // Method to display high scores in a dialog
    public static void displayHighScores(Color color) {
        // Read high scores from the file
        List<Score> highScores = read();
        // Sort the scores in descending order
        Collections.sort(highScores);
        // Limit to top 10 scores
        int limit = Math.min(10, highScores.size());

        // Create table model and set up the table
        String[] columnNames = {"Player", "Score"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        for (int i = 0; i < limit; i++) {
            Score score = highScores.get(i);
            tableModel.addRow(new Object[]{score.getName(), score.getPoint()});
        }
        JTable table = new JTable(tableModel);

        // Customize table appearance
        table.setOpaque(false);
        table.setBackground(color.brighter());
        table.setRowHeight(30);
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setForeground(color.darker().darker());
                setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                return c;
            }
        });

        // Customize cell renderer for column headings
        JTableHeader header = table.getTableHeader();
        header.setFont(header.getFont().deriveFont(Font.BOLD, 18f));
        header.setBackground(color.brighter());
        header.setForeground(color.darker());

        // Set alternating row colors for better readability
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (row % 2 == 0) {
                    c.setBackground(color);
                } else {
                    c.setBackground(color.brighter());
                }
                return c;
            }
        });

        // Set up the table in a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(color.darker(), 2));
        scrollPane.setBackground(color.brighter());
        scrollPane.getViewport().setBackground(color.brighter());

        // Customize dialog appearance
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(color.getRed(), color.getGreen(), color.getBlue(), 128));
        panel.add(scrollPane, BorderLayout.CENTER);

        // Customize the JOptionPane background color
        UIManager.put("OptionPane.background", color);
        UIManager.put("Panel.background", color);
        UIManager.put("Button.background", Color.WHITE);

        // Create and display the JOptionPane
        JOptionPane optionPane = new JOptionPane(panel, JOptionPane.PLAIN_MESSAGE);
        JDialog dialog = optionPane.createDialog("High Scores");
        dialog.getContentPane().setBackground(null);
        dialog.setVisible(true);
    }

    // Method to save high scores back to the CSV file
    public static void save(List<Score> highScores) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            // Write high scores to the file
            for (Score score : highScores) {
                bw.write(score.getName() + "," + score.getPoint());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving high scores: " + e.getMessage());
        }
    }
}

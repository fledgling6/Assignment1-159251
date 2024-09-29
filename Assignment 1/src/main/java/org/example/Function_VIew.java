package org.example;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Function_VIew {
    GUI gui;

    public Function_VIew(GUI gui) {
        this.gui = gui;
    }

    public void wordWrapping() {
        gui.wordWrapping = !gui.wordWrapping;  // Toggle the state
        gui.textArea.setLineWrap(gui.wordWrapping);
        gui.textArea.setWrapStyleWord(gui.wordWrapping);
    }

    public void zoomIn() {
        Font currentFont = gui.textArea.getFont();
        gui.textArea.setFont(currentFont.deriveFont(currentFont.getSize() + 2f));
    }

    public void zoomOut() {
        Font currentFont = gui.textArea.getFont();
        gui.textArea.setFont(currentFont.deriveFont(currentFont.getSize() - 2f));
    }

    public void resetZoom() {
        gui.textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));  // Reset to default size
    }

    public void lineNumbers() {
        // Simple toggle for line numbers (you might want to implement a more advanced feature)
        JTextArea lineNumberArea = new JTextArea();
        lineNumberArea.setEditable(false);
        lineNumberArea.setBackground(Color.LIGHT_GRAY);

        // Create a panel to hold both areas
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(lineNumberArea), BorderLayout.WEST);
        panel.add(new JScrollPane(gui.textArea), BorderLayout.CENTER);

        gui.room.setContentPane(panel);
        gui.room.revalidate();
    }

    public void toggleTheme() {
        Color currentBg = gui.textArea.getBackground();
        if (currentBg.equals(Color.WHITE)) {
            gui.textArea.setBackground(Color.DARK_GRAY);
            gui.textArea.setForeground(Color.WHITE);
        } else {
            gui.textArea.setBackground(Color.WHITE);
            gui.textArea.setForeground(Color.BLACK);
        }
    }

    public void fontAndTheme() {
        // Font options
        String[] fontNames = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        String selectedFont = (String) JOptionPane.showInputDialog(gui.room,
                "Select Font",
                "Font Selection",
                JOptionPane.QUESTION_MESSAGE,
                null,
                fontNames,
                fontNames[0]);

        // Font size selection
        String fontSizeStr = JOptionPane.showInputDialog(gui.room, "Enter Font Size:", "Font Size", JOptionPane.PLAIN_MESSAGE);
        int fontSize = 12; // Default size
        try {
            fontSize = Integer.parseInt(fontSizeStr);
        } catch (NumberFormatException e) {
            // Handle invalid input
            JOptionPane.showMessageDialog(gui.room, "Invalid font size. Using default size 12.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Apply selected font
        if (selectedFont != null) {
            gui.textArea.setFont(new Font(selectedFont, Font.PLAIN, fontSize));
        }

        // Theme selection
        String[] themes = {"Light", "Dark"};
        String selectedTheme = (String) JOptionPane.showInputDialog(gui.room,
                "Select Theme",
                "Theme Selection",
                JOptionPane.QUESTION_MESSAGE,
                null,
                themes,
                themes[0]);

        // Apply selected theme
        if ("Dark".equals(selectedTheme)) {
            gui.textArea.setBackground(Color.DARK_GRAY);
            gui.textArea.setForeground(Color.WHITE);
        } else {
            gui.textArea.setBackground(Color.WHITE);
            gui.textArea.setForeground(Color.BLACK);
        }
    }
    public void timeAndDate() {
        // Get the current date and time
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateTime = formatter.format(new Date());

        // Insert the date and time at the beginning of the text area
        String existingText = gui.textArea.getText();
        gui.textArea.setText(currentDateTime + "\n" + existingText); // Prepend current date and time
        gui.textArea.setCaretPosition(0); // Move the caret to the beginning
    }

}

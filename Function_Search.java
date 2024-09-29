package org.example;

import javax.swing.*;

public class Function_Search {
    GUI gui;
    public Function_Search(GUI gui) {this.gui=gui;}

    public void searchText() {
        String searchTerm = JOptionPane.showInputDialog(gui.room, "Enter text to search:", "Search", JOptionPane.QUESTION_MESSAGE);
        if (searchTerm != null && !searchTerm.isEmpty()) {
            String text = gui.textArea.getText();
            int index = text.indexOf(searchTerm);
            if (index >= 0) {
                gui.textArea.select(index, index + searchTerm.length());
                gui.textArea.requestFocus();
            } else {
                JOptionPane.showMessageDialog(gui.room, "Text not found.", "Search", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

}

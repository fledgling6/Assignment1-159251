package org.example;

import javax.swing.*;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

/**
 * This class provides methods to manage text operations such as cut, copy, paste, and select all.
 */
public class Function_Manage {
    private final GUI gui;

    /**
     * Constructor that initializes the GUI reference.
     *
     * @param gui The GUI instance that contains the JTextArea and JFrame.
     */
    public Function_Manage(GUI gui) {
        this.gui = gui;
    }

    /**
     * Cuts the selected text from the JTextArea and places it on the clipboard.
     * If no text is selected, a warning message is shown.
     */
    public void CutText() {
        try {
            // Check if any text is selected
            if (gui.textArea.getSelectedText() == null) {
                JOptionPane.showMessageDialog(gui.room, "No text selected for cutting.", "Cut Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            // Perform the cut operation
            gui.textArea.cut();
        } catch (Exception e) {
            // Show an error message if an exception occurs
            JOptionPane.showMessageDialog(gui.room, "An error occurred while cutting: " + e.getMessage(), "Cut Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Copies the selected text from the JTextArea to the clipboard.
     * If no text is selected, a warning message is shown.
     */
    public void CopyText() {
        try {
            // Check if any text is selected
            if (gui.textArea.getSelectedText() == null) {
                JOptionPane.showMessageDialog(gui.room, "No text selected for copying.", "Copy Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            // Perform the copy operation
            gui.textArea.copy();
        } catch (Exception e) {
            // Show an error message if an exception occurs
            JOptionPane.showMessageDialog(gui.room, "An error occurred while copying: " + e.getMessage(), "Copy Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Pastes the content of the clipboard into the JTextArea at the current caret position.
     * If the clipboard is empty or contains unsupported data, an error message is shown.
     */
    public void PasteText() {
        try {
            // Ensure the JTextArea has focus
            gui.textArea.requestFocusInWindow();

            // Get the system clipboard contents
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            Transferable contents = clipboard.getContents(null);

            // Check if the clipboard has string data
            if (contents != null && contents.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                String text = (String) contents.getTransferData(DataFlavor.stringFlavor);
                System.out.println("Pasting text: " + text);  // Debug information

                // Insert the text at the current caret position
                gui.textArea.insert(text, gui.textArea.getCaretPosition());
            } else {
                // Show an error message if the clipboard is empty or contains unsupported data
                JOptionPane.showMessageDialog(gui.room, "Clipboard is empty or contains unsupported data format.", "Paste Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (UnsupportedFlavorException | IOException e) {
            // Show an error message if an exception occurs
            JOptionPane.showMessageDialog(gui.room, "An error occurred while pasting: " + e.getMessage(), "Paste Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Selects all text in the JTextArea.
     */
    public void SelectAll() {
        // Select all text in the JTextArea
        gui.textArea.selectAll();
    }
}
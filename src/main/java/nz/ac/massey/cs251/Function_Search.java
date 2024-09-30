package nz.ac.massey.cs251;

import nz.ac.massey.cs251.GUI;

import javax.swing.*;
import java.util.logging.Logger;

public class Function_Search {
    // Logger for logging any errors or information
    private static final Logger LOGGER = Logger.getLogger(Function_Search.class.getName());

    // Constants for dialog titles and messages
    private static final String SEARCH_TITLE = "Search";
    private static final String SEARCH_PROMPT = "Enter text to search:";
    private static final String NOT_FOUND_MESSAGE = "Text not found.";

    // Reference to the GUI
    private GUI gui;

    /**
     * Constructor that initializes the GUI reference.
     *
     * @param gui The GUI instance to be used.
     */
    public Function_Search(GUI gui) {
        this.gui = gui;
    }

    /**
     * Initiates the search process by showing an input dialog and searching for the text.
     */
    public void searchText() {
        try {
            // Show the input dialog to get the search term from the user
            String searchTerm = showInputDialog(SEARCH_TITLE, SEARCH_PROMPT);

            // If the search term is not null and not empty, proceed with the search
            if (searchTerm != null && !searchTerm.isEmpty()) {
                searchAndSelectText(searchTerm);
            }
        } catch (Exception e) {
            // Log the error and show an error message to the user
            LOGGER.severe("Error during search: " + e.getMessage());
            JOptionPane.showMessageDialog(gui.room, "An error occurred during the search.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Shows an input dialog to get the search term from the user.
     *
     * @param title  The title of the dialog.
     * @param prompt The prompt message in the dialog.
     * @return The search term entered by the user, or null if the dialog was canceled.
     */
    private String showInputDialog(String title, String prompt) {
        return JOptionPane.showInputDialog(gui.room, prompt, title, JOptionPane.QUESTION_MESSAGE);
    }

    /**
     * Searches for the given search term in the text area and selects it if found.
     *
     * @param searchTerm The term to search for.
     */
    private void searchAndSelectText(String searchTerm) {
        // Get the text from the text area
        String text = gui.textArea.getText();

        // Find the index of the search term in the text
        int index = text.indexOf(searchTerm);

        // If the search term is found, select it and set focus
        if (index >= 0) {
            selectAndFocusText(index, searchTerm.length());
        } else {
            // If the search term is not found, show a message to the user
            showInformationMessage(SEARCH_TITLE, NOT_FOUND_MESSAGE);
        }
    }

    /**
     * Selects and focuses the text in the text area.
     *
     * @param startIndex The starting index of the text to select.
     * @param length     The length of the text to select.
     */
    private void selectAndFocusText(int startIndex, int length) {
        gui.textArea.select(startIndex, startIndex + length);
        gui.textArea.requestFocus();
    }

    /**
     * Shows an information message to the user.
     *
     * @param title   The title of the message dialog.
     * @param message The message to display.
     */
    private void showInformationMessage(String title, String message) {
        JOptionPane.showMessageDialog(gui.room, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
}
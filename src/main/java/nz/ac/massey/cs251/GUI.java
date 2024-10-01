package nz.ac.massey.cs251;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI implements ActionListener {
    // Main window (JFrame) for the application
    JFrame room;

    // Flag to track word wrapping state
    boolean wordWrapping = false;

    // Text area with syntax highlighting
    RSyntaxTextArea textArea;
    RTextScrollPane scrollPane;

    // Menu bar and menus
    JMenuBar menuBar;
    JMenu menuFile, menuSearch, menuView, menuHelp, menuManage;

    // File menu items
    JMenuItem iNew, iNewWindow, iOpen, iSave, iSaveAs, iPrint, iExportAS, iExit;

    // View menu items
    JMenuItem iZoomIn, iZoomOut, iResetZoom, iLineNumbers, iWordWrapping, iToggleTheme, iFontAndTheme, iTimeAndDate;

    // Edit menu items (under Manage)
    JMenuItem iCut, iCopy, iPaste, iSelectAll;

    // Function classes for handling different operations
    Function_File file = new Function_File(this);
    Function_Search search = new Function_Search(this);
    Function_VIew vIew = new Function_VIew(this);
    Function_Manage manage = new Function_Manage(this);

    public static void main(String[] args) {
        // Ensure that the GUI is created on the Event Dispatch Thread
        SwingUtilities.invokeLater(GUI::new);
    }

    public GUI() {
        // Initialize the main window and its components
        createRoom();
        createTestArea();
        createMenubar();
        createMenuBar();
        createFileMenu();
        createVIewMenu();
        createManageMenu();
        // Make the main window visible
        room.setVisible(true);
    }

    private void createRoom() {
        // Create the main window (JFrame) and set its properties
        room = new JFrame("Notepad");
        room.setSize(1000, 800); // Set the initial size of the window
        room.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close the application when the window is closed
        room.setLayout(new BorderLayout()); // Use BorderLayout for the window
    }

    private void createTestArea() {
        // Create the text area with syntax highlighting and a scroll pane
        textArea = new RSyntaxTextArea();
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA); // Default to Java syntax
        scrollPane = new RTextScrollPane(textArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder()); // Remove the border from the scroll pane
        room.add(scrollPane, BorderLayout.CENTER); // Add the scroll pane to the center of the window
    }

    private void applySyntaxHighlighting() {
        // Apply syntax highlighting based on the file extension
        String fileName = file.getFileName();
        if (fileName.endsWith(".java")) {
            textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        } else if (fileName.endsWith(".py")) {
            textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_PYTHON);
        } else if (fileName.endsWith(".js")) {
            textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT);
        } else {
            textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE);
        }
    }

    private void createMenubar() {
        // Create the menu bar and set it as the main window's menu bar
        menuBar = new JMenuBar();
        room.setJMenuBar(menuBar);
    }

    private void createMenuBar() {
        // Create the menu bar and add menus to it
        menuBar = new JMenuBar();
        room.setJMenuBar(menuBar);

        // Create the "File" menu
        menuFile = new JMenu("File");
        menuBar.add(menuFile);

        // Create the "Search" menu
        menuSearch = new JMenu("Search");
        JMenuItem searchItem = new JMenuItem("Search for text ");
        searchItem.addActionListener(this); // Add action listener for the search item
        searchItem.setActionCommand("Search"); // Set the action command
        menuSearch.add(searchItem); // Add the search item to the Search menu
        menuBar.add(menuSearch); // Add the Search menu to the menu bar

        // Create the "View" menu
        menuView = new JMenu("View");
        menuBar.add(menuView);

        // Create the "Manage" menu
        menuManage = new JMenu("Manage");
        menuBar.add(menuManage);

        // Create the "Help" menu
        menuHelp = new JMenu("Help");
        menuBar.add(menuHelp);
    }

    private void createFileMenu() {
        // Create and add items to the "File" menu
        iNew = new JMenuItem("New");
        iNew.addActionListener(this); // Add action listener
        iNew.setActionCommand("New"); // Set the action command
        menuFile.add(iNew);

        iNewWindow = new JMenuItem("New Window");
        iNewWindow.addActionListener(this);
        iNewWindow.setActionCommand("New Window");
        menuFile.add(iNewWindow);

        iOpen = new JMenuItem("Open");
        iOpen.addActionListener(this);
        iOpen.setActionCommand("Open");
        menuFile.add(iOpen);

        iSave = new JMenuItem("Save");
        iSave.addActionListener(this);
        iSave.setActionCommand("Save");
        menuFile.add(iSave);

        iSaveAs = new JMenuItem("Save as");
        iSaveAs.addActionListener(this);
        iSaveAs.setActionCommand("Save as");
        menuFile.add(iSaveAs);

        iPrint = new JMenuItem("Print");
        iPrint.addActionListener(this);
        iPrint.setActionCommand("Print");
        menuFile.add(iPrint);

        iExportAS = new JMenuItem("Export as");
        iExportAS.addActionListener(this);
        iExportAS.setActionCommand("Export as PDF");
        menuFile.add(iExportAS);

        iExit = new JMenuItem("Exit");
        iExit.addActionListener(this);
        iExit.setActionCommand("Exit");
        menuFile.add(iExit);
    }

    private void createVIewMenu() {
        // Create and add items to the "View" menu
        iZoomIn = new JMenuItem("Zoom In");
        iZoomIn.addActionListener(this);
        iZoomIn.setActionCommand("Zoom In");
        menuView.add(iZoomIn);

        iZoomOut = new JMenuItem("Zoom Out");
        iZoomOut.addActionListener(this);
        iZoomOut.setActionCommand("Zoom Out");
        menuView.add(iZoomOut);

        iResetZoom = new JMenuItem("Reset Zoom");
        iResetZoom.addActionListener(this);
        iResetZoom.setActionCommand("Reset Zoom");
        menuView.add(iResetZoom);

        iLineNumbers = new JMenuItem("Line Numbers");
        iLineNumbers.addActionListener(this);
        iLineNumbers.setActionCommand("Line Numbers");
        menuView.add(iLineNumbers);

        iWordWrapping = new JMenuItem("Word Wrapping");
        iWordWrapping.addActionListener(this);
        iWordWrapping.setActionCommand("Word Wrapping");
        menuView.add(iWordWrapping);

        iToggleTheme = new JMenuItem("Toggle Theme");
        iToggleTheme.addActionListener(this);
        iToggleTheme.setActionCommand("Toggle Theme");
        menuView.add(iToggleTheme);

        iFontAndTheme = new JMenuItem("Font and Theme");
        iFontAndTheme.addActionListener(this);
        iFontAndTheme.setActionCommand("Font and Theme");
        menuView.add(iFontAndTheme);

        iTimeAndDate = new JMenuItem("Time and Date");
        iTimeAndDate.addActionListener(this);
        iTimeAndDate.setActionCommand("Time and Date");
        menuView.add(iTimeAndDate);
    }

    private void createManageMenu() {
        // Create and add items to the "Manage" menu
        iCut = new JMenuItem("Cut");
        iCut.addActionListener(this);
        iCut.setActionCommand("Cut");
        menuManage.add(iCut);

        iCopy = new JMenuItem("Copy");
        iCopy.addActionListener(this);
        iCopy.setActionCommand("Copy");
        menuManage.add(iCopy);

        iPaste = new JMenuItem("Paste");
        iPaste.addActionListener(this);
        iPaste.setActionCommand("Paste");
        menuManage.add(iPaste);

        iSelectAll = new JMenuItem("Select All");
        iSelectAll.addActionListener(this);
        iSelectAll.setActionCommand("SelectAll");
        menuManage.add(iSelectAll);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle the action events based on the action command
        String command = e.getActionCommand();
        switch (command) {
            case "New":
                file.newFile(); // Create a new file
                break;
            case "New Window":
                new GUI(); // Open a new window
                break;
            case "Open":
                file.openFile(); // Open an existing file
                break;
            case "Save as":
                file.SaveAsFile(); // Save the file with a new name
                break;
            case "Save":
                file.SaveFile(); // Save the current file
                break;
            case "Print":
                file.printFile(); // Print the current file
                break;
            case "Export as PDF":
                file.exportAsPDF(); // Export the current file as a PDF
                break;
            case "Exit":
                file.Exit(); // Exit the application
                break;
            case "Zoom In":
                vIew.zoomIn(); // Increase the zoom level
                break;
            case "Zoom Out":
                vIew.zoomOut(); // Decrease the zoom level
                break;
            case "Reset Zoom":
                vIew.resetZoom(); // Reset the zoom level to default
                break;
            case "Line Numbers":
                vIew.lineNumbers(); // Toggle line numbers
                break;
            case "Word Wrapping":
                vIew.wordWrapping(); // Toggle word wrapping
                break;
            case "Toggle Theme":
                vIew.toggleTheme(); // Toggle the theme
                break;
            case "Font and Theme":
                vIew.fontAndTheme(); // Change the font and theme
                break;
            case "Time and Date":
                vIew.timeAndDate(); // Insert the current time and date
                break;
            case "Search":
                search.searchText(); // Perform a text search
                break;
            case "Cut":
                manage.CutText(); // Cut the selected text
                break;
            case "Copy":
                manage.CopyText(); // Copy the selected text
                break;
            case "Paste":
                manage.PasteText(); // Paste the copied text
                break;
            case "SelectAll":
                manage.SelectAll(); // Select all text in the text area
                break;
        }
    }
}
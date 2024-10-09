package org.example;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI implements ActionListener {
    JFrame room;

    boolean wordWrapping = false;

    //TEXT AREA
    RSyntaxTextArea textArea;
    RTextScrollPane scrollPane;

    //TOP MENU BAR
    JMenuBar menuBar;

    //FILE MENU
    JMenu menuFile, menuSearch, menuView, menuHelp, menuManage;
    JMenuItem iNew, iNewWindow, iOpen, iSave, iSaveAs, iPrint, iExportAS, iExit;

    //VIew MENu
    JMenuItem iZoomIn, iZoomOut, iResetZoom, iLineNumbers, iWordWrapping, iToggleTheme, iFontAndTheme, iTimeAndDate;

    JMenuItem iCut, iCopy, iPaste, iSelectAll;
    Function_File file = new Function_File(this);

    Function_Search search = new Function_Search(this);
    Function_VIew vIew = new Function_VIew(this);
    Function_Manage manage = new Function_Manage(this);

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GUI::new);
    }

    public GUI() {
        createRoom();
        createTestArea();
        createMenubar();
        createMenuBar();
        createFileMenu();
        createVIewMenu();
        createManageMenu();
        room.setVisible(true);
    }

    private void createRoom() {
        room = new JFrame("Notepad");
        room.setSize(1000, 800);
        room.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        room.setLayout(new BorderLayout());
    }

    private void createTestArea() {
        textArea = new RSyntaxTextArea();
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        scrollPane = new RTextScrollPane(textArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        room.add(scrollPane, BorderLayout.CENTER);


    }
    private void applySyntaxHighlighting() {
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



    public void createMenubar() {
        menuBar = new JMenuBar();
        room.setJMenuBar(menuBar);

    }
    public void createMenuBar() {
        menuBar = new JMenuBar();
        room.setJMenuBar(menuBar);

        menuFile = new JMenu("File");
        menuBar.add(menuFile);

        // Create a Search menu and add it to the menu bar
        menuSearch = new JMenu("Search");  // Change this line
        JMenuItem searchItem = new JMenuItem("Search for text ");
        searchItem.addActionListener(this);
        searchItem.setActionCommand("Search");
        menuSearch.add(searchItem);  // Add search item to the Search menu
        menuBar.add(menuSearch);  // Add Search menu to the menu bar

        menuView = new JMenu("View");
        menuBar.add(menuView);

        menuManage = new JMenu("Manage");
        menuBar.add(menuManage);

        menuHelp = new JMenu("Help");
        menuBar.add(menuHelp);
    }


    public void createFileMenu() {
        iNew= new JMenuItem("New");
        iNew.addActionListener(this);
        iNew.setActionCommand("New");
        menuFile.add(iNew);

        iNewWindow= new JMenuItem("New Window");
        iNewWindow.addActionListener(this);
        iNewWindow.setActionCommand("New Window");
        menuFile.add(iNewWindow);

        iOpen= new JMenuItem("Open");
        iOpen.addActionListener(this);
        iOpen.setActionCommand("Open");
        menuFile.add(iOpen);

        iSave= new JMenuItem("Save");
        iSave.addActionListener(this);
        iSave.setActionCommand("Save");
        menuFile.add(iSave);

        iSaveAs= new JMenuItem("Save as");
        iSaveAs.addActionListener(this);
        iSaveAs.setActionCommand("Save as");
        menuFile.add(iSaveAs);

        iPrint= new JMenuItem("Print");
        iPrint.addActionListener(this);
        iPrint.setActionCommand("Print");
        menuFile.add(iPrint);

        iExportAS= new JMenuItem("Export as");
        iExportAS.addActionListener(this);
        iExportAS.setActionCommand("Export as PDF");
        menuFile.add(iExportAS);

        iExit= new JMenuItem("Exit");
        iExit.addActionListener(this);
        iExit.setActionCommand("Exit");
        menuFile.add(iExit);

    }
    public void createVIewMenu() {
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

    public void createManageMenu() {
        iCut = new JMenuItem("Cut");
        iCut.addActionListener(this);
        iCut.setActionCommand("Cut");
        menuManage.add(iCut);

        iCopy = new JMenuItem("Copy");
        iCopy .addActionListener(this);
        iCopy .setActionCommand("Copy");
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
        String command = e.getActionCommand();
        switch (command){
            case"New":file.newFile();break;
            case "New Window":new GUI();break;
            case "Open":file.openFile();break;
            case "Save as":file.SaveAsFile();break;
            case "Save":file.SaveFile();break;
            case"Print":file.printFile();break;
            case "Export as PDF":file.exportAsPDF();break;
            case "Exit":file.Exit();break;
            case "Zoom In":vIew.zoomIn();break;
            case "Zoom Out":vIew.zoomOut();break;
            case "Reset Zoom":vIew.resetZoom();break;
            case "Line Numbers":vIew.lineNumbers();break;
            case "Word Wrapping":vIew.wordWrapping();break;
            case "Toggle Theme":vIew.toggleTheme();break;
            case"Font and Theme":vIew.fontAndTheme();break;
            case "Time and Date":vIew.timeAndDate();break;
            case "Search":search.searchText();break;
            case "Cut":manage.CutText();break;
            case "Copy":manage.CopyText();break;
            case "Paste":manage.PasteText();break;
            case "SelectAll":manage.SelectAll();break;
        }
    }
}

package org.example;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.*;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.*;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.rtf.RTFEditorKit;
import org.odftoolkit.simple.TextDocument;

public class Function_File {

    // Reference to the GUI
    GUI gui;
    // Variables to store file information
    public String fileName;
    private File currentFile;
    public String fileAddress;

    // Constructor that initializes the GUI reference
    public Function_File(GUI gui) {
        this.gui = gui;
        this.currentFile = null;
    }

    // Method to create a new (empty) file
    public void newFile() {
        gui.textArea.setText("");  // Clear the text area
        gui.room.setTitle("New");  // Set the window title to "New"
        fileName = null;           // Reset the file name
        fileAddress = null;        // Reset the file address
    }

    // Method to open an existing file
    public void openFile() {
        // Create a FileDialog to select a file to open
        FileDialog fd = new FileDialog(gui.room, "Open", FileDialog.LOAD);
        fd.setVisible(true);

        // If a file was selected
        if (fd.getFile() != null) {
            fileName = fd.getFile();         // Get the selected file's name
            fileAddress = fd.getDirectory(); // Get the directory of the selected file
            gui.room.setTitle(fileName);     // Set the window title to the file name

            try {
                // Construct the File object from the file name and address
                File file = new File(fileAddress, fileName);
                // Determine the file extension
                String extension = getFileExtension(file);

                // Open the file based on its extension
                switch (extension.toLowerCase()) {
                    case "rtf":
                        openRTFFile(file);
                        break;
                    case "odt":
                        openODTFile(file);
                        break;
                    default:
                        openTextFile(file);
                        break;
                }
            } catch (IOException e) {
                // Show error message if there is an exception
                JOptionPane.showMessageDialog(gui.room, "Error opening file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                System.out.println("FILE IS NOT OPEN: " + e.getMessage());
            }
        }
    }

    // Private method to open plain text files
    public void openTextFile(File file) throws IOException {
        // Use BufferedReader to read the file line by line
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            gui.textArea.setText("");  // Clear the text area before loading the file
            String line;
            while ((line = br.readLine()) != null) {
                gui.textArea.append(line + "\n");  // Append each line to the text area
            }
        }
    }

    // Private method to open RTF files
    private void openRTFFile(File file) throws IOException {
        // Use RTFEditorKit to parse the RTF document
        RTFEditorKit rtfKit = new RTFEditorKit();
        javax.swing.text.Document doc = rtfKit.createDefaultDocument();
        try (FileInputStream fis = new FileInputStream(file)) {
            rtfKit.read(fis, doc, 0);
            // Set the text area content with the parsed RTF document
            gui.textArea.setText(doc.getText(0, doc.getLength()));
        } catch (BadLocationException e) {
            throw new RuntimeException(e);
        }
    }

    // Private method to open ODT files
    private void openODTFile(File file) throws IOException {
        try {
            // Use TextDocument from ODF Toolkit to load the ODT document
            TextDocument odtDoc = TextDocument.loadDocument(file);
            // Set the text area content with the text content of the ODT document
            gui.textArea.setText(odtDoc.getContentRoot().getTextContent());
        } catch (Exception e) {
            throw new IOException("Error reading ODT document: " + e.getMessage(), e);
        }
    }

    // Helper method to get the file extension
    private String getFileExtension(File file) {
        String name = file.getName();
        int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return "";  // No extension found
        }
        return name.substring(lastIndexOf + 1);
    }

    // Method to get the current file name
    public String getFileName() {
        if (currentFile != null) {
            return currentFile.getName();
        } else {
            return "No file selected";
        }
    }

    // Method to save the current file
    public void SaveFile() {
        if (fileName == null) {
            SaveAsFile();  // If no file name is set, use 'Save As'
        } else {
            try (FileWriter fw = new FileWriter(new File(fileAddress, fileName))) {
                // Write the contents of the text area to the file
                fw.write(gui.textArea.getText());
                gui.room.setTitle(fileName);  // Update the window title
            } catch (IOException e) {
                // Show error message if there is an exception
                JOptionPane.showMessageDialog(gui.room, "Error saving file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                System.out.println("SAVE ERROR: " + e.getMessage());
            }
        }
    }

    // Method to save the current file with a new name or location
    public void SaveAsFile() {
        // Create a FileDialog to select a location and name for the file
        FileDialog fd = new FileDialog(gui.room, "Save", FileDialog.SAVE);
        fd.setVisible(true);

        if (fd.getFile() != null) {
            fileName = fd.getFile();         // Get the selected file name
            fileAddress = fd.getDirectory(); // Get the directory of the selected file
            gui.room.setTitle(fileName);     // Update the window title

            try (FileWriter fw = new FileWriter(fileAddress + fileName)) {
                // Write the contents of the text area to the file
                fw.write(gui.textArea.getText());
            } catch (IOException e) {
                // Show error message if there is an exception
                JOptionPane.showMessageDialog(gui.room, "Error saving file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                System.out.println("SAVE AS ERROR: " + e.getMessage());
            }
        }
    }

    // Method to exit the application
    public void Exit() {
        System.exit(0);
    }

    // Method to print the current document
    public void printFile() {
        PrinterJob job = PrinterJob.getPrinterJob();
        // Set the printable content to the text area
        job.setPrintable(gui.textArea.getPrintable(null, null));
        boolean doPrint = job.printDialog();  // Show the print dialog
        if (doPrint) {
            try {
                job.print();  // Print the document
            } catch (PrinterException e) {
                System.out.println("PRINT ERROR");
            }
        }
    }

    // Method to export the current document as a PDF
    public void exportAsPDF() {
        // Create a FileDialog to select a location and name for the PDF
        FileDialog fd = new FileDialog(gui.room, "Export as PDF", FileDialog.SAVE);
        fd.setVisible(true);

        if (fd.getFile() != null) {
            fileName = fd.getFile();         // Get the selected file name
            fileAddress = fd.getDirectory(); // Get the directory of the selected file
            if (!fileName.toLowerCase().endsWith(".pdf")) {
                fileName += ".pdf";  // Ensure the file has a .pdf extension
            }

            try {
                // Create a new iText Document
                Document document = new Document();
                // Create a PdfWriter instance and associate it with the document and output stream
                PdfWriter.getInstance(document, new FileOutputStream(fileAddress + fileName));
                document.open();
                // Add the text area content as a paragraph to the PDF
                document.add(new Paragraph(gui.textArea.getText()));
                document.close();
                // Show success message
                JOptionPane.showMessageDialog(gui.room, "PDF exported successfully!", "Export Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                // Show error message if there is an exception
                JOptionPane.showMessageDialog(gui.room, "Error exporting PDF: " + e.getMessage(), "Export Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
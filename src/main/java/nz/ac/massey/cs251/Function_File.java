package nz.ac.massey.cs251;

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
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.odftoolkit.simple.TextDocument;

public class Function_File {
    RSyntaxTextArea textArea;
    RTextScrollPane scrollPane;

    GUI gui;
    String fileName;
    private File currentFile;
    String fileAddress;
    public Function_File(GUI gui) {
        this.gui=gui;
        this.currentFile = null;
    }

    public void newFile() {
        gui.textArea.setText("");
        gui.room.setTitle("New");
        fileName=null;
        fileAddress=null;
    }
    public void openFile() {
        FileDialog fd = new FileDialog(gui.room, "Open", FileDialog.LOAD);
        fd.setVisible(true);

        if (fd.getFile() != null) {
            fileName = fd.getFile();
            fileAddress = fd.getDirectory();
            gui.room.setTitle(fileName);

            try {
                File file = new File(fileAddress, fileName);
                String extension = getFileExtension(file);

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
                JOptionPane.showMessageDialog(gui.room, "Error opening file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                System.out.println("FILE IS NOT OPEN: " + e.getMessage());
            }
        }
    }

    private void openTextFile(File file) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            gui.textArea.setText("");
            String line;
            while ((line = br.readLine()) != null) {
                gui.textArea.append(line + "\n");
            }
        }
    }

    private void openRTFFile(File file) throws IOException {
        RTFEditorKit rtfKit = new RTFEditorKit();
        javax.swing.text.Document doc = rtfKit.createDefaultDocument();
        try (FileInputStream fis = new FileInputStream(file)) {
            rtfKit.read(fis, doc, 0);
            try {
                gui.textArea.setText(doc.getText(0, doc.getLength()));
            } catch (javax.swing.text.BadLocationException e) {
                throw new IOException("Error reading RTF document: Invalid location", e);
            }
        } catch (IOException e) {
            throw new IOException("Error reading RTF document: " + e.getMessage(), e);
        } catch (BadLocationException e) {
            throw new RuntimeException(e);
        }
    }


    private void openODTFile(File file) throws IOException {
        try {
            TextDocument odtDoc = TextDocument.loadDocument(file);
            gui.textArea.setText(odtDoc.getContentRoot().getTextContent());
        } catch (Exception e) {
            throw new IOException("Error reading ODT document: " + e.getMessage(), e);
        }
    }

    private String getFileExtension(File file) {
        String name = file.getName();
        int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return ""; // 空字符串表示没有扩展名
        }
        return name.substring(lastIndexOf + 1);
    }
    public String getFileName() {
        if (currentFile != null) {
            return currentFile.getName();
        } else {
            return "No file selected";
        }
    }


    public void SaveFile() {
        if (fileName == null) {
            SaveAsFile();
        } else {
            try (FileWriter fw = new FileWriter(fileAddress + fileName)) {
                fw.write(gui.textArea.getText());
                gui.room.setTitle(fileName);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(gui.room, "Error saving file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                System.out.println("SAVE ERROR: " + e.getMessage());
            }
        }
    }

    public void SaveAsFile() {
        FileDialog fd = new FileDialog(gui.room, "Save", FileDialog.SAVE);
        fd.setVisible(true);

        if (fd.getFile() != null) {
            fileName = fd.getFile();
            fileAddress = fd.getDirectory();
            gui.room.setTitle(fileName);

            try (FileWriter fw = new FileWriter(fileAddress + fileName)) {
                fw.write(gui.textArea.getText());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(gui.room, "Error saving file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                System.out.println("SAVE AS ERROR: " + e.getMessage());
            }
        }
    }
    public void Exit() {
        System.exit(0);
    }
    public void printFile() {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(gui.textArea.getPrintable(null, null));
        boolean doPrint = job.printDialog();
        if (doPrint) {
            try {
                job.print();
            } catch (PrinterException e) {
                System.out.println("PRINT ERROR");
            }
        }
    }

    public void exportAsPDF() {
        FileDialog fd = new FileDialog(gui.room, "Export as PDF", FileDialog.SAVE);
        fd.setVisible(true);

        if (fd.getFile() != null) {
            fileName = fd.getFile();
            fileAddress = fd.getDirectory();
            if (!fileName.toLowerCase().endsWith(".pdf")) {
                fileName += ".pdf";
            }

            try {
                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream(fileAddress + fileName));
                document.open();
                document.add(new Paragraph(gui.textArea.getText()));
                document.close();
                JOptionPane.showMessageDialog(gui.room, "PDF exported successfully!", "Export Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(gui.room, "Error exporting PDF: " + e.getMessage(), "Export Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

}

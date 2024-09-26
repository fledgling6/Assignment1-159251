package org.example;

import java.awt.*;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
public class Function_File {
    GUI gui;
    String fileName;
    String fileAddress;
    public Function_File(GUI gui) {
        this.gui=gui;

    }
    public void newFile() {
        gui.textArea.setText("");
        gui.room.setTitle("New");
        fileName=null;
        fileAddress=null;
    }
    public void openFile() {
        FileDialog fd = new FileDialog(gui.room,"Open",FileDialog.LOAD);
        fd.setVisible(true);

        if(fd.getFile()!=null){
            fileName=fd.getFile();
            fileAddress=fd.getDirectory();
            gui.room.setTitle(fileName);
        }
        try{
            File file = new File(fileAddress, fileName);
            BufferedReader br = new BufferedReader(new FileReader(file));
            gui.textArea.setText("");
            String line = null;
            while ((line = br.readLine())!=null){
                gui.textArea.append(line+"\n");
            }
            br.close();
        }catch (Exception e){
            System.out.println("FILE IS NOT OPEN");
        }
    }

    public void SaveFile() {
        if(fileName==null){
            SaveAsFile();
        }
        else {
            try {
                FileWriter fw = new FileWriter(fileAddress+fileName);
                fw.write(gui.textArea.getText());
                gui.room.setTitle(fileName);
                fw.close();
            }catch (Exception e){
                System.out.println("SOMETHING WRONG");
            }
        }

    }
    public  void SaveAsFile() {
        FileDialog fd = new FileDialog(gui.room,"Save",FileDialog.SAVE);
        fd.setVisible(true);

        if(fd.getFile()!=null){
            fileName = fd.getFile();
            fileAddress = fd.getDirectory();
            gui.room.setTitle(fileName);
        }

        try {
            FileWriter fw = new FileWriter(fileAddress+fileName);
            fw.write(gui.textArea.getText());
            fw.close();
        }catch (Exception e){
            System.out.println("SOMETHING WRONG");
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
                System.out.println("PDF exported successfully!");
            } catch (Exception e) {
                System.out.println("EXPORT AS PDF ERROR");
            }
        }
    }

}

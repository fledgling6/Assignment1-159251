package nz.ac.massey.cs251;

public class Function_Manage {
    GUI gui;

    public Function_Manage(GUI gui) {
        this.gui = gui;
    }

    public void CutText() {
        // Cut the selected text and remove it from the text area
        gui.textArea.cut();
    }

    public void CopyText() {
        // Copy the selected text to the clipboard
        gui.textArea.copy();
    }

    public void PasteText() {
        // Paste text from the clipboard into the text area
        gui.textArea.paste();
    }

    public void SelectAll() {
        // Select all text in the text area
        gui.textArea.selectAll();
    }
}

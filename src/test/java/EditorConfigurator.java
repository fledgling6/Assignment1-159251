import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import java.awt.*;
import java.util.Map;

public class EditorConfigurator {
    public void applyConfiguration(RSyntaxTextArea textArea, Map<String, Object> config) {
        Map<String, Object> editorConfig = (Map<String, Object>) config.get("editor");

        // Apply font and font size
        String fontName = (String) editorConfig.getOrDefault("font", "Monospaced");
        int fontSize = (int) editorConfig.getOrDefault("fontSize", 12);
        textArea.setFont(new Font(fontName, Font.PLAIN, fontSize));

        // Apply theme
        String theme = (String) editorConfig.getOrDefault("theme", "Light");
        if ("Dark".equals(theme)) {
            textArea.setBackground(Color.DARK_GRAY);
            textArea.setForeground(Color.WHITE);
        } else {
            textArea.setBackground(Color.WHITE);
            textArea.setForeground(Color.BLACK);
        }

        // Apply word wrapping
        boolean wordWrapping = (boolean) editorConfig.getOrDefault("wordWrapping", false);
        textArea.setLineWrap(wordWrapping);
        textArea.setWrapStyleWord(wordWrapping);
    }
}
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class EditorConfiguratorTest {

    private RSyntaxTextArea textArea;
    private EditorConfigurator configurator;

    @BeforeEach
    void setUp() {
        textArea = new RSyntaxTextArea();
        configurator = new EditorConfigurator();
    }

    @Test
    void testApplyConfiguration() {
        Map<String, Object> config = new HashMap<>();
        Map<String, Object> editorConfig = new HashMap<>();
        editorConfig.put("font", "Courier New");
        editorConfig.put("fontSize", 14);
        editorConfig.put("theme", "Dark");
        editorConfig.put("wordWrapping", true);
        config.put("editor", editorConfig);

        configurator.applyConfiguration(textArea, config);

        assertEquals("Courier New", textArea.getFont().getFontName());
        assertEquals(14, textArea.getFont().getSize());
        assertEquals(Color.DARK_GRAY, textArea.getBackground());
        assertEquals(Color.WHITE, textArea.getForeground());
        assertTrue(textArea.getLineWrap());
        assertTrue(textArea.getWrapStyleWord());
    }

}
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ConfigLoaderTest {

    @TempDir
    Path tempDir;

    @Test
    void testLoadConfig() throws IOException {
        // 创建临时的 YAML 文件
        Path tempFile = tempDir.resolve("config.yaml");
        Files.writeString(tempFile, "editor:\n" +
                "  font: Monospaced\n" +
                "  fontSize: 12\n" +
                "  theme: Light\n" +
                "  wordWrapping: false\n");

        // 使用临时目录中的文件进行测试
        System.setProperty("user.dir", tempDir.toString());

        Map<String, Object> config = ConfigLoader.loadConfig();

        assertNotNull(config);
        assertTrue(config.containsKey("editor"));
        Map<String, Object> editorConfig = (Map<String, Object>) config.get("editor");
        assertEquals("Monospaced", editorConfig.get("font"));
        assertEquals(12, editorConfig.get("fontSize"));
        assertEquals("Light", editorConfig.get("theme"));
        assertFalse((Boolean) editorConfig.get("wordWrapping"));
    }

    @Test
    void testLoadConfigWithMissingFile() {
        // 设置系统属性以指向不存在的文件
        System.setProperty("user.dir", "/nonexistent/path");

        Exception exception = assertThrows(RuntimeException.class, () -> {
            ConfigLoader.loadConfig();
        });

        String expectedMessage = "Unable to find config.yaml on the classpath.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
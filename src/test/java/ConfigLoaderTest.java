import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.yaml.snakeyaml.error.YAMLException;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class ConfigLoaderTest {

    private Path validConfigPath;
    private Path invalidConfigPath;
    private Path missingConfigPath;

    @BeforeEach
    void setUp() {
        // Set paths for the test cases
        validConfigPath = Paths.get("src/test/resources/config.yaml");
        invalidConfigPath = Paths.get("src/test/resources/invalid-config.yaml");
        missingConfigPath = Paths.get("src/test/resources/missing-config.yaml");
    }


    @Test
    void testLoadConfigWithInvalidFile() {
        assertThrows(RuntimeException.class, () -> {
            ConfigLoader.loadConfig(invalidConfigPath);
        });
    }
    @Test
    void testLoadMissingConfig() {
        // Test loading a non-existing (missing) config file
        Exception exception = assertThrows(RuntimeException.class, () -> ConfigLoader.loadConfig(missingConfigPath));
        assertTrue(exception.getCause() instanceof java.nio.file.NoSuchFileException);
        assertEquals("Failed to load configuration file: " + missingConfigPath, exception.getMessage());
    }
}

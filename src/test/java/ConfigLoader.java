import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.error.YAMLException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ConfigLoader {
    private static final Path DEFAULT_CONFIG_PATH = Paths.get("config.yaml");

    public static Config loadConfig() {
        return loadConfig(DEFAULT_CONFIG_PATH);
    }

    public static Config loadConfig(Path configPath) {
        try {
            // Print the absolute path of the config file
            System.out.println(configPath.toAbsolutePath().toString());

            // Load the configuration from the specified file path
            Yaml yaml = new Yaml(new Constructor(Config.class));
            Config config = yaml.load(Files.newInputStream(configPath));
            return config;
        } catch (IOException e) {
            // Handle the case where the configuration file is missing or unreadable
            throw new RuntimeException("Failed to load configuration file: " + configPath, e);
        } catch (YAMLException e) {
            // Handle the case where the configuration file is not in the expected YAML format
            throw new RuntimeException("Invalid configuration format in file: " + configPath, e);
        }
    }

    public static class Config {
        private Editor editor;

        public Editor getEditor() {
            return editor;
        }

        public static class Editor {
            private String font;
            private int fontSize;
            private String theme;
            private boolean wordWrapping;

            public String getFont() {
                return font;
            }

            public int getFontSize() {
                return fontSize;
            }

            public String getTheme() {
                return theme;
            }

            public boolean isWordWrapping() {
                return wordWrapping;
            }
        }
    }
}

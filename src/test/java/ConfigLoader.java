import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;

import java.io.InputStream;
import java.util.Map;

public class ConfigLoader {
    public static Map<String, Object> loadConfig() {
        Yaml yaml = new Yaml();
        InputStream inputStream = ConfigLoader.class.getClassLoader().getResourceAsStream("config.yaml");

        if (inputStream == null) {
            throw new RuntimeException("Unable to find config.yaml on the classpath.");
        }

        try {
            return (Map<String, Object>) yaml.load(inputStream);
        } catch (YAMLException e) {
            throw new RuntimeException("Error parsing config.yaml: " + e.getMessage(), e);
        }
    }
}
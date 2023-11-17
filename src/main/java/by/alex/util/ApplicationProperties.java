package by.alex.util;

import lombok.Data;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

import static by.alex.constant.ConstantApp.DATABASE_PROPERTIES_FILE_PATH;

@Data
public class ApplicationProperties {
    private static final Map<String, String> properties;


    static {
        try (
                InputStream inputStream = ApplicationProperties.class.getClassLoader().getResourceAsStream(DATABASE_PROPERTIES_FILE_PATH);
        ) {
            Yaml yaml = new Yaml();
            Map<String, Map<String, String>> data = yaml.load(inputStream);
            properties = data.get("database");
        } catch (Exception e) {

            System.err.println("Error initialization DataBaseConnector: " + e.getMessage());
            throw new RuntimeException("Error initialization DataBaseConnector: " + e.getMessage());
        }
    }

    public static Map<String, String> prop() {
        return properties;
    }

}

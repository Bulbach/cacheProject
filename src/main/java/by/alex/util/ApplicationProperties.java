package by.alex.util;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

import static by.alex.constant.ConstantApp.DATABASE_PROPERTIES_FILE_PATH;


public class ApplicationProperties {

    private static final Map<String, String> properties;
    private static final Map<String, String> cacheProperties;


    static {
        try (
                InputStream inputStream = ApplicationProperties.class.getClassLoader().getResourceAsStream(DATABASE_PROPERTIES_FILE_PATH);
        ) {
            Yaml yaml = new Yaml();
            Map<String, Map<String, String>> data = yaml.load(inputStream);
            properties = data.get("database");
            cacheProperties = data.get("cache");
        } catch (Exception e) {

            System.err.println("Error initialization DataBaseConnector: " + e.getMessage());
            throw new RuntimeException("Error initialization DataBaseConnector: " + e.getMessage());
        }
    }

    public static Map<String, String> prop() {
        return properties;
    }
    public static Map<String, String> cacheProp() {
        return cacheProperties;
    }

}

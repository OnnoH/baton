package nl.onnoh.baton.util;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

public class ReadProperties {

    static Properties applicationProperties = new Properties();

    public static Properties load(String propertyFile) {
        try (InputStream propertyStream = ReadProperties.class.getResourceAsStream(propertyFile)) {
            System.out.println("Reading " + propertyFile);
            if (propertyFile.endsWith(".yaml") || propertyFile.endsWith(".yml")) {
                Yaml yaml = new Yaml();
                Map<String, Object> props = yaml.load(propertyStream);
                applicationProperties = flatten(props, null);
            } else {
                applicationProperties.load(propertyStream);
            }
            replace(applicationProperties);

            return applicationProperties;

        } catch (Exception e) {
            System.err.println("Error reading " + propertyFile);
        }

        return null;
    }
    @SuppressWarnings("unchecked")
    private static Properties flatten(Map<String, Object> item, String parentKey) {
        item.forEach((key, value) -> {
            String parent = (parentKey == null ? "" : parentKey + ".") + key;
            if (value instanceof Map) {
                flatten((Map<String, Object>) value, parent);
            } else {
                applicationProperties.setProperty(parent, value.toString());
            }
        });

        return applicationProperties;
    }

    private static void replace(Properties properties) {
        properties.forEach((key, value) -> {
            String newValue = value.toString();
            if (newValue != null) {
                if (newValue.startsWith("${") && newValue.endsWith("}")) {
                    newValue = newValue.substring(2, newValue.length() - 1);
                    System.out.println("Replacing " + newValue);
                    if (System.getenv().containsKey(newValue)) {
                        newValue = System.getenv(newValue);
                    } else if (applicationProperties.containsKey(newValue)) {
                        newValue = applicationProperties.getProperty(newValue);
                    }
                }
            }
            applicationProperties.setProperty(key.toString(), newValue);
        });
    }
}


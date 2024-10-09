package nl.onnoh.baton.util;

import com.samskivert.mustache.Mustache;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static nl.onnoh.baton.util.Resource.filterResources;

public class Template {

    private static final String TEMPLATE_PATH = "templates";
    private void executeTemplate (Reader template, Writer writer, Map<String, String> data) {
        Mustache.compiler().compile(template).execute(data, writer);
    }

    private Map<String, String> getTemplateProperties(String projectName) {

        Map<String, String> templateProperties = new HashMap<>();
        templateProperties.put("projectName", projectName);

        return templateProperties;
    }

    public void processTemplates(String projectName, String language, String framework) {
        Resource.filterResources(Resource.listResources(), TEMPLATE_PATH, language, framework).forEach(file -> {
            System.out.println("Processing " + file.getPath());
            try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(file.getPath())){
                assert inputStream != null;
                Reader reader = new InputStreamReader(inputStream);
                File newFile = new File(file.getAbsolutePath()
                        .replace(TEMPLATE_PATH, projectName)
                        .replace(language, "")
                        .replace(framework, "")
                        .replace("//", "/"));
                System.out.println("Creating " + newFile.getPath());
                if (newFile.getParentFile().mkdirs()) {
                    System.out.println("Directory " + file.getParentFile().getName() +" is created!");
                }
                Writer writer = new FileWriter(newFile);
                executeTemplate(reader, writer, getTemplateProperties(projectName));
                reader.close();
                writer.close();
            } catch (IOException e) {
                System.err.println("Error processing template " + file.getPath() + ": " + e.getMessage());
            }
        });
    }
}

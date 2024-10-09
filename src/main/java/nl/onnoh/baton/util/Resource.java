package nl.onnoh.baton.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Resource {
    private static final String RAT = "common/resource-allocation-table";
    public static List<File> listResources() {
        List<File> fileList = new ArrayList<>();
        try (InputStream inputStream = Resource.class.getClassLoader().getResourceAsStream(RAT)) {
            if (Objects.isNull(inputStream)) {
                System.err.println("Could not find the RAT.");
                return fileList;
            }
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            while(bufferedReader.ready()) {
                String line = bufferedReader.readLine();
                    fileList.add(new File(line));
            }
        } catch ( IOException e) {
            System.err.println("Could not read the RAT : " + e.getMessage());
        }
        return fileList;
    }

    public static List<File> filterResources(List<File> fileList, String path) {
        List<File> filteredList = listResources();
        fileList.forEach(file -> {
            if (file.toString().contains(path)) {
                filteredList.add(file);
            }
        });

        return filteredList;
    }
    public static List<File> filterResources(List<File> fileList, String path, String language, String framework) {
        List<File> filteredList = listResources();
        fileList.forEach(file -> {
            if (file.toString().contains(path) && file.toString().contains(language) && file.toString().contains(framework)) {
                filteredList.add(file);
            }
        });

        return filteredList;
    }

}

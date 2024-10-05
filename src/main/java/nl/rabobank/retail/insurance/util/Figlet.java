package nl.rabobank.retail.insurance.util;

import com.github.lalyos.jfiglet.FigletFont;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Figlet {

    private static final String FONTS_FOLDER = "figlet-fonts";

    public static String createFiglet(String text, String font) throws IOException {

        return FigletFont.convertOneLine(Figlet.class.getClassLoader().getResourceAsStream(FONTS_FOLDER + "/" + font + ".flf"), text);

    }

    private List<File> filterFontResources(String path) {
        List<File> filteredList = Resource.listResources();
        filteredList.forEach(file -> {
            if (file.toString().contains(path)) {
                filteredList.add(file);
            }
        });

        return filteredList;
    }
}

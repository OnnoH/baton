package nl.onnoh.baton.commands.banner;

import com.github.lalyos.jfiglet.FigletFont;
import nl.onnoh.baton.util.Figlet;
import nl.onnoh.baton.util.Resource;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.io.File;
import java.util.List;
import java.util.concurrent.Callable;

@Command(name = "banner")
public class BannerCommand implements Callable<Object> {

    private static final String FONTS_FOLDER = "figlet-fonts";
    private static final String DEFAULT_FONT = "standard";

    private String font;

    @Parameters(index = "0", arity = "1", description = "The text to be converted to art.")
    private String text;

    @CommandLine.Option(names = {"-f", "--font"}, defaultValue = DEFAULT_FONT, description = "The font that is used to create the banner.")
    private void setFont(String font) {
        if (Font.isValid(font)) {
            this.font = font;
        } else {
            throw new CommandLine.ParameterException(new CommandLine(this), "Invalid font: " + font);
        }
    }

    @Override
    public Object call() throws Exception {

        System.out.println();
        System.out.println(
            FigletFont.convertOneLine(Figlet.class.getClassLoader().getResourceAsStream(FONTS_FOLDER + "/" + font + ".flf"), text)
        );
        System.out.println();

        return null;
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

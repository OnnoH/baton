package nl.onnoh.baton.commands.scaffold;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.StoredConfig;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Callable;

import static nl.onnoh.baton.util.Figlet.createFiglet;

@Command(name = "scaffold")
public class ScaffoldCommand implements Callable<Integer> {

    private static final String DEFAULT_LANGUAGE = "java";
    private static final String DEFAULT_FRAMEWORK = "spring-boot";
    private static final String DEFAULT_BUILD_TOOL = "maven";
    private static final String DEFAULT_LIBRARY = "none";
    private static final String SB_BANNER_NAME = "banner.txt";
    private static final String MN_BANNER_NAME = "micronaut-banner.txt";
    private static final String USER_HOME_PROPERTY = "user.home";


    private String language;
    private String framework;
    private String buildTool;
    private String library;

    Set<String> libraries = new LinkedHashSet<>();

    @Parameters(index = "0", arity = "1", description = "The name of the project to be scaffolded.")
    private String projectName;

    @Option(names = {"-l", "--language"}, defaultValue = DEFAULT_LANGUAGE, description = "The language the code is generated for (default: ${DEFAULT-VALUE}).")
    private void setLanguage(String language) {
        if (Language.isValid(language)) {
            this.language = language;
        } else {
            throw new CommandLine.ParameterException(new CommandLine(this), "Invalid language: " + language);
        }
    }

    @Option(names = {"-fw", "--framework"}, defaultValue = DEFAULT_FRAMEWORK, description = "The framework the generated code is using (default: ${DEFAULT-VALUE}).")
    private void setFramework(String framework) {
        if (Framework.isValid(framework)) {
            this.framework = framework;
        } else {
            throw new CommandLine.ParameterException(new CommandLine(this), "Invalid framework: " + framework);
        }
    }

    @Option(names = {"-bt", "--build-tool"}, defaultValue = DEFAULT_BUILD_TOOL, description = "The build tool to be used (default: ${DEFAULT-VALUE}).")
    private void setBuildTool(String buildTool) {
        if (BuildTool.isValid(buildTool)) {
            this.buildTool = buildTool;
        } else {
            throw new CommandLine.ParameterException(new CommandLine(this), "Invalid build tool: " + buildTool);
        }
    }

    @Option(names = {"-lib", "--library"}, defaultValue = DEFAULT_LIBRARY, description = "The library to be included (default: ${DEFAULT-VALUE}).")
    private void setLibrary(String library) {
        if (Library.isValid(library)) {
            libraries.add(library);
        } else {
            throw new CommandLine.ParameterException(new CommandLine(this), "Invalid library: " + library);
        }
    }

    @Override
    public Integer call() throws Exception {
        String userHome = System.getProperty(USER_HOME_PROPERTY);
        System.setProperty(USER_HOME_PROPERTY, projectName);

        System.out.println("Scaffolding " + projectName);
        createProject(projectName, Objects.requireNonNull(Language.getLanguage(language)), Objects.requireNonNull(BuildTool.getBuildTool(buildTool)));
        createBanner(projectName, Objects.requireNonNull(Framework.getFramework(framework)));
        System.out.println("Libraries : "  + libraries);

        System.setProperty(USER_HOME_PROPERTY, userHome);
        return execGit();
    }

    private void createProject(String projectName, Language language, BuildTool buildTool) {
        createFolder(projectName);
        String languageFolder = language.getName();
        switch (buildTool) {
            case GRADLE:
                break;
            case NONE:
                break;
            case MAVEN:
            default:
                createFolder(projectName + "/src/main/" + languageFolder);
                createFolder(projectName + "/src/main/resources");
                createFolder(projectName + "/src/test/" + languageFolder);
                createFolder(projectName + "/src/test/resources");
                break;
        }

    }

    private void createBanner(String projectName, Framework framework) throws IOException {
        switch (framework) {
            case MICRONAUT:
                createFile(projectName + "/" + MN_BANNER_NAME, createFiglet(projectName, null));
                break;
            case SPRING_BOOT:
            default:
                createFile(projectName + "/" + SB_BANNER_NAME, createFiglet(projectName, null));
        }
    }

    private Integer execGit() {
        try (Git git = Git.init().setDirectory(new File(projectName)).call()) {
            String repositoryUrl = "";
            System.out.println("Created a new repository at " + projectName);
            git.add().addFilepattern(".").call();
            git.commit().setMessage("Initial commit").call();
            StoredConfig config = git.getRepository().getConfig();
            config.setString("remote", "origin", "url", repositoryUrl);
            config.save();
            git.push().setRemote("origin").setPushAll().call();
        } catch (GitAPIException e) {
            System.err.println("Failed to create a new repository at " + projectName + " : " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Failed to save the configuration of the repository at " + projectName + " : " + e.getMessage());
        }

        return 0;
    }

    private void createFolder(String folderPath) {
        try {
            Path path = Paths.get(folderPath);
            Files.createDirectories(path);
            System.out.println("Directory " + path + " is created!");
        } catch (IOException e) {
            System.err.println("Failed to create directory!" + e.getMessage());
        }
    }

    private void createFile(String filePath, String contents) {
        try {
            File newFile = new File(filePath);
            if (newFile.createNewFile()) {
                FileWriter writeContents = new FileWriter(filePath);
                writeContents.write(contents);
                writeContents.close();
                System.out.println("File " + newFile.getName() + " is created!");
            } else {
                System.err.println("File " + newFile.getName() + " already exists.");
            }
        } catch (IOException e) {
            System.err.println("Failed to create file!" + e.getMessage());
        }
    }

}

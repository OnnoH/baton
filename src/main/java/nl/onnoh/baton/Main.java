package nl.onnoh.baton;

import nl.onnoh.baton.commands.banner.BannerCommand;
import nl.onnoh.baton.commands.bpmn.AnalyseCommand;
import nl.onnoh.baton.commands.scaffold.ScaffoldCommand;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(
          name = "baton"
        , description = "CLI for Camunda Conductors"
        , version = "1.0"
        , subcommands = {
              ScaffoldCommand.class
            , BannerCommand.class
            , AnalyseCommand.class
        }
)
class Main {
    @Option(names = {"-h", "--help"}, usageHelp = true, description = "Displays a help message.")
    private boolean helpRequested = false;

    @Option(names = {"-pf", "--property-file"}, description = "Location of the property file.")
    private String propertyFile;

    @Option(names = {"-v", "--verbose"}, description = "Verbose output.")
    private boolean verbose = false;
    public static void main(String... args) throws Exception {
        int exitCode = new CommandLine(new Main()).execute(args);
        System.exit(exitCode);
    }

}

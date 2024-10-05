package nl.rabobank.retail.insurance.util;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecShellCommand {

    public static Integer execCommand(String projectName, String[] commands) throws IOException {
        int exitCode = 1;
        String command = String.join(" ", commands);

        try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {

            ProcessBuilder builder = new ProcessBuilder();
            builder.directory(new File( projectName ).getAbsoluteFile());
            if (isWindows()) {
                builder.command("cmd.exe", "/c", command);
            } else {
                builder.command("sh", "-c", command);
            }
            System.out.println("Executing " + builder.command().toString());
            Process process = builder.start();
            StreamGobbler streamGobbler = new StreamGobbler(process.getInputStream(), System.out::println);
            Future<?> future = executorService.submit(streamGobbler);

            exitCode = process.waitFor();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return exitCode;

    }

    private static Boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().startsWith("windows");
    }
}

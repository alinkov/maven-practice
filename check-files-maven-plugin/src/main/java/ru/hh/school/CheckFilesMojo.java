package ru.hh.school;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import java.io.File;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 *   This plugin counts the files number in a directory
 *  and displays the detailing (count of сontaining files and subdirectories)
 *  if their number does not exceed the specified.
 */
@Mojo(name = "check-files")
public class CheckFilesMojo extends AbstractMojo {
    /**
     * Path to the top directory
     */
    @Parameter(property = "path", defaultValue = "./")
    private File path;

    /**
     * Number to compare with count files in directory
     */
    @Parameter(property = "count", defaultValue = "22")
    private int count;

    public void execute()
            throws MojoExecutionException {
        int countFiles = getCount(path);
        getLog().info("------------------------------------------------------------------------");
        getLog().info("              The required directory has " + countFiles + " files.");
        if (countFiles > count) {
            getLog().info("                   That's more than " + count + " files.");
        }
        else {
            getLog().info("                   That's less than " + count + " files.");
            getLog().info("------------------------------------------------------------------------");
            getLog().info("Detailing:");
            printDetailing(path);
        }
        getLog().info("------------------------------------------------------------------------");
    }
    private int getCount(File directory) {
        int filesCount = 0;
        File[] listFiles = directory.listFiles();
        assert listFiles != null;
        for (File subFile : listFiles) {
            if (subFile.isFile()) {
                filesCount++;
            } else {
                filesCount += getCount(subFile);
            }
        }
        return (filesCount);
    }

    private void printDetailing(File directory) {
        int filesCount = 0;
        int directoryCount = 0;
        File[] listFiles = directory.listFiles();
        assert listFiles != null;
        for (File subFile : listFiles) {
            if (subFile.isFile()) {
                filesCount++;
            } else {
                directoryCount++;
                printDetailing(subFile);
            }
        }
        getLog().info(String.format("Directory %s contains %s subdirectories and %s files.", directory, directoryCount, filesCount));
    }
}
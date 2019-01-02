package ru.hh.school;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 *  This plugin counts the files number in a directory.
 *  and displays the detailing (count of containing files and subdirectories).
 *  if their number does not exceed the specified.
 *  @author KseniaKolesnikova
 */
@Mojo(name = "check-files")
public class CheckFilesMojo extends AbstractMojo {
    /**
     * Path to the top directory.
     */
    @Parameter(property = "path", defaultValue = "${basedir}")
    private File path;

    /**
     * Number to compare with count files in the directory.
     */
    @Parameter(property = "count", defaultValue = "10")
    private int count;

    /**
     * Number of files in the directory.
     */
    private int fileCount;

    @Override
    public void execute() throws MojoExecutionException {
        if (path.exists()) {
            fileCount = 0;
            List<String> detailing = new ArrayList<String>();
            getCount(path, detailing);
            printResult(detailing);
        } else {
            getLog().warn("Directory " + path + " does not exists.");
        }
    }

    /**
     * Recursive search files count.
     * @param directory is path to the calculating directory
     * @param detailing is list of strings for displaying detailing
     */
    private void getCount(File directory, List<String> detailing) {
        int filesCount = 0;
        int directoryCount = 0;
        if (!directory.canRead()) {
            getLog().warn("Directory " + directory + " cannot be read");
            return;
        }
        File[] listFiles = directory.listFiles();
        if (listFiles != null) {
            for (File subFile : listFiles) {
                if (subFile.isFile()) {
                    filesCount++;
                } else {
                    directoryCount++;
                    getCount(subFile, detailing);
                }
            }
        }
        fileCount += filesCount;
        if (fileCount < count) {
            detailing.add(String.format("Directory %s contains %s subdirectories and %s files.",
                    directory, directoryCount, filesCount));
        }
    }

    /**
     * Displaying detailing.
     * @param detailing is path to the calculating directory
     */
    private void printResult(List<String> detailing) {
        getLog().info("------------------------------------------------------------------------");
        getLog().info("              The required directory has " + fileCount + " files.");
        if (fileCount > count) {
            getLog().info("                   That's more than " + count + " files.");
        } else {
            getLog().info("                   That's less than " + count + " files.");
            getLog().info("------------------------------------------------------------------------");
            getLog().info("Detailing:");
            for (String detail : detailing) {
                getLog().info(detail);
            }
        }
        getLog().info("------------------------------------------------------------------------");
    }
}

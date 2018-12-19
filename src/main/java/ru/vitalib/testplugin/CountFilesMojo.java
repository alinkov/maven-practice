package ru.vitalib.testplugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Count number of files in directory (default value = "${basedir}/src/main) and
 * its subdirectories and display in case have less then given number of files.
 *
 */
@Mojo(name = "countFiles", defaultPhase = LifecyclePhase.VALIDATE, threadSafe = true)
public class CountFilesMojo extends AbstractMojo {
    /**
     * Absolute path of directory starting with "/"
     * or relative path starting with directory name inside of root project folder
     * (directory of pom.xml)
     */
    @Parameter(defaultValue = "${basedir}", property = "rootDirectory")
    private File rootDirectory;

    /**
     * Minimum number of files permitted for directory
     */
    @Parameter(defaultValue = "1", property = "minNumberOfFiles")
    private Integer minNumberOfFiles;


    /**
     * Depth of search in directory tree
     */
    @Parameter(defaultValue = "1", property = "depthOfSearch")
    private Integer depthOfSearch;


    private Map<File, Integer> numberOfFilesInDirectories = new HashMap<>();

    public void execute() throws MojoExecutionException {
        validateInput();
        getLog().info("Serach in directory: " + rootDirectory);
        getLog().info("Depth of search " + depthOfSearch);
        getLog().info("Minimal quantity of files: " + minNumberOfFiles);
        countFilesInDirectory(rootDirectory, depthOfSearch);
        if (numberOfFilesInDirectories.size() != 0) {
            getLog().info(
        "The following directories has less then "
                    + minNumberOfFiles + " file(s):"
            );

            for (Map.Entry<File, Integer> entry: numberOfFilesInDirectories.entrySet()) {
                getLog().info(entry.getKey() + ": " + entry.getValue() + " file(s)");
            }
        } else {
            getLog().info(
                    "All directories have more or equal "
                    + minNumberOfFiles + " files.");
        }
    }

    void countFilesInDirectory(File directory, int requiredDepth) {
        if (requiredDepth < 1) {
            return;
        }
        if (!directory.canRead()) {
            getLog().info(directory + " Permission denied");
            return;
        }
        int counter = 0;
        for (File file : directory.listFiles()) {
            if (!file.canRead()) {
                getLog().info(file + " Permission denied");
                continue;
            }
            if (file.isFile()) {
                counter++;
            } else if (file.isDirectory()) {
                countFilesInDirectory(file, requiredDepth - 1);
            }
        }
        if (counter < minNumberOfFiles) {
            numberOfFilesInDirectories.put(directory, counter);
        }
    }

    void validateInput() throws MojoExecutionException {
        if (!rootDirectory.canRead()) {
            throw new MojoExecutionException(rootDirectory + " Permission denied");
        }
        if (!rootDirectory.isDirectory()) {
            throw new MojoExecutionException(rootDirectory + " is not a directory.");
        }
        if (minNumberOfFiles < 1) {
            throw new MojoExecutionException("Number of files should be more then 0");
        }
        if (depthOfSearch < 1) {
            throw new MojoExecutionException("Depth of search should be more or equal to 1");
        }
    }
}


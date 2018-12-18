package ru.vitalib.testplugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

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
    @Parameter(defaultValue = "${basedir}")
    private File rootDirectory;

    /**
     * Minimum number of files permitted for directory
     */
    @Parameter(defaultValue = "1")
    private Integer minNumberOfFiles;


    /**
     * Depth of search in directory tree
     */
    @Parameter(defaultValue = "1")
    private Integer depthOfSearch;


    public void execute() throws MojoExecutionException {
        validateInput();
        getLog().info("Files directory: " + rootDirectory);
        Map<File, Integer> numberOfFilesInDirectories = countFilesInDirectory(
                rootDirectory,
                depthOfSearch
        );
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

    class Pair {
        File file;
        Integer depth;
        Pair(File file, Integer depth) {
            this.file = file;
            this.depth = depth;
        }
    }

    private Map<File, Integer> countFilesInDirectory(File testsDirectory, int requiredDepth) {
        Queue<Pair> dirsQueue = new LinkedList<>();
        int depth = 1;
        dirsQueue.add(new Pair(testsDirectory, depth));
        Map<File, Integer> quauntityOfFilesInDirectories = new HashMap<>();
        while (dirsQueue.size() > 0 && depth <= requiredDepth) {
            Pair pair = dirsQueue.poll();
            File currentDir = pair.file;
            if (!currentDir.canRead()) {
                getLog().info(currentDir + " Permission denied");
                continue;
            }
            depth = pair.depth++;
            int counter = 0;
//            System.out.println("Before for " + currentDir + "is Dir: " + currentDir.isDirectory());
            for (File file : currentDir.listFiles()) {
                if (file.isFile()) {
                    counter++;
                } else if (file.isDirectory()) {
                    dirsQueue.add(new Pair(file, depth));
                }
            }
            if (counter < minNumberOfFiles) {
                quauntityOfFilesInDirectories.put(currentDir, counter);
            }
        }
        return quauntityOfFilesInDirectories;
    }

    void validateInput() throws MojoExecutionException {
        if (!rootDirectory.exists()) {
            throw new MojoExecutionException(rootDirectory + " doesn't exists.");
        }
        if (minNumberOfFiles < 1) {
            throw new MojoExecutionException("Number of files should be more then 0");
        }
        if (depthOfSearch < 1) {
            throw new MojoExecutionException("Depth of search should be more or equal to 1");
        }
    }
}


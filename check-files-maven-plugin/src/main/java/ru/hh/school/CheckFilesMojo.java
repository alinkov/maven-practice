package ru.hh.school;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import java.io.File;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 *  This plugin counts the files number in a directory
 *  and displays the detailing (count of сontaining files and subdirectories)
 *  if their number does not exceed the specified.
 *
 *  Parameters:
 *  path - path to the top directory
 *  count - number to compare with count files in directory
 */

@Mojo(name = "check-count")
public class CheckFilesMojo extends AbstractMojo {
    @Parameter(property = "path", defaultValue = "./")
    private String path;

    @Parameter(property = "count", defaultValue = "20")
    private String count;

    public void execute()
            throws MojoExecutionException {
        int countFiles = getCount(new File(path));
        System.out.println("-------------------------------------------------------------------------------");
        System.out.println("              The required directory has " + countFiles + " files.");
        if (countFiles > Integer.parseInt(count)) {
            System.out.println("                   That's more than " + count + " files.");
        }
        else {
            System.out.println("                   That's less than " + count + " files.");
            System.out.println("-------------------------------------------------------------------------------");
            System.out.println("Detailing:");
            getDetailing(new File(path));
        }
        System.out.println("-------------------------------------------------------------------------------");
    }
    private int getCount(File Directory) {
        int filesCount = 0;
        File[] listFiles = Directory.listFiles();
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

    private void getDetailing(File Directory) {
        int filesCount = 0;
        int directoryCount = 0;
        File[] listFiles = Directory.listFiles();
        assert listFiles != null;
        for (File subFile : listFiles) {
            if (subFile.isFile()) {
                filesCount++;
            } else {
                directoryCount++;
                getDetailing(subFile);
            }
        }
        System.out.println("Directory " + Directory + " contains " + directoryCount + " subdirectories and " + filesCount + " files.");
    }
}
package ru.hh.school;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * This plugin sorts files and dirictories by date.
 *  @author AleksandrFursov
 */
@Mojo(name = "sort")
final class DateControl extends AbstractMojo {

  /**
   * Absolute path of directory starting with "/"
   * or relative path inside of root project folder.
   */
  @Parameter(property = "rootDir", defaultValue = "${basedir}")
  private File rootDir;

  /**
   * Sort ascending(true) or descending(false).
   */
  @Parameter(property = "sortAsc", defaultValue = "true")
  private boolean sortAsc;

  /**
   * Sort by creation date(true) or change date(false).
   */
  @Parameter(property = "mode", defaultValue = "true")
  private boolean mode;

  /**
   * List for saving attributes of files.
   */
  private List<String> filesArr = new ArrayList<String>();

  @Override
  public void execute() throws MojoExecutionException {
    printParameters();
    checkInput();
    fillFilesArr(rootDir);
    if (filesArr.size() == 0) {
      getLog().info("The directory " + rootDir + " is empty.");
      return;
    }
    Collections.sort(filesArr);
    if (!sortAsc) {
      Collections.reverse(filesArr);
    }
    for (String fileInfo : filesArr) {
      getLog().info(fileInfo);
    }
  }

  /**
   * Recursive function for getting attributes
   * of each file and directory of root directory.
   * @param directory - path to the calculating directory
   * @throws MojoExecutionException - MojoExecutionException
   */
  private void fillFilesArr(final File directory)
      throws MojoExecutionException {
    if (!directory.canRead()) {
      String directiryInfo = filesArr.remove(filesArr.size() - 1);
      filesArr.add(directiryInfo + " Access denied.");
      return;
    }
    for (File file : directory.listFiles()) {
      Path filePath = file.toPath();
      BasicFileAttributes attr = null;
      try {
        attr = Files.readAttributes(filePath, BasicFileAttributes.class);
        if (mode) {
          filesArr.add(attr.creationTime().toString()
              + ' ' + filePath.toString());
        } else {
          filesArr.add(attr.lastModifiedTime().toString()
              + ' ' + filePath.toString());
        }
      } catch (IOException exception) {
        throw new MojoExecutionException("Unable to read attributes of file: "
            + file);
      }
      if (file.isDirectory()) {
        fillFilesArr(file);
      }
    }
  }

  /**
   * Prints values of all parameters of the plugin.
   */
  private void printParameters() {
    getLog().info("Serach in directory: " + rootDir);
    if (sortAsc) {
      getLog().info("Sort ascending");
    } else {
      getLog().info("Sort descending");
    }
    if (mode) {
      getLog().info("by creation date.");
    } else {
      getLog().info("by change date.");
    }
  }

  /**
   * Validate input.
   * @throws MojoExecutionException - MojoExecutionException
   */
  private void checkInput() throws MojoExecutionException {
    if (!rootDir.isDirectory()) {
      throw new MojoExecutionException(rootDir + " is not a directory.");
    }
    if (!rootDir.canRead()) {
      throw new MojoExecutionException(rootDir + " Access denied");
    }
  }
}

package ru.hh.school;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo(name = "sort")
public class DateControl extends AbstractMojo {

  /*
   * Absolute path of directory starting with "/"
   * or relative path inside of root project folder
   */
  @Parameter(property = "rootDir", defaultValue = "${basedir}")
  private File rootDir;

  /*
   * Sort ascending(true) or descending(false)
   */
  @Parameter(property = "sortAsc", defaultValue = "true")
  private boolean sortAsc;

  /*
   * Sort by creation date(true) or change date(false)
   */
  @Parameter(property = "mode", defaultValue = "true")
  private boolean mode;

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

  void fillFilesArr(File directory) throws MojoExecutionException {
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
          filesArr.add(attr.creationTime().toString() + ' ' + filePath.toString());
        } else {
          filesArr.add(attr.lastModifiedTime().toString() + ' ' + filePath.toString());
        }
      } catch (IOException exception) {
        throw new MojoExecutionException("Unable to read attributes of file: " + file);
      }
      if (file.isDirectory()) {
        fillFilesArr(file);
      }
    }
  }

  void printParameters() {
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

  void checkInput() throws MojoExecutionException {
    if (!rootDir.isDirectory()) {
      throw new MojoExecutionException(rootDir + " is not a directory.");
    }
    if (!rootDir.canRead()) {
      throw new MojoExecutionException(rootDir + " Access denied");
    }
  }
}
package com.hh.homework.maven;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import org.apache.commons.io.filefilter.RegexFileFilter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

import java.io.File;
import java.util.*;

/**
 *         Show files from src, which were changed after last build.
 *
 *         Parameters:
 *         onlyJava (boolean) - if true, show only *.java files;
 *         maxAmount (int) - show not more than maxAmount recently changed files.
 *
 */


@Mojo(name = "recentlyChanged", defaultPhase = LifecyclePhase.PRE_CLEAN, threadSafe = true)
public class RecentlyChangesMojo extends AbstractMojo {

    @Parameter( defaultValue = "${project}", readonly = true )
    private MavenProject project;

    @Parameter(defaultValue = "${project.build.directory}", readonly = true)
    private File outputDirectory;

    @Parameter(property = "maxAmount", defaultValue = "-1")
    private int maxAmount;

    @Parameter(property = "onlyJava", defaultValue="true")
    private boolean onlyJava;

    public void execute() throws MojoExecutionException {

        String fileType = "";
        if (onlyJava) {
            fileType = " java";
        }
        getLog().info("Looking for recently changed" + fileType + " files");

        try {
            File packageFile = new File(outputDirectory, project.getName() + '-' +project.getVersion() + '.' + project.getPackaging());

            RegexFileFilter fileFilter = new RegexFileFilter(".*");
            if (onlyJava) {
                fileFilter = new RegexFileFilter(".*java");
            }
            File src = new File(project.getBasedir(), "src");
            Collection<File> allFiles = FileUtils.listFiles(src, fileFilter, TrueFileFilter.INSTANCE);
            getLog().info("Total file amount: " + allFiles.size());

            ArrayList<File> changedFiles = new ArrayList();
            for (File item : allFiles) {
                if (item.lastModified() > packageFile.lastModified()) {
                    changedFiles.add(item);
                }
            }
            changedFiles.sort((File a, File b)-> a.lastModified() >  b.lastModified() ? -1 :  1);
            getLog().info("Recently changed file amount: " + changedFiles.size());
            for (File item : changedFiles) {
                if (0 == maxAmount) {
                    break;
                }
                getLog().info(item.toString());
                maxAmount -= 1;
            }
        } catch (Exception e) {
            getLog().error(e);
            throw new MojoExecutionException(e.getMessage());
        }
    }
}

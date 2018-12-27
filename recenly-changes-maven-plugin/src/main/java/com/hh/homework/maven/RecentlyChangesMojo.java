package com.hh.homework.maven;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import org.apache.commons.io.filefilter.RegexFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

/**
 *         Show files from src, which were changed after last build.
 *
 *         Parameters:
 *         onlyJava (boolean) - if true, show only *.java files;
 *         maxAmount (int) - show not more than maxAmount recently changed files.
 *
 * @parameter maxAmount
 * @param onlyJava
 *
 *
 */


@Mojo(name = "recentlyChanged", defaultPhase = LifecyclePhase.PRE_CLEAN, threadSafe = true)
public class RecentlyChangesMojo extends AbstractMojo {

    @Parameter( defaultValue = "${project}", readonly = true)
    private MavenProject project;

    @Parameter(defaultValue = "${project.build.directory}", readonly = true)
    private File outputDirectory;

    @Parameter(property = "maxAmount", defaultValue = "-1", required = true)
    private int maxAmount;

    @Parameter(property = "onlyJava", defaultValue="true", required = true)
    private boolean onlyJava;

    public void execute() throws MojoExecutionException {

        String fileType = onlyJava ? " java" : "";
        getLog().info("Looking for recently changed" + fileType + " files");

        try {
            String[] projectTypes = new String[] { "jar",  project.getPackaging()};
            Long packageTime = FileUtils.listFiles(outputDirectory, projectTypes, true).stream()
                .max((File a, File b)-> Long.compare(b.lastModified(), a.lastModified()))
                .get()
                .lastModified();

            SuffixFileFilter fileFilter = onlyJava ? new SuffixFileFilter(".java") : new SuffixFileFilter("");
            File src = new File(project.getBasedir(), "src");
            Collection<File> allFiles = FileUtils.listFiles(src, fileFilter, TrueFileFilter.INSTANCE);
            getLog().info("Total file amount: " + allFiles.size());

            List<File> changedFiles = allFiles.stream()
                    .filter(x -> x.lastModified() > packageTime)
                    .collect(Collectors.toList());

            maxAmount = maxAmount < 0 ? changedFiles.size() : maxAmount;
            getLog().info("Recently changed file amount: " + changedFiles.size());
            changedFiles.stream()
                    .sorted((File a, File b)-> Long.compare(b.lastModified(), a.lastModified()))
                    .limit(maxAmount)
                    .forEach(x -> getLog().info(x.toString()));

        } catch (Exception e) {
            getLog().error(e);
            throw new MojoExecutionException(e.getMessage());
        }
    }
}

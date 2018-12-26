package ru.hh.school;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import ru.hh.school.errors.BuildException;

/**
 * Building docker image.
 */
@Mojo(name = "build", defaultPhase = LifecyclePhase.INSTALL, threadSafe = true)
public class BuildMojo extends AbstractMojo {

    /**
     * Command for run docker daemon.
     */
    @Parameter(property = "dockerExec", defaultValue = "docker")
    private String dockerExec;

    /**
     * Path to build context.
     */
    @Parameter(property = "contextDir", defaultValue = "${project.build.directory}")
    private String contextDir;

    /**
     * Dockerfile name.
     */
    @Parameter(property = "dockerfile", defaultValue = "Dockerfile")
    private String dockerfile;

    /**
     * Name for image.
     */
    @Parameter(property = "repositoryName", defaultValue = "${project.name}")
    private String repositoryName;

    /**
     * List of tags for image.
     */
    @Parameter(property = "dockerTags", required = true)
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private List<String> dockerTags;

    @Override
    public void execute() throws MojoExecutionException {
        List<String> command = buildCommand();
        buildImage(command);
    }

    private void buildImage(List<String> command) throws MojoExecutionException {
        getLog().info("Start build image: " + String.join(" ", command));

        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.directory(new File(contextDir));
        try {
            Process start = processBuilder.start();
            int exit = start.waitFor();
            if (exit != 0) {
                BufferedReader errorReader = new BufferedReader(
                        new InputStreamReader(start.getErrorStream())
                );
                String errorMsg = errorReader.lines().collect(Collectors.joining("\n"));
                throw new BuildException(errorMsg);
            }
        } catch (BuildException | IOException | InterruptedException e) {
            getLog().error("Error when build docker image: " + e.getMessage());
            throw new MojoExecutionException("Couldn't build docker image", e);
        }
        getLog().info("Successful build image");
    }

    private List<String> buildCommand() throws MojoExecutionException {
        if(dockerTags == null || dockerTags.isEmpty()) {
            throw new MojoExecutionException("You should provide at least one tag");
        }

        List<String> command = new ArrayList<>();
        command.add(dockerExec);
        command.add("build");

        List<String> tagArgs = dockerTags.stream()
                .map(tag -> "-t" + repositoryName + ":" + tag)
                .collect(Collectors.toList());

        command.addAll(tagArgs);
        // перед выполнением будет смена директории на contextDir
        command.add("-f" + dockerfile);
        command.add(".");
        return command;
    }
}

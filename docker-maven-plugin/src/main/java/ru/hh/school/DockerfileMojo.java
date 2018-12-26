package ru.hh.school;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

/**
 * Generate Dockerfile.
 */
@Mojo(name = "generate", defaultPhase = LifecyclePhase.PACKAGE, threadSafe = true)
public class DockerfileMojo extends AbstractMojo {

    /**
     * Dir for generated Dockerfile.
     */
    @Parameter(property = "outputDir", defaultValue = "${project.build.directory}")
    private String outputDir;

    /**
     * Artifact to be added to image.
     */
    @Parameter(property = "artifactName",
            defaultValue = "${project.build.finalName}.${project.packaging}")
    private String artifactName;

    /**
     * Base image to build image.
     */
    @Parameter(property = "baseImage", defaultValue = "openjdk:8-jre")
    private String baseImage;

    /**
     * Run commands.
     */
    @Parameter(property = "runs")
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private List<String> runs = new ArrayList<>();

    /**
     * Entrypoint generated in ["sh", "-c", "ls"] format.
     */
    @Parameter(property = "entryPoint")
    private List<String> entryPoint;

    /**
     * CMD generated in ["sh", "-c", "ls"] format.
     */
    @Parameter(property = "cmd")
    private List<String> cmd;

    /**
     * Volumes.
     */
    @Parameter(property = "volumes")
    private List<String> volumes = new ArrayList<>();

    /**
     * Exposed ports.
     */
    @Parameter(property = "ports")
    private List<String> ports = new ArrayList<>();

    /**
     * Maven project.
     */
    @Parameter(readonly = true, defaultValue = "${project}")
    private MavenProject mavenProject;

    @Override
    public void execute() throws MojoExecutionException {
        String targetDirectory = mavenProject.getModel().getBuild().getDirectory();
        List<String> dockerfile = buildDockerfile();
        try {
            Files.createDirectories(Paths.get(targetDirectory));
            Files.write(
                    Paths.get(targetDirectory,"Dockerfile"),
                    dockerfile
            );
        } catch (IOException e) {
            getLog().error("Couldn't generate Dockerfile: ", e);
            throw new MojoExecutionException("Couldn't generate Dockerfile: ", e);
        }
    }

    private List<String> buildDockerfile() {
        List<String> dockerfile = new ArrayList<>();
        dockerfile.add("FROM " + baseImage);

        for (String runCommand : runs) {
            dockerfile.add("RUN " + runCommand);
        }

        if (!entryPoint.isEmpty()) {
            dockerfile.add("ENTRYPOINT [\"" + String.join("\", \"", entryPoint) + "\"]");
        }
        if (!cmd.isEmpty()) {
            dockerfile.add("CMD [\"" + String.join("\", \"", cmd) + "\"]");
        }

        // Default
        if (!cmd.isEmpty() && !entryPoint.isEmpty()) {
            dockerfile.add(
                    "ENTRYPOINT [\"/usr/bin/java\", \"-jar\", \"/usr/share/service/service.jar\"]\n"
            );
        }

        if (!volumes.isEmpty()) {
            dockerfile.add("VOLUME " + String.join(" ", volumes));
        }

        dockerfile.add("ADD " + artifactName + " /usr/share/service/service.jar");

        if (!ports.isEmpty()) {
            dockerfile.add("EXPOSE " + String.join(" ", ports));
        }
        return dockerfile;
    }
}

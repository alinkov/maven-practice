package ru.hh.school.plugins.file_size_control_plugin;

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

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;


/**
 * Goal which control files size.
 *
 */
@Mojo(name = "control", defaultPhase = LifecyclePhase.PACKAGE)
public class FileSizeControlMojo extends AbstractMojo {
    /**
     * Target directory.
     */
    @Parameter(defaultValue = "${project.build.directory}", property = "directory", required = true)
    private File directory;

    /**
     * Maximum file size (in bytes).
     */
    @Parameter(defaultValue = "1024", property = "size", required = true)
    private long size;

    class FileVerifier extends SimpleFileVisitor<Path> {

        private boolean verifyResult;

        @Override
        public FileVisitResult visitFile(final Path path, final BasicFileAttributes attrs)
                throws IOException {
            if (attrs.isRegularFile() && attrs.size() > size) {
                getLog().error("File size exceeded: " + path.toString()
                    + ". File size: " + attrs.size() + " bytes.");
                verifyResult = false;
            }
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(final Path path, final IOException exc)
                throws IOException {
            getLog().error("Failed to process: " + path.toString()
                + ". Reason: " + exc.toString());
            verifyResult = false;
            return FileVisitResult.SKIP_SUBTREE;
        }

        public boolean getVerifyResult() {
            return verifyResult;
        }

    }

    public final void execute()
        throws MojoExecutionException, MojoFailureException {
        getLog().info("Directory = " + directory.getPath());
        getLog().info("max size = " + size);

        if (!directory.exists()) {
            getLog().error("Directory " + directory + " doesn't exists.");
            throw new MojoFailureException("Directory doesn't exists.");
        }
        if (!directory.isDirectory()) {
            getLog().error("Directory " + directory + " is not a directory.");
            throw new MojoFailureException("Illegal directory path.");
        }

        FileVerifier verifer = new FileVerifier();
        try {
            Files.walkFileTree(Paths.get(directory.getPath()), verifer);
        } catch (IOException e) {
            getLog().error(e);
            return;
        }
        if (!verifer.getVerifyResult()) {
            throw new MojoFailureException("Large files detected.");
        }
    }

}


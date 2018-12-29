package ru.hh.school;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.NotFoundException;
import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URI;
import java.nio.file.*;
import java.util.Iterator;
import java.util.Vector;

/**
 * Scan project for fields in classes.
 */

@Mojo(name = "setname", defaultPhase = LifecyclePhase.PROCESS_CLASSES, threadSafe = true)
public class NamingMojo extends AbstractMojo {
    @Parameter(defaultValue = "${project.groupId}.", readonly = true)
    private String groupTarget;

    /**
     * Target class. Default scan all.
     */
    @Parameter(defaultValue = "", property = "classTarget")
    private String classTarget;

    /**
     * Target field. Default scan all.
     */
    @Parameter(defaultValue = "", property = "fieldTarget")
    private String fieldTarget;

    /**
     * Message on success.
     */
    @Parameter(defaultValue = "Поле содержит: ", property = "message")
    private String message;

    @Parameter( defaultValue = "${project}", readonly = true )
    private MavenProject project;

    public void execute() throws MojoExecutionException {
        getLog().info("Замена полей");


        ClassPool pool = ClassPool.getDefault();
        try {
            Vector<String> classList = new Vector<String>();
            for( String cp : project.getCompileClasspathElements()) {
                pool.insertClassPath(cp);
                if (classTarget==null || classTarget.equals("")) {
                    processFilesFromFolder(new File(cp),"", classList);
                }
            }
            if (classTarget!=null && classTarget.length()!=0) {
                classList.add(groupTarget.concat(classTarget));
            }
            for (String className : classList) {
                try {
                    CtClass ct = pool.get(className);
                    if (fieldTarget == null || fieldTarget.equals("")) {
                        CtField[] fields = ct.getFields();
                        for (CtField field : fields) {
                            getLog().info(String.format("%s %s#%s: %s", message, className, field.getName(), field.getConstantValue()));
                        }
                    } else {
                        try {
                            CtField field = ct.getField(fieldTarget);
                            getLog().info(String.format("%s %s#%s: %s", message, className, fieldTarget, field.getConstantValue()));
                        }
                        catch (NotFoundException e) {
                            if (classList.size()<2) {
                                getLog().error(e.getMessage());
                                throw new MojoExecutionException(e.getMessage());
                            }
                        }
                    }
                }
                catch (NotFoundException e) {
                    getLog().error(e.getMessage());
                    throw new MojoExecutionException(e.getMessage());
                }
            }
        } catch (NotFoundException | DependencyResolutionRequiredException e) {
            getLog().error(e.getMessage());
            throw new MojoExecutionException(e.getMessage());
        }
    }

    private void processFilesFromFolder(File folder, String packageName, Vector<String> classList)
    {
        File[] folderEntries = folder.listFiles();
        for (File entry : folderEntries)
        {
            if (entry.isDirectory())
            {
                String newPackageName;
                if (packageName==null || packageName.equals("")) {
                    newPackageName = new String(entry.getName());
                }
                else {
                    newPackageName = packageName.concat(".").concat(entry.getName());
                }
                processFilesFromFolder(entry, newPackageName, classList);
                continue;
            }
            String fileName = entry.getName();
            if (fileName.endsWith(".class")) {
                classList.add(packageName.concat(".").concat(fileName.substring(0,fileName.length()-6)));
            }
        }
    }
}
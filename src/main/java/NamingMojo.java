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

@Mojo(name = "setname", defaultPhase = LifecyclePhase.PROCESS_CLASSES, threadSafe = true)
public class NamingMojo extends AbstractMojo {
    @Parameter(defaultValue = "${project.groupId}.", readonly = true)
    private String groupTarget;

    @Parameter(property = "classTarget")
    private String classTarget;

    @Parameter(property = "fieldTarget")
    private String fieldTarget;

    @Parameter(defaultValue = "Поле содержит: ", property = "message")
    private String message;

    @Parameter( defaultValue = "${project}", readonly = true )
    private MavenProject project;

    public void execute() throws MojoExecutionException {
        getLog().info("Замена полей");

        ClassPool pool = ClassPool.getDefault();
        try {
            for( String cp : project.getCompileClasspathElements()) {
                pool.insertClassPath(cp);
            }
            CtClass ct = pool.get(groupTarget+classTarget);
            CtField field = ct.getField(fieldTarget);
            getLog().info(String.format("%s %s", message, field.getConstantValue()));
        } catch (NotFoundException | DependencyResolutionRequiredException e) {
            getLog().error(e.getMessage());
            throw new MojoExecutionException(e.getMessage());
        }
    }
}
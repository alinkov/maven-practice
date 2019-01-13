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
import java.util.LinkedList;

/**
 * Scan project for fields in classes.
 */

@Mojo(
        name = "setname",
        defaultPhase = LifecyclePhase.PROCESS_CLASSES,
        threadSafe = true
)
public class NamingMojo extends AbstractMojo {
    /**
     * Target group ID. Hidden parametr.
     */
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

    /**
     * Project description.
     */
    @Parameter(defaultValue = "${project}", readonly = true)
    private MavenProject project;

    /**
     * Output Field of Class.
     *
     * @throws MojoExecutionException Can throw MojoExecutionException.
     */
    public final void execute() throws MojoExecutionException {
        getLog().info("Замена полей");

        ClassPool pool = ClassPool.getDefault();
        try {
            LinkedList<String> classList = new LinkedList<String>();
            for (String cp : project.getCompileClasspathElements()) {
                pool.insertClassPath(cp);
                if (classTarget == null || classTarget.equals("")) {
                    classList.addAll(
                            processFilesFromFolder(new File(cp), "")
                    );
                }
            }
            if (classTarget != null && classTarget.length() != 0) {
                classList.add(groupTarget.concat(classTarget));
            }
            for (String className : classList) {
                CtClass ct = pool.get(className);
                if (classList.size() < 2) {
                    showNeedFields(ct, true);
                } else {
                    showNeedFields(ct, false);
                }
            }
        } catch (NotFoundException e) {
            getLog().error(e.getMessage());
            throw new MojoExecutionException(e.getMessage());
        } catch (DependencyResolutionRequiredException e) {
            getLog().error(e.getMessage());
            throw new MojoExecutionException(e.getMessage());
        }
    }

    /**
     * Вывод значение поля fieldTarget.
     * При пустом fieldTarget вывод всех полей класса.
     *
     * @param ct        Класс, в котором необходимо искать поле.
     * @param needError В случае отсутствия поля выдавать/не выдавать ошибку.
     * @throws MojoExecutionException Ошибка отсутствия поля.
     */
    private void showNeedFields(final CtClass ct, final boolean needError)
            throws MojoExecutionException {
        if (fieldTarget == null || fieldTarget.equals("")) {
            CtField[] fields = ct.getFields();
            for (CtField field : fields) {
                getLog().info(String.format(
                        "%s %s#%s: %s",
                        message,
                        ct.getName(),
                        field.getName(),
                        field.getConstantValue()
                ));
            }
        } else {
            try {
                CtField field = ct.getField(fieldTarget);
                getLog().info(String.format(
                        "%s %s#%s: %s",
                        message,
                        ct.getName(),
                        fieldTarget,
                        field.getConstantValue())
                );
            } catch (NotFoundException e) {
                if (needError) {
                    getLog().error(e.getMessage());
                    throw new MojoExecutionException(e.getMessage());
                }
            }
        }
    }

    /**
     * Create list of classes in folder.
     *
     * @param folder      Current folder.
     * @param packageName Current package name.
     * @return List of classes in current folder.
     */
    private LinkedList<String> processFilesFromFolder(
            final File folder,
            final String packageName
    ) {
        LinkedList<String> classList = new LinkedList<String>();
        File[] folderEntries = folder.listFiles();
        for (File entry : folderEntries) {
            if (entry.isDirectory()) {
                if (entry.canRead()) {
                    String newPackageName;
                    if (packageName == null || packageName.equals("")) {
                        newPackageName = entry.getName();
                    } else {
                        newPackageName = packageName.concat(".");
                        newPackageName = newPackageName.concat(entry.getName());
                    }
                    classList.addAll(
                            processFilesFromFolder(entry, newPackageName)
                    );
                }
                continue;
            }
            String fileName = entry.getName();
            String filter = ".class";
            if (fileName.endsWith(filter)) {
                String className;
                if (packageName != null && !packageName.equals("")) {
                    className = packageName.concat(".");
                } else {
                    className = "";
                }
                int nameLen = fileName.length() - filter.length();
                className = className.concat(fileName.substring(0, nameLen));
                classList.add(className);
            }
        }
        return classList;
    }
}

package mavenplugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import java.io.File;
import java.util.Arrays;

import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * This plugin finds large files in a directory by comparing their size with threshold.
 * It shows path to such files.
 *
 * @author AndreyPonorenko
 */
@Mojo(name = "check-sizes")
public class MyMojo extends AbstractMojo {

    /**
     * Path to the top directory.
     */
    @Parameter(property = "path", defaultValue = "${basedir}")
    private File path;

    /**
     * Threshold value of bytes to compare with file size in the directory.
     */
    @Parameter(property = "theshold", defaultValue = "1024")
    private long theshold;

    /**
     * Recursive search to analyze files in directories.
     */
    @Parameter(property = "recursiveDescent", defaultValue = "true")
    private boolean recursiveDescent;

    public void execute(){
        if (path.exists()) {
            searchLargeFiles(path);
        } else {
            getLog().warn(String.format("Chosen directory: %s does not exists", path));
        }
    }

    void searchLargeFiles(File dir) {
        if (!dir.canRead()) {
            getLog().warn(dir + " cannot be read");
            return;
        }
        File[] files = dir.listFiles();
        Arrays.stream(files)
                .map(file -> {
                    if (file.isDirectory() && recursiveDescent) {
                        searchLargeFiles(file);
                    }
                    return file;
                })
                .filter(p -> !p.isDirectory())
                .forEach(this::check);
    }

    void check(File file) {
        if (file.length() > theshold)
            getLog().warn(String.format("%s = %s bytes (more then %s bytes) ",
                    file.getAbsolutePath(), file.length(), theshold));
    }
}

package hh;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.util.ArrayList;

/**
 * Goal which scan dir and check files
 */
@Mojo(name = "scanAndTest", defaultPhase = LifecyclePhase.PROCESS_SOURCES)
public class TestFiles extends AbstractMojo {

    private void recursiveDirScan(File start, ArrayList<File> filelist) {
        try {
            if (start.isDirectory()) {
                for (File item : start.listFiles()) {
                    if (item.isDirectory()) {
                        recursiveDirScan(item, filelist);
                    } else {
                        filelist.add(item);
                    }
                }
            }
        } catch (Exception e) {
            /* Не могу придумать, что тут правильно будет делать. Нет доступа, значит просто пропустить? */
        }
    }

    /**
     * Location of the file.
     */
    @Parameter(defaultValue = "${project.build.directory}", property = "scanDir", required = true)
    private File scanDirectory;

    /**
     * Minimum of files count
     */
    @Parameter(defaultValue = "0", property = "minFileCount", required = true)
    private long minFileCount;

    /**
     * Maximum size of files
     */
    @Parameter(defaultValue = "100000", property = "maxFilesSize", required = true)
    private long maxFilesSize;

    public void execute() throws MojoExecutionException {
        File f = scanDirectory;
        ArrayList<File> filelist = new ArrayList<File>();
        recursiveDirScan(f, filelist);

        System.out.println(scanDirectory);

        if (filelist.size() < minFileCount)
            throw new MojoExecutionException("In direcrory must be at least " + minFileCount + " files");

        long fileSize = 0;
        for (File item : filelist) {
            fileSize += item.length();
            if (fileSize > maxFilesSize)
                throw new MojoExecutionException("Maximum size of files in direcrory (" + maxFilesSize + ") exceeded");
        }
    }
}
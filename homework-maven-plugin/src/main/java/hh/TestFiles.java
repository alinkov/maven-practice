package hh;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;

/**
 * Goal which scan dir and check files
 */
@Mojo( name = "scanAndTest", defaultPhase = LifecyclePhase.PROCESS_SOURCES )
public class TestFiles
    extends AbstractMojo
{

    private void recursiveDirScan(File start, ArrayList<File> filelist)
    {
        if(start.isDirectory())
        {
            for(File item : start.listFiles()){
                if(item.isDirectory()){
                    recursiveDirScan(item, filelist);
                }
                else{
                    filelist.add(item);
                }
            }
        }
    }

    /**
     * Location of the file.
     */
    @Parameter( defaultValue = "${project.build.directory}", property = "scanDir", required = true )
    private File scanDirectory;

    @Parameter( defaultValue = "5", property = "minFileCount", required = true )
    private String minFileCount;

    @Parameter( defaultValue = "100000", property = "maxFilesSize", required = true )
    private String maxFilesSize;

    public void execute()
        throws MojoExecutionException
    {
        File f = scanDirectory;
        ArrayList<File> filelist = new ArrayList<File>();
        recursiveDirScan(f,filelist);

        Long minCount = new Long(Integer.parseInt(minFileCount));
        Long maxSize = new Long(Integer.parseInt(maxFilesSize));

        if (filelist.size() < minCount)
            throw new MojoExecutionException( "In direcrory must be at least "+minFileCount+" files" );

        Long fileSize = new Long(0);
        for (File item : filelist)
        {
            fileSize += item.length();
            if(fileSize > maxSize)
                throw new MojoExecutionException( "Maximum size of files in direcrory ("+maxSize+") exceeded" );
        }
    }
}

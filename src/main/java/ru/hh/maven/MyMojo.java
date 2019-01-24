package ru.hh.maven;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Goal which touches a timestamp file.
 */
@Mojo( name = "count", defaultPhase = LifecyclePhase.PROCESS_SOURCES )
public class MyMojo
    extends AbstractMojo
{
    /**
     * Location of the file.
     */
    @Parameter( defaultValue = "${project.build.directory}", property = "searchDir", required = true )
    private File searchDirectory;

    /**
     * File name
     */
    @Parameter( defaultValue = "1024", property = "size", required = false )
    private int fileSize;
    
    
    public void execute()
        throws MojoExecutionException
    {
        if ( !searchDirectory.exists() )
        {
        	System.out.println("Can't find directory" + searchDirectory);
        	throw new MojoExecutionException( "Can't find directory " + searchDirectory );
        }

        File report = new File("Isc_Mojo_Report.txt");
        FileWriter w = null;
        try
        {
        	w = new FileWriter( report );
        }
        catch ( IOException e )
        {
            throw new MojoExecutionException( "Error creating file " + report, e );
        }
        //1. Get all files in searchDirectory
        File[] listOfFiles = searchDirectory.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
        	File curFile=listOfFiles[i];
           if (curFile.isFile()) {
              System.out.println("File " + curFile.getName());
              //2. Get size of each file and compare with fileSize
              if (curFile.length() < fileSize) {
            	  //3. Write name of file and it's size to Isc_Mojo_Report.txt in current directory
            	  try
                  {
                      w.write(curFile.getName() + " -> " + curFile.length() + " bytes\n");
                  }
                  catch ( IOException e )
                  {
                      throw new MojoExecutionException( "Error writing file " + report, e );
                  } 
              }
           }
        }
        if ( w != null )
        {
        	try {
				w.close();
			} catch (IOException e) {
				 // ignore
			}
        }

    }
}

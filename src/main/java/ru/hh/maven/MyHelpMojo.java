package ru.hh.maven;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;


@Mojo( name = "help", defaultPhase = LifecyclePhase.PROCESS_SOURCES )
public class MyHelpMojo
    extends AbstractMojo
{
	/**
     * Location of the file.
     */
    @Parameter( defaultValue = "false", property = "detailed", required = true )
    private boolean helpDetails;

    public void execute()
        throws MojoExecutionException
    {
    	String shortHelp="\n  Usage:\n\n"
    			+ "  mvn ru.hh.school:Isc1plugin-maven-plugin:[goal]\n\n"
    			+ "  where goal may be:\n"
    			+ "     help - writes this message\n"
    			+ "     count - counts files in target directory with size less then given size\n\n"
    			+ "  use\n\n"
    			+ "  mvn ru.hh.school:Isc1plugin-maven-plugin:help -Ddetailed=true\n\n"
    			+ "  for more details";
    	
    	String detailedHelp="\n  Plugin is used to get list of files in target directory with size less then given size\n\n"
    			+ "  Usage:\n\n"
    			+ "  mvn ru.hh.school:Isc1plugin-maven-plugin:[goal]\n\n"
    			+ "  where goal may be:\n"
    			+ "     help - writes this message\n"
    			+ "     count - counts files and creates report Isc_Mojo_Report.txt in current directory\n\n"
    			+ "  Parameters:\n"
    			+ "     searchDir - target directory to search files\n"
    			+ "     size - given maximum file size to search\n\n"
    			+ "  Example:\n\n"
    			+ "  mvn ru.hh.school:Isc1plugin-maven-plugin:count -DsearchDir=\"C:\\temp\" -Dsize=1024\n";
    	
    	String helpString;
    	if(helpDetails==false) helpString=shortHelp;
    	else helpString=detailedHelp;
    	
    	System.out.println(helpString);
    	
        File help = new File("IscMojoHelp.txt");
        
        FileWriter w = null;
        try
        {
            w = new FileWriter( help );

            w.write(helpString);
        }
        catch ( IOException e )
        {
            throw new MojoExecutionException( "Error creating file " + help, e );
        }
        finally
        {
            if ( w != null )
            {
                try
                {
                    w.close();
                }
                catch ( IOException e )
                {
                    // ignore
                }
            }
        }
    }
}

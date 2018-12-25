package hh;


import org.apache.maven.plugin.testing.MojoRule;
import org.apache.maven.plugin.testing.WithoutMojo;

import org.junit.Rule;
import static org.junit.Assert.*;
import org.junit.Test;
import java.io.File;

public class MyMojoTest
{
    @Rule
    public MojoRule rule = new MojoRule()
    {
        @Override
        protected void before() throws Throwable 
        {
        }

        @Override
        protected void after()
        {
        }
    };

    /**
     * @throws Exception if any
     */
    @Test
    public void testSomething()
            throws Exception
    {
        File pom = new File( "." );

        System.out.println(pom.getAbsolutePath());

        assertNotNull( pom );
        assertTrue( pom.exists() );

        TestFiles testFiles = ( TestFiles ) rule.lookupConfiguredMojo( pom, "touch" );
        assertNotNull( testFiles );
        testFiles.execute();

        File scanDirectory = ( File ) rule.getVariableValueFromObject( testFiles, "scanDirectory" );
        assertNotNull( scanDirectory );
        assertTrue( scanDirectory.exists() );

        File touch = new File( scanDirectory, "touch.txt" );
        assertTrue( touch.exists() );

    }

    /** Do not need the MojoRule. */
    @WithoutMojo
    @Test
    public void testSomethingWhichDoesNotNeedTheMojoAndProbablyShouldBeExtractedIntoANewClassOfItsOwn()
    {
        assertTrue( true );
    }

}


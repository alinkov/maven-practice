package sample.plugin;


import org.apache.maven.plugin.testing.MojoRule;

import org.junit.Rule;

import static org.junit.Assert.*;

import org.junit.Test;

import java.io.File;

public class StopListMojoTest {
    @Rule
    public MojoRule rule = new MojoRule() {};

    @Test
    public void testPluginRun() throws Exception {
        File pom = new File("target/test-classes/project-to-test/");
        assertNotNull(pom);
        assertTrue(pom.exists());
        StopListMojo myMojo = (StopListMojo) rule.lookupConfiguredMojo(pom, "check");
        myMojo.execute();
    }

}


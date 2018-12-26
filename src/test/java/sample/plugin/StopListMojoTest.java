package sample.plugin;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import org.apache.maven.plugin.testing.MojoRule;
import org.junit.Rule;
import org.junit.Test;

public class StopListMojoTest {
  @Rule public MojoRule rule = new MojoRule() {};

  @Test
  public void testPluginRun() throws Exception {
    File pom = new File("src/test/resources/project-to-test/");
    assertNotNull(pom);
    assertTrue(pom.exists());
    StopListMojo myMojo = (StopListMojo) rule.lookupConfiguredMojo(pom, "check");
    myMojo.execute();
  }
}